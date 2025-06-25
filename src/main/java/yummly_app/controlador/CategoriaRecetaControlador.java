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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yummly_app.dao.CategoriaRecetaDAO;
import yummly_app.modelo.CategoriaReceta;

@RestController
@RequestMapping("/categoria-recetas")
public class CategoriaRecetaControlador {

	@Autowired
	private CategoriaRecetaDAO categoriaRecetaDAO;
	
    // GET /categoria-recetas → lista todas las categorías
    @GetMapping("")
    public ResponseEntity<List<CategoriaReceta>> listarCategorias() {
        List<CategoriaReceta> categorias = categoriaRecetaDAO.obtenerCategoriasRecetas();
        return ResponseEntity.ok(categorias);
    }

    // GET /categoria-recetas/{nombre} → obtener una categoría por nombre
    @GetMapping("/{nombre}")
    public ResponseEntity<?> obtenerCategoriaPorNombre(@PathVariable String nombre) {
        Optional<CategoriaReceta> categoriaOpt = categoriaRecetaDAO.obtenerCategoriaPorNombre(nombre);
        if (categoriaOpt.isPresent()) {
            return ResponseEntity.ok(categoriaOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría con nombre '" + nombre + "' no encontrada");
        }
    }

    // GET /categoria-recetas/existe/{nombre} → verificar existencia de categoría por nombre
    @GetMapping("/existe/{nombre}")
    public ResponseEntity<Boolean> existeCategoria(@PathVariable String nombre) {
        boolean existe = categoriaRecetaDAO.existeCategoriaPorNombre(nombre);
        return ResponseEntity.ok(existe);
    }
    
    // POST /categoria-recetas → crear nueva categoría
    @PostMapping("")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaReceta categoria) {
        try {
            CategoriaReceta creada = categoriaRecetaDAO.crearCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear categoría: " + e.getMessage());
        }
    }

    // DELETE /categoria-recetas/{nombre} → eliminar categoría por nombre
    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable String nombre) {
        try {
            categoriaRecetaDAO.eliminarCategoriaPorNombre(nombre);
            return ResponseEntity.ok("Categoría eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar categoría: " + e.getMessage());
        }
    }
}
