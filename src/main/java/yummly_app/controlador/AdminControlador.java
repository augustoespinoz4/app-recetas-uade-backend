package yummly_app.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yummly_app.dao.RecetaDAO;
import yummly_app.dao.ValoracionRecetaDAO;
import yummly_app.dto.CambiarEstadoRecetaDTO;
import yummly_app.dto.RegistroInicialDTO;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Usuario;
import yummly_app.modelo.ValoracionReceta;
import yummly_app.servicio.UsuarioServicio;

@RestController
@RequestMapping("/admin")
public class AdminControlador {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private RecetaDAO recetaDAO;
	
	@Autowired
	private ValoracionRecetaDAO valoracionRecetaDAO; 
	
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
    
    @PutMapping("/recetas/{id}/estado")
    public ResponseEntity<?> cambiarEstadoReceta(@PathVariable Long id, @RequestBody CambiarEstadoRecetaDTO dto) {
        try {
            Receta receta = recetaDAO.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

            receta.setEstado(dto.getNuevoEstado());
            recetaDAO.guardar(receta);

            return ResponseEntity.ok("Estado actualizado correctamente a: " + dto.getNuevoEstado());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado no válido.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    
    
    @GetMapping("/valoraciones/estado/{estado}")
    public ResponseEntity<?> obtenerValoracionesPorEstado(@PathVariable ValoracionReceta.EstadoValoracion estado) {
        try {
            List<ValoracionReceta> valoraciones = valoracionRecetaDAO.obtenerValoracionesPorEstado(estado);
            return ResponseEntity.ok(valoraciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al obtener valoraciones: " + e.getMessage());
        }
    }

    @PutMapping("/valoraciones/{id}/estado/{estado}")
    public ResponseEntity<?> cambiarEstadoValoracion(@PathVariable Long id, @PathVariable ValoracionReceta.EstadoValoracion estado) {
        try {
            ValoracionReceta valoracion = valoracionRecetaDAO.obtenerValoracionPorId(id)
                .orElseThrow(() -> new RuntimeException("Valoración no encontrada"));

            valoracion.setEstado(estado);
            valoracionRecetaDAO.guardarValoracion(valoracion);

            return ResponseEntity.ok("Estado de valoración actualizado correctamente a: " + estado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado no válido.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


}
