package yummly_app.servicio;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yummly_app.dao.CategoriaRecetaDAO;
import yummly_app.dao.IngredienteDAO;
import yummly_app.dao.MultimediaPasoDAO;
import yummly_app.dao.MultimediaRecetaDAO;
import yummly_app.dao.PasoRecetaDAO;
import yummly_app.dao.RecetaDAO;
import yummly_app.dao.RecetaIngredienteDAO;
import yummly_app.dao.UsuarioDAO;
import yummly_app.dao.ValoracionRecetaDAO;
import yummly_app.dto.CrearRecetaDTO;
import yummly_app.dto.EnviarValoracionRecetaDTO;
import yummly_app.dto.IngredienteDTO;
import yummly_app.dto.IngredienteRecetaDTO;
import yummly_app.dto.ModificarRecetaDTO;
import yummly_app.dto.MultimediaPasoDTO;
import yummly_app.dto.MultimediaRecetaDTO;
import yummly_app.dto.PasoRecetaDTO;
import yummly_app.dto.UsuarioBasicoDTO;
import yummly_app.dto.ValoracionRecetaDTO;
import yummly_app.modelo.CategoriaReceta;
import yummly_app.modelo.Ingrediente;
import yummly_app.modelo.MultimediaPaso;
import yummly_app.modelo.MultimediaReceta;
import yummly_app.modelo.PasoReceta;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Receta.EstadoReceta;
import yummly_app.modelo.RecetaIngrediente;
import yummly_app.modelo.Usuario;
import yummly_app.modelo.ValoracionReceta;

@Service
public class RecetaServicio {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private CategoriaRecetaDAO categoriaRecetaDAO;
	
	@Autowired
	private RecetaDAO recetaDAO;
	
	@Autowired
	private IngredienteDAO ingredienteDAO;
	
	@Autowired
	private RecetaIngredienteDAO recetaIngredienteDAO;
	
	@Autowired
	private PasoRecetaDAO pasoRecetaDAO;
	
	@Autowired
	private MultimediaPasoDAO multimediaPasoDAO;
	
	@Autowired
	private MultimediaRecetaDAO multimediaRecetaDAO;
	
	@Autowired
	private ValoracionRecetaDAO valoracionRecetaDAO;
	
	@Transactional
	public void crearReceta(CrearRecetaDTO dto) {
	    // Validaciones básicas
	    if (dto.getPasos() == null || dto.getPasos().isEmpty()) {
	        throw new RuntimeException("La receta debe tener al menos un paso.");
	    }
	    if (dto.getMultimediaReceta() == null || dto.getMultimediaReceta().isEmpty()) {
	        throw new RuntimeException("La receta debe tener al menos una imagen multimedia.");
	    }

	    // 1. Obtener usuario y categoría
	    Usuario usuario = usuarioDAO.obtenerUsuarioPorId(dto.getIdUsuario())
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    CategoriaReceta categoria = categoriaRecetaDAO.obtenerCategoriaPorNombre(dto.getCategoria())
	            .orElseThrow(() -> new RuntimeException("Categoría no válida"));

	    // 2. Crear y guardar la receta
	    Receta receta = new Receta();
	    receta.setUsuario(usuario);
	    receta.setTitulo(dto.getTitulo());
	    receta.setDescripcion(dto.getDescripcion());
	    receta.setCantidadPersonas(dto.getCantidadPersonas());
	    receta.setPublico(dto.isPublico());
	    receta.setCategoria(categoria);
	    receta.setEstado(EstadoReceta.pendiente);
	    recetaDAO.guardar(receta); // se genera idReceta

	    // 3. Ingredientes
	    for (IngredienteRecetaDTO ing : dto.getIngredientes()) {
	        Ingrediente ingrediente = ingredienteDAO.obtenerIngredientePorNombre(ing.getNombre())
	                .orElseThrow(() -> new RuntimeException("Ingrediente con nombre '" + ing.getNombre() + "' no válido"));

	        RecetaIngrediente ri = new RecetaIngrediente();
	        ri.setReceta(receta);
	        ri.setIngrediente(ingrediente);
	        ri.setCantidad(ing.getCantidad());
	        ri.setUnidadMedida(ing.getUnidadMedida());
	        recetaIngredienteDAO.guardarIngrediente(ri);
	    }

	    // 4. Pasos y multimedia de pasos
	    for (PasoRecetaDTO pasoDTO : dto.getPasos()) {
	        PasoReceta paso = new PasoReceta();
	        paso.setReceta(receta);
	        paso.setNumeroPaso(pasoDTO.getNumeroPaso());
	        paso.setDescripcion(pasoDTO.getDescripcion());
	        pasoRecetaDAO.guardarPaso(paso);

	        if (pasoDTO.getMultimedia() != null) {
	            for (MultimediaPasoDTO media : pasoDTO.getMultimedia()) {
	                MultimediaPaso mp = new MultimediaPaso();
	                mp.setPaso(paso);
	                mp.setTipo(media.getTipo());
	                mp.setUrl(media.getUrl());
	                multimediaPasoDAO.guardarMultimediaPaso(mp);
	            }
	        }
	    }

	    // 5. Multimedia receta
	    for (MultimediaRecetaDTO media : dto.getMultimediaReceta()) {
	        MultimediaReceta m = new MultimediaReceta();
	        m.setReceta(receta);
	        m.setTipo(media.getTipo());
	        m.setUrl(media.getUrl());
	        multimediaRecetaDAO.guardarMultimedia(m);
	    }
	}
	
