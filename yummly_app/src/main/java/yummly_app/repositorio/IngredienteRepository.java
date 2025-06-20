package yummly_app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    Optional<Ingrediente> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
