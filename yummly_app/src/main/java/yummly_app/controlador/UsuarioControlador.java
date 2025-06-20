package yummly_app.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yummly_app.dao.UsuarioDAO;
import yummly_app.dto.CompletarRegistroDTO;
import yummly_app.dto.RegistroInicialDTO;
import yummly_app.modelo.Usuario;
import yummly_app.servicio.UsuarioServicio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
    @Autowired
    private UsuarioServicio usuarioServicio;
	
    // GET /usuarios → devuelve todos los usuarios
    @GetMapping("")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioDAO.obtenerTodosLosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    // GET /usuarios/{id} → devuelve usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioDAO.obtenerUsuarioPorId(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    // Obtener usuario por alias
    @GetMapping("/alias/{alias}")
    public ResponseEntity<Usuario> obtenerUsuarioPorAlias(@PathVariable String alias) {
        return usuarioDAO.obtenerUsuarioPorAlias(alias)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        return usuarioDAO.obtenerUsuarioPorEmail(email)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /usuarios/registro-inicial
    @PostMapping("/registro-inicial")
    public ResponseEntity<?> registrarInicial(@RequestBody RegistroInicialDTO dto) {
        try {
            Usuario creado = usuarioServicio.registrarUsuarioInicial(dto.getAlias(), dto.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    // PUT /usuarios/completar-registro/{id}
    @PutMapping("/completar-registro/{id}")
    public ResponseEntity<?> completarRegistro(@PathVariable Long id, @RequestBody CompletarRegistroDTO dto) {
        try {
            Usuario actualizado = usuarioServicio.completarRegistro(id, dto.getNombre(), dto.getApellido(), dto.getContrasena());
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // PUT /usuarios/{id} → actualizar datos del usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario datosActualizados) {
        try {
            Usuario usuarioActualizado = usuarioDAO.actualizarUsuario(id, datosActualizados);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /usuarios/{id} → eliminar usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorId(id);
        if (usuarioOpt.isPresent()) {
            usuarioDAO.eliminarUsuarioPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
