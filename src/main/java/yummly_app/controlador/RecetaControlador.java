package yummly_app.controlador;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import yummly_app.dao.RecetaDAO;
import yummly_app.dao.UsuarioDAO;
import yummly_app.dao.ValoracionRecetaDAO;
import yummly_app.dto.RecetaDTO;
import yummly_app.dto.RecetaRespuestaDTO;
import yummly_app.dto.ValoracionRecetaDTO;
import yummly_app.mapper.RecetaMapper;
import yummly_app.mapper.ValoracionRecetaMapper;
import yummly_app.modelo.CategoriaReceta;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Usuario;
import yummly_app.modelo.ValoracionReceta;

@RestController
@RequestMapping("/recetas")
public class RecetaControlador {

    @Autowired
    private RecetaDAO recetaDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired 
    ValoracionRecetaDAO valoracionRecetaDAO;

    // Crear receta
    @PostMapping("")
    public ResponseEntity<?> crearReceta(@RequestBody RecetaDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioDAO.obtenerUsuarioPorId(dto.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
        }

        Receta receta = new Receta();
        receta.setTitulo(dto.getTitulo());
        receta.setDescripcion(dto.getDescripcion());
        receta.setCantidadPersonas(dto.getCantidadPersonas());
        receta.setPublico(dto.isPublico());
        receta.setCategoria(dto.getCategoria());
        receta.setUsuario(usuarioOpt.get());
        receta.setFechaCreacion(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(recetaDAO.guardar(receta));
    }

    // Obtener todas las recetas públicas
    @GetMapping("")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetas() {
        List<Receta> recetas = recetaDAO.buscarTodas();

        List<RecetaRespuestaDTO> recetaDTOs = recetas.stream()
            .filter(Receta::isPublico)
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(recetaDTOs);
    }

    // Obtener recetas por usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasPorUsuario(@PathVariable Long idUsuario) {
        List<Receta> recetas = recetaDAO.buscarPorUsuario(idUsuario);
        List<RecetaRespuestaDTO> recetaDTOs = recetas.stream()
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(recetaDTOs);
    }
    
    @GetMapping("/usuario/alias/{alias}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasPorAliasUsuario(@PathVariable String alias) {
        List<Receta> recetas = recetaDAO.buscarPorAliasUsuario(alias);
        List<RecetaRespuestaDTO> recetaDTOs = recetas.stream()
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(recetaDTOs);
    }

    // Buscar por título
    @GetMapping("/buscar")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasPorTitulo(@RequestParam String titulo) {
        List<Receta> recetas = recetaDAO.buscarPorTitulo(titulo);
        List<RecetaRespuestaDTO> recetaDTOs = recetas.stream()
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(recetaDTOs);
    }

    // Buscar por categoría
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasPorCategoria(@PathVariable CategoriaReceta categoria) {
        List<Receta> recetas = recetaDAO.buscarPorCategoria(categoria);
        List<RecetaRespuestaDTO> recetaDTOs = recetas.stream()
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(recetaDTOs);
    }

    // Obtener receta por ID
    @GetMapping("/{id}")
    public ResponseEntity<RecetaRespuestaDTO> obtenerRecetaPorId(@PathVariable Long id) {
        return recetaDAO.buscarPorId(id)
            .map(RecetaMapper::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET /recetas/estado-visibilidad/{estado}/{publico}
    @GetMapping("/estado-visibilidad/{estado}/{publico}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerPorEstadoYVisibilidad(
        @PathVariable Receta.EstadoReceta estado,
        @PathVariable boolean publico) {

        List<Receta> recetas = recetaDAO.obtenerRecetasPorEstadoYVisibilidad(estado, publico);
        List<RecetaRespuestaDTO> dtos = recetas.stream()
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET /recetas/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerPorEstado(@PathVariable Receta.EstadoReceta estado) {
        List<Receta> recetas = recetaDAO.obtenerRecetasPorEstado(estado);
        List<RecetaRespuestaDTO> dtos = recetas.stream()
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET /recetas/publico/{publico}
    @GetMapping("/visibilidad/{publico}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerPorVisibilidad(@PathVariable boolean publico) {
        List<Receta> recetas = recetaDAO.obtenerRecetasPorVisibilidad(publico);
        List<RecetaRespuestaDTO> dtos = recetas.stream()
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}/valoraciones/estado/{estado}")
    public ResponseEntity<List<ValoracionRecetaDTO>> obtenerValoracionesRecetaEstado(
            @PathVariable Long id,
            @PathVariable ValoracionReceta.EstadoValoracion estado) {

        List<ValoracionReceta> valoraciones = valoracionRecetaDAO.obtenerValoracionesPorRecetaYEstado(id, estado);

        List<ValoracionRecetaDTO> dtoList = valoraciones.stream()
            .map(ValoracionRecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
    
 // GET /recetas/ordenadas?estado=aprobada&publico=true&criterio=fecha
    @GetMapping("/ordenadas")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasOrdenadas(
            @RequestParam Receta.EstadoReceta estado,
            @RequestParam boolean publico,
            @RequestParam(defaultValue = "fecha") String criterio) {

        List<Receta> recetas = recetaDAO.obtenerRecetasPorEstadoYVisibilidad(estado, publico);

        Comparator<Receta> comparador;
        switch (criterio.toLowerCase()) {
            case "nombre" -> comparador = Comparator.comparing(Receta::getTitulo, String.CASE_INSENSITIVE_ORDER);
            case "usuario" -> comparador = Comparator.comparing(r -> r.getUsuario().getAlias(), String.CASE_INSENSITIVE_ORDER);
            default -> comparador = Comparator.comparing(Receta::getFechaCreacion).reversed(); // fecha por default
        }

        List<RecetaRespuestaDTO> dtos = recetas.stream()
            .sorted(comparador)
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // Eliminar receta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReceta(@PathVariable Long id) {
        if (recetaDAO.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        recetaDAO.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar receta
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReceta(@PathVariable Long id, @RequestBody RecetaDTO dto) {
        Optional<Receta> recetaOpt = recetaDAO.buscarPorId(id);
        if (recetaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Receta receta = recetaOpt.get();
        receta.setTitulo(dto.getTitulo());
        receta.setDescripcion(dto.getDescripcion());
        receta.setCantidadPersonas(dto.getCantidadPersonas());
        receta.setPublico(dto.isPublico());
        receta.setCategoria(dto.getCategoria());

        return ResponseEntity.ok(recetaDAO.guardar(receta));
    }
}
