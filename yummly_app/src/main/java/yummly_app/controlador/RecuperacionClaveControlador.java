package yummly_app.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import yummly_app.dao.UsuarioDAO;
import yummly_app.modelo.Usuario;
import yummly_app.servicio.RecuperacionClaveServicio;

@RestController
@RequestMapping("/recuperacion-clave")
public class RecuperacionClaveControlador {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private RecuperacionClaveServicio recuperacionClaveServicio;

    // 1. Solicitar recuperación de clave
    @PostMapping("/solicitar")
    public ResponseEntity<String> solicitarCodigo(@RequestParam String email) {
        Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un usuario con ese correo.");
        }

        Usuario usuario = usuarioOpt.get();
        recuperacionClaveServicio.iniciarRecuperacionClave(usuario);

        return ResponseEntity.ok("Código de recuperación enviado al correo.");
    }

    // 2. Verificar código ingresado por el usuario
    @PostMapping("/verificar")
    public ResponseEntity<String> verificarCodigo(
        @RequestParam String email,
        @RequestParam String codigo
    ) {
        Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        boolean valido = recuperacionClaveServicio.validarCodigo(usuario, codigo);

        if (!valido) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido o vencido.");
        }

        return ResponseEntity.ok("Código verificado correctamente.");
    }

 // 3. Actualizar la contraseña
    @PostMapping("/actualizar")
    public ResponseEntity<String> actualizarContrasena(
        @RequestParam String email,
        @RequestParam String codigo,
        @RequestParam String nuevaContrasena
    ) {
        Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        boolean valido = recuperacionClaveServicio.validarCodigo(usuario, codigo);

        if (!valido) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido o expirado.");
        }

        // Guardar la contraseña directamente sin hashear
        usuarioDAO.actualizarContrasena(usuario.getIdUsuario(), nuevaContrasena);

        recuperacionClaveServicio.marcarCodigoComoUsado(usuario, codigo);

        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}


