package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
