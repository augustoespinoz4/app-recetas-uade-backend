package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.Usuario;
import yummly_app.repositorio.UsuarioRepository;

@Repository
public class UsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear un nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    // Buscar usuario por alias
    public Optional<Usuario> obtenerUsuarioPorAlias(String alias) {
        return usuarioRepository.findByAlias(alias);
    }

    // Buscar usuario por email
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Verificar existencia por alias
    public boolean existeUsuarioPorAlias(String alias) {
        return usuarioRepository.existsByAlias(alias);
    }

    // Verificar existencia por email
    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Actualizar datos del usuario (parcial: nombre, apellido, tipo, estado)
    @Transactional
    public Usuario actualizarUsuario(Long idUsuario, Usuario datosActualizados) {
        return usuarioRepository.findById(idUsuario)
            .map(usuario -> {
                usuario.setNombre(datosActualizados.getNombre());
                usuario.setApellido(datosActualizados.getApellido());
                usuario.setTipoUsuario(datosActualizados.getTipoUsuario());
                usuario.setEstadoRegistro(datosActualizados.getEstadoRegistro());
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID " + idUsuario));
    }

    // Actualizar contraseña
    @Transactional
    public void actualizarContrasena(Long idUsuario, String nuevaContrasenaHash) {
        usuarioRepository.findById(idUsuario)
            .ifPresent(usuario -> {
                usuario.setContrasenaHash(nuevaContrasenaHash);
                usuarioRepository.save(usuario);
            });
    }

    // Eliminar usuario por ID
    @Transactional
    public void eliminarUsuarioPorId(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    // Eliminar usuario por email
    @Transactional
    public void eliminarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
