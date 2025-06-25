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

import yummly_app.dao.IngredienteDAO;
import yummly_app.modelo.Ingrediente;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteControlador {

    @Autowired
    private IngredienteDAO ingredienteDAO;

    // GET /ingredientes → lista todos los ingredientes
    @GetMapping("")
    public ResponseEntity<List<Ingrediente>> obtenerTodos() {
        List<Ingrediente> ingredientes = ingredienteDAO.obtenerTodosLosIngredientes();
        return ResponseEntity.ok(ingredientes);
    }

    // GET /ingredientes/{id} → obtener ingrediente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return ingredienteDAO.obtenerIngredientePorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Ingrediente no encontrado con id " + id));
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> obtenerIngredientePorNombre(@PathVariable String nombre) {
        Optional<Ingrediente> ingredienteOpt = ingredienteDAO.obtenerIngredientePorNombre(nombre);
        if (ingredienteOpt.isPresent()) {
            return ResponseEntity.ok(ingredienteOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ingrediente no encontrado con nombre: " + nombre);
        }
    }

    // POST /ingredientes → crear nuevo ingrediente
    @PostMapping("")
    public ResponseEntity<?> crearIngrediente(@RequestBody Ingrediente ingrediente) {
        if (ingredienteDAO.existePorNombre(ingrediente.getNombre())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un ingrediente con ese nombre");
        }
        Ingrediente creado = ingredienteDAO.crearIngrediente(ingrediente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /ingredientes/{id} → actualizar ingrediente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarIngrediente(
            @PathVariable Long id,
            @RequestBody Ingrediente ingredienteActualizado) {
        try {
            Ingrediente actualizado = ingredienteDAO.actualizarIngrediente(id, ingredienteActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // DELETE /ingredientes/{id} → eliminar ingrediente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarIngrediente(@PathVariable Long id) {
        if (!ingredienteDAO.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ingrediente no encontrado con id " + id);
        }
        ingredienteDAO.eliminarIngredientePorId(id);
        return ResponseEntity.noContent().build();
    }
}

