package yummly_app.servicio;

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
import yummly_app.dto.CrearRecetaDTO;
import yummly_app.dto.IngredienteRecetaDTO;
import yummly_app.dto.ModificarRecetaDTO;
import yummly_app.dto.MultimediaPasoDTO;
import yummly_app.dto.MultimediaRecetaDTO;
import yummly_app.dto.PasoRecetaDTO;
import yummly_app.modelo.CategoriaReceta;
import yummly_app.modelo.Ingrediente;
import yummly_app.modelo.MultimediaPaso;
import yummly_app.modelo.MultimediaReceta;
import yummly_app.modelo.PasoReceta;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Receta.EstadoReceta;
import yummly_app.modelo.RecetaIngrediente;
import yummly_app.modelo.Usuario;

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
	
	@Transactional
	public void crearReceta(CrearRecetaDTO dto) {
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

	        for (MultimediaPasoDTO media : pasoDTO.getMultimedia()) {
	            MultimediaPaso mp = new MultimediaPaso();
	            mp.setPaso(paso);
	            mp.setTipo(media.getTipo());
	            mp.setUrl(media.getUrl());
	            multimediaPasoDAO.guardarMultimediaPaso(mp);
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
}
