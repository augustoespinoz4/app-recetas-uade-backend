package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import yummly_app.modelo.CategoriaReceta;
import yummly_app.modelo.Receta;
import yummly_app.repositorio.RecetaRepository;

@Repository
public class RecetaDAO {

    @Autowired
    private RecetaRepository recetaRepository;

    public Receta guardar(Receta receta) {
        return recetaRepository.save(receta);
    }

    public Optional<Receta> buscarPorId(Long id) {
        return recetaRepository.findById(id);
    }
    
    // Método nuevo para buscar receta por usuario y título
    public Optional<Receta> buscarPorUsuarioYTitulo(Long idUsuario, String titulo) {
        return recetaRepository.findByUsuario_IdUsuarioAndTituloIgnoreCase(idUsuario, titulo);
    }
    
    public List<Receta> buscarTodas() {
        return recetaRepository.findAll();
    }
    
    public List<Receta> obtenerRecetasPorEstadoYVisibilidad(Receta.EstadoReceta estado, boolean publico) {
        return recetaRepository.findByEstadoAndPublico(estado, publico);
    }
    
    public List<Receta> obtenerRecetasPorEstado(Receta.EstadoReceta estado) {
    	return recetaRepository.findByEstado(estado);
    }
    
    public List<Receta> obtenerRecetasPorVisibilidad(boolean publico) {
    	return recetaRepository.findByPublico(publico);
    }
    
    public List<Receta> obtenerRecetasPorIngrediente(Long idIngrediente) {
        return recetaRepository.buscarRecetasPorIngrediente(idIngrediente);
    }
    
    public List<Receta> obtenerRecetasQueNoTienenIngrediente(Long idIngrediente) {
        return recetaRepository.buscarRecetasQueNoTienenIngrediente(idIngrediente);
    }

    public void eliminar(Long id) {
        recetaRepository.deleteById(id);
    }

    public List<Receta> buscarPorUsuario(Long idUsuario) {
        return recetaRepository.findByUsuarioIdUsuario(idUsuario);
    }
    
    public List<Receta> buscarPorAliasUsuario(String alias) {
        return recetaRepository.findByUsuarioAliasContainingIgnoreCase(alias);
    }

    public List<Receta> buscarPorTitulo(String titulo) {
        return recetaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Receta> buscarPorCategoria(CategoriaReceta categoria) {
        return recetaRepository.findByCategoria(categoria);
    }
    
    
    
}
