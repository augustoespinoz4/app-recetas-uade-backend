package yummly_app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.CategoriaReceta;

public interface CategoriaRecetaRepository extends JpaRepository<CategoriaReceta, String> {
	Optional<CategoriaReceta> findByNombre(String nombre);
	boolean existsByNombre(String nombre);
}
