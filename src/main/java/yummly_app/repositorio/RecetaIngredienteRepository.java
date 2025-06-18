package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.RecetaIngrediente;
import yummly_app.modelo.RecetaIngredienteId;

public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, RecetaIngredienteId> {
    List<RecetaIngrediente> findByRecetaIdReceta(Long idReceta);
    List<RecetaIngrediente> findByIngredienteIdIngrediente(Long idIngrediente);
}