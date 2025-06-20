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

    public List<Receta> buscarTodas() {
        return recetaRepository.findAll();
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
