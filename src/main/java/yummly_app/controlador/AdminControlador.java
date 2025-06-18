package yummly_app.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yummly_app.dto.RegistroInicialDTO;
import yummly_app.modelo.Usuario;
import yummly_app.servicio.UsuarioServicio;

@RestController
@RequestMapping("/admin")
public class AdminControlador {
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
    // POST /usuarios/registro-alumno → alumnos (registrados internamente)
    @PostMapping("/registro-alumno")
    public ResponseEntity<?> registrarAlumno(@RequestBody RegistroInicialDTO dto) {
        try {
            Usuario creado = usuarioServicio.registrarAlumno(dto.getAlias(), dto.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
