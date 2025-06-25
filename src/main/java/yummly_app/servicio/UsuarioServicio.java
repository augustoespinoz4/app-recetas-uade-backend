package yummly_app.servicio;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yummly_app.dao.ListaRecetasIntentarDAO;
import yummly_app.dao.UsuarioDAO;
import yummly_app.dto.auth.LoginResponseDTO;
import yummly_app.mapper.UsuarioMapper;
import yummly_app.modelo.ListaRecetasIntentar;
import yummly_app.modelo.Usuario;

@Service
public class UsuarioServicio{

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired
    private ListaRecetasIntentarDAO listaRecetasIntentarDAO;

    @Autowired
    private EmailServicio emailServicio;

    public Usuario registrarUsuarioInicial(String alias, String email) {
        if (usuarioDAO.existeUsuarioPorAlias(alias)) {
            throw new IllegalArgumentException("El alias ya está en uso");
        }
        if (usuarioDAO.existeUsuarioPorEmail(email)) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        try {
            emailServicio.enviarMensajeDeConfirmacion(email, alias);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo de confirmación. Por favor, verifica el email ingresado.");
        }

        Usuario nuevo = new Usuario();
        nuevo.setAlias(alias);
        nuevo.setEmail(email);
        nuevo.setTipoUsuario("visitante");
        nuevo.setEstadoRegistro("incompleto");
        nuevo.setFechaRegistro(LocalDateTime.now());

        Usuario guardado = usuarioDAO.crearUsuario(nuevo);

        // Crear automáticamente la lista de recetas a intentar
        ListaRecetasIntentar lista = new ListaRecetasIntentar();
        lista.setUsuario(guardado);
        lista.setNombreLista("Recetas a intentar");
        listaRecetasIntentarDAO.crearLista(lista);

        return guardado;
    }


    
    // Registro inicial con alias y email
    public Usuario registrarAlumno(String alias, String email) {
        if (usuarioDAO.existeUsuarioPorAlias(alias)) {
            throw new IllegalArgumentException("El alias ya está en uso");
        }
        if (usuarioDAO.existeUsuarioPorEmail(email)) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        try {
            emailServicio.enviarMensajeDeConfirmacion(email, alias);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo de confirmación. Por favor, verifica el email ingresado.");
        }

        Usuario nuevo = new Usuario();
        nuevo.setAlias(alias);
        nuevo.setEmail(email);
        nuevo.setTipoUsuario("alumno");
        nuevo.setEstadoRegistro("incompleto");
        nuevo.setFechaRegistro(LocalDateTime.now());

        Usuario guardado = usuarioDAO.crearUsuario(nuevo);

        // Crear automáticamente la lista de recetas a intentar
        ListaRecetasIntentar lista = new ListaRecetasIntentar();
        lista.setUsuario(guardado);
        lista.setNombreLista("Recetas a intentar");
        listaRecetasIntentarDAO.crearLista(lista);

        return guardado;
    }

    // Completar datos del usuario (etapa 2)
    @Transactional
    public Usuario completarRegistro(Long idUsuario, String nombre, String apellido, String contrasena) {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getEstadoRegistro().equalsIgnoreCase("incompleto")) {
            throw new IllegalStateException("El usuario ya completó el registro");
        }

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setContrasenaHash(contrasena);  // Encripta la contraseña
        usuario.setEstadoRegistro("completo");
        
        Usuario actualizado = usuarioDAO.crearUsuario(usuario);
        
        // Enviar correo de confirmación de registro completo
        emailServicio.enviarConfirmacionRegistroCompleto(usuario.getEmail(), nombre);

        return actualizado; // save actualiza
    }
    

    public LoginResponseDTO login(String aliasOEmail, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorAlias(aliasOEmail);
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioDAO.obtenerUsuarioPorEmail(aliasOEmail);
        }
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!"completo".equalsIgnoreCase(usuario.getEstadoRegistro())) {
            throw new RuntimeException("Registro incompleto, completar registro antes de iniciar sesión");
        }

        if (usuario.getContrasenaHash() == null || !usuario.getContrasenaHash().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return UsuarioMapper.toLoginResponseDTO(usuario);
    }

}

