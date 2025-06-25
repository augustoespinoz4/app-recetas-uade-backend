package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.CategoriaReceta;
import yummly_app.repositorio.CategoriaRecetaRepository;

@Repository
public class CategoriaRecetaDAO {
	
	@Autowired
	CategoriaRecetaRepository categoriaRecetaRepository;
	
    public List<CategoriaReceta> obtenerCategoriasRecetas() {
        return categoriaRecetaRepository.findAll();
    }

    public Optional<CategoriaReceta> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRecetaRepository.findByNombre(nombre);
    }

    public boolean existeCategoriaPorNombre(String nombre) {
        return categoriaRecetaRepository.existsByNombre(nombre);
    }	
    
    @Transactional
    public CategoriaReceta crearCategoria(CategoriaReceta categoria) {
        if (existeCategoriaPorNombre(categoria.getNombre())) {
            throw new RuntimeException("La categoría ya existe");
        }
        return categoriaRecetaRepository.save(categoria);
    }

    @Transactional
    public void eliminarCategoriaPorNombre(String nombre) {
        Optional<CategoriaReceta> categoriaOpt = obtenerCategoriaPorNombre(nombre);
        if (categoriaOpt.isPresent()) {
            categoriaRecetaRepository.delete(categoriaOpt.get());
        } else {
            throw new RuntimeException("Categoría no encontrada");
        }
    }	
}
