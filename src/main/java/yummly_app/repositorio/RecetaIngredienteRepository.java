package yummly_app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import yummly_app.modelo.Receta;
import yummly_app.modelo.RecetaIngrediente;
import yummly_app.modelo.RecetaIngredienteId;

public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, RecetaIngredienteId> {
	
	
    List<RecetaIngrediente> findAllByRecetaIdReceta(Long idReceta);
    List<RecetaIngrediente> findByIngredienteIdIngrediente(Long idIngrediente);
    
    // Para buscar un ingrediente específico de una receta
    Optional<RecetaIngrediente> findByRecetaIdRecetaAndIngredienteIdIngrediente(Long idReceta, Long idIngrediente);

    // Para eliminar un ingrediente específico de una receta
    @Modifying
    void deleteByRecetaIdRecetaAndIngredienteIdIngrediente(Long idReceta, Long idIngrediente);
    
    @Modifying
    void deleteAllByReceta(Receta receta);
}