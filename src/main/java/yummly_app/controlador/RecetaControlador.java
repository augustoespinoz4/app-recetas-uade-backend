package yummly_app.controlador;

import java.util.Comparator;
import java.util.List;
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

import yummly_app.dao.ListaRecetasIntentarDAO;
import yummly_app.dao.RecetaDAO;
import yummly_app.dao.UsuarioDAO;
import yummly_app.dao.ValoracionRecetaDAO;
import yummly_app.dto.CrearRecetaDTO;
import yummly_app.dto.EnviarValoracionRecetaDTO;
import yummly_app.dto.ModificarRecetaDTO;
import yummly_app.dto.RecetaRespuestaDTO;
import yummly_app.dto.UsuarioBasicoDTO;
import yummly_app.dto.ValoracionRecetaDTO;
import yummly_app.mapper.RecetaMapper;
import yummly_app.mapper.ValoracionRecetaMapper;
import yummly_app.modelo.CategoriaReceta;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Usuario;
import yummly_app.modelo.ValoracionReceta;
import yummly_app.servicio.RecetaServicio;

@RestController
@RequestMapping("/recetas")
public class RecetaControlador {

    @Autowired
    private RecetaDAO recetaDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired
    private ListaRecetasIntentarDAO listaRecetasIntentarDAO;
    
    @Autowired 
    private ValoracionRecetaDAO valoracionRecetaDAO;
    
    @Autowired
    private RecetaServicio recetaServicio;


    // Crear receta
    @PostMapping("")
    public ResponseEntity<String> crearReceta(@RequestBody CrearRecetaDTO dto) {
        try {
            recetaServicio.crearReceta(dto);
            return ResponseEntity.ok("Receta creada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear receta: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error inesperado: " + e.getMessage());
        }
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
    
    // Obtener recetas que tienen un ingrediente específico
    @GetMapping("/ingrediente/{idIngrediente}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasPorIngrediente(@PathVariable Long idIngrediente) {
        List<Receta> recetas = recetaDAO.obtenerRecetasPorIngrediente(idIngrediente);

        List<RecetaRespuestaDTO> recetaDTOs = recetas.stream()
            .filter(Receta::isPublico)
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(recetaDTOs);
    }

    // Obtener recetas que NO tienen un ingrediente específico
    @GetMapping("/ingrediente/{idIngrediente}/sin")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasPorNoIngrediente(@PathVariable Long idIngrediente) {
        List<Receta> recetas = recetaDAO.obtenerRecetasQueNoTienenIngrediente(idIngrediente);

        List<RecetaRespuestaDTO> recetaDTOs = recetas.stream()
            .filter(Receta::isPublico)
            .map(RecetaMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(recetaDTOs);
    }
    
    // Obtener recetas guardadas por un usuario
    @GetMapping("/lista-recetas-intentar/{idUsuario}")
    public ResponseEntity<List<RecetaRespuestaDTO>> obtenerRecetasIntentarPorUsuario(@PathVariable Long idUsuario) {
        try {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            List<Receta> recetasGuardadas = listaRecetasIntentarDAO.obtenerRecetasGuardadasUsuario(usuario);

            List<RecetaRespuestaDTO> recetaDTOs = recetasGuardadas.stream()
                .map(RecetaMapper::toDTO)
                .collect(Collectors.toList());

            return ResponseEntity.ok(recetaDTOs);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
    
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeRecetaUsuario(@RequestParam Long idUsuario, @RequestParam String titulo) {
        boolean existe = recetaDAO.buscarPorUsuarioYTitulo(idUsuario, titulo).isPresent();
        return ResponseEntity.ok(existe);
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
    public ResponseEntity<?> actualizarReceta(@PathVariable Long id, @RequestBody ModificarRecetaDTO dto) {
        try {
            recetaServicio.modificarReceta(id, dto);
            return ResponseEntity.ok("Receta actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al modificar receta: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/visibilidad")
    public ResponseEntity<String> cambiarVisibilidad(@PathVariable Long id) {
        try {
            recetaServicio.cambiarVisibilidad(id);
            return ResponseEntity.ok("Visibilidad cambiada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    // Obtener valoraciones aprobadas de una receta
    @GetMapping("/{idReceta}/valoraciones")
    public ResponseEntity<List<ValoracionRecetaDTO>> obtenerValoracionesAprobadasPorReceta(@PathVariable Long idReceta) {
        try {
            List<ValoracionRecetaDTO> valoraciones = valoracionRecetaDAO.obtenerValoracionesAprobadas(idReceta)
                .stream()
                .map(v -> new ValoracionRecetaDTO(
                    new UsuarioBasicoDTO(v.getUsuario().getIdUsuario(), v.getUsuario().getAlias()),
                    v.getPuntaje(),
                    v.getComentario(),
                    v.getEstado(),
                    v.getFechaValoracion()
                ))
                .collect(Collectors.toList());

            return ResponseEntity.ok(valoraciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/valorar")
    public ResponseEntity<?> valorarReceta(
        @PathVariable Long id,
        @RequestBody EnviarValoracionRecetaDTO dto
    ) {
        try {
            recetaServicio.valorarReceta(id, dto);
            return ResponseEntity.ok("Valoración registrada correctamente. Será revisada antes de ser publicada.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