	@Transactional
	public void modificarReceta(Long id, ModificarRecetaDTO dto) {
	    Receta receta = recetaDAO.buscarPorId(id)
	        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

	    receta.setTitulo(dto.getTitulo());
	    receta.setDescripcion(dto.getDescripcion());
	    receta.setCantidadPersonas(dto.getCantidadPersonas());
	    receta.setPublico(dto.isPublico());

	    CategoriaReceta categoria = categoriaRecetaDAO.obtenerCategoriaPorNombre(dto.getCategoria())
	        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
	    receta.setCategoria(categoria);

	    // Marcar estado como pendiente si estaba aprobada/rechazada
	    if (receta.getEstado() != Receta.EstadoReceta.pendiente) {
	        receta.setEstado(Receta.EstadoReceta.pendiente);
	    }

	    // Limpiar y reemplazar ingredientes
	    receta.getIngredientes().clear();
	    for (IngredienteDTO ingDTO : dto.getIngredientes()) {
	        Ingrediente ingrediente = ingredienteDAO.obtenerIngredientePorId(ingDTO.getIdIngrediente())
	            .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
	        RecetaIngrediente ri = new RecetaIngrediente(receta, ingrediente, ingDTO.getCantidad(), ingDTO.getUnidadMedida());
	        receta.getIngredientes().add(ri);
	    }

	    // Limpiar y reemplazar multimedia de receta
	    receta.getMultimedia().clear();
	    for (MultimediaRecetaDTO m : dto.getMultimediaReceta()) {
	        receta.getMultimedia().add(new MultimediaReceta(null, receta, m.getTipo(), m.getUrl()));
	    }

	    // Limpiar y reemplazar pasos y su multimedia
	    receta.getPasos().clear();
	    for (PasoRecetaDTO pasoDTO : dto.getPasos()) {
	        PasoReceta paso = new PasoReceta();
	        paso.setReceta(receta);
	        paso.setNumeroPaso(pasoDTO.getNumeroPaso());
	        paso.setDescripcion(pasoDTO.getDescripcion());

	        if (pasoDTO.getMultimedia() != null) {
	            for (MultimediaPasoDTO m : pasoDTO.getMultimedia()) {
	                MultimediaPaso mp = new MultimediaPaso(null, paso, m.getTipo(), m.getUrl());
	                paso.getMultimedia().add(mp);
	            }
	        }

	        receta.getPasos().add(paso);
	    }

	    recetaDAO.guardar(receta); // save actualiza por cascada
	}
	
	@Transactional
	public PasoReceta agregarPasoAReceta(Long idReceta, PasoRecetaDTO pasoDTO) {
	    Receta receta = recetaDAO.buscarPorId(idReceta)
	        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

	    int nuevoNumeroPaso = receta.getPasos().size() + 1;

	    PasoReceta paso = new PasoReceta();
	    paso.setReceta(receta);
	    paso.setNumeroPaso(nuevoNumeroPaso); // Asignación automática
	    paso.setDescripcion(pasoDTO.getDescripcion());

	    return pasoRecetaDAO.guardarPaso(paso);
	}
	
