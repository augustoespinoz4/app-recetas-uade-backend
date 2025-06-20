package yummly_app.servicio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yummly_app.dao.UsuarioDAO;
import yummly_app.modelo.Usuario;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private EmailServicio emailServicio;

    // Registro inicial con alias y email
    public Usuario registrarUsuarioInicial(String alias, String email) {
        if (usuarioDAO.existeUsuarioPorAlias(alias)) {
            throw new IllegalArgumentException("El alias ya está en uso");
        }
        if (usuarioDAO.existeUsuarioPorEmail(email)) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        Usuario nuevo = new Usuario();
        nuevo.setAlias(alias);
        nuevo.setEmail(email);
        nuevo.setTipoUsuario("visitante");
        nuevo.setEstadoRegistro("incompleto");
        nuevo.setFechaRegistro(LocalDateTime.now());

        Usuario guardado = usuarioDAO.crearUsuario(nuevo);

        emailServicio.enviarMensajeBienvenida(email, alias);

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
        usuario.setContrasenaHash(contrasena);
        usuario.setEstadoRegistro("completo");
        
        Usuario actualizado = usuarioDAO.crearUsuario(usuario);
        
        // Enviar correo de confirmación de registro completo
        emailServicio.enviarConfirmacionRegistroCompleto(usuario.getEmail(), nombre);

        return actualizado; // save actualiza
    }
}