	@Transactional
	public void eliminarPasoRecetaPorId(Long idPaso) {
	    PasoReceta paso = pasoRecetaDAO.obtenerPasoPorId(idPaso)
	        .orElseThrow(() -> new RuntimeException("Paso no encontrado"));

	    Long idReceta = paso.getReceta().getIdReceta();

	    pasoRecetaDAO.eliminarPasoPorId(idPaso);

	    Receta receta = recetaDAO.buscarPorId(idReceta)
	        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

	    reordenarNumerosDePaso(receta);
	    recetaDAO.guardar(receta);
	}
	
	@Transactional
	public MultimediaReceta agregarMultimediaAReceta(Long idReceta, MultimediaRecetaDTO dto) {
	    Receta receta = recetaDAO.buscarPorId(idReceta)
	        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

	    MultimediaReceta multimedia = new MultimediaReceta();
	    multimedia.setReceta(receta);
	    multimedia.setTipo(dto.getTipo());
	    multimedia.setUrl(dto.getUrl());

	    return multimediaRecetaDAO.guardarMultimedia(multimedia);
	}
	
	@Transactional
	public void eliminarMultimediaDeReceta(Long idMultimedia) {
	    multimediaRecetaDAO.eliminarMultimediaPorId(idMultimedia);
	}
	
	@Transactional
	public void cambiarVisibilidad(Long idReceta) {
	    Receta receta = recetaDAO.buscarPorId(idReceta)
	        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

	    receta.setPublico(!receta.isPublico()); // invertimos el valor actual
	    recetaDAO.guardar(receta);
	}
	
	public List<ValoracionRecetaDTO> obtenerValoracionesPorReceta(Long idReceta) {
	    List<ValoracionReceta> valoraciones = valoracionRecetaDAO.obtenerValoracionesAprobadas(idReceta);

	    return valoraciones.stream()
	        .map(v -> new ValoracionRecetaDTO(
	            new UsuarioBasicoDTO(
	                v.getUsuario().getIdUsuario(),
	                v.getUsuario().getAlias()
	            ),
	            v.getPuntaje(),
	            v.getComentario(),
	            v.getEstado(),
	            v.getFechaValoracion()
	        ))
	        .collect(Collectors.toList());
	}

	
	@Transactional
	public void valorarReceta(Long idReceta, EnviarValoracionRecetaDTO dto) {
		if (dto.getPuntaje() < 1 || dto.getPuntaje() > 5) {
		    throw new RuntimeException("El puntaje debe estar entre 1 y 5.");
		}
		
	    Receta receta = recetaDAO.buscarPorId(idReceta)
	        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

	    Usuario usuario = usuarioDAO.obtenerUsuarioPorId(dto.getIdUsuario())
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    // Si ya valoró, actualizar la valoración existente
	    Optional<ValoracionReceta> existente = valoracionRecetaDAO
	        .obtenerValoracionPorRecetaYUsuario(idReceta, dto.getIdUsuario());

	    ValoracionReceta valoracion = existente.orElseGet(() -> {
	        ValoracionReceta nueva = new ValoracionReceta();
	        nueva.setReceta(receta);
	        nueva.setUsuario(usuario);
	        nueva.setFechaValoracion(LocalDateTime.now());
	        return nueva;
	    });

	    valoracion.setPuntaje(dto.getPuntaje());
	    valoracion.setComentario(dto.getComentario());
	    valoracion.setEstado(ValoracionReceta.EstadoValoracion.pendiente); // Vuelve a pendiente si ya existía

	    valoracionRecetaDAO.guardarValoracion(valoracion);
	}

	private void reordenarNumerosDePaso(Receta receta) {
	    List<PasoReceta> pasos = receta.getPasos().stream()
	        .sorted(Comparator.comparing(PasoReceta::getNumeroPaso))
	        .collect(Collectors.toList());

	    for (int i = 0; i < pasos.size(); i++) {
	        pasos.get(i).setNumeroPaso(i + 1);
	    }
	}
}
