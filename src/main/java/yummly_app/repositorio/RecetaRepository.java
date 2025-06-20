package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yummly_app.modelo.CategoriaReceta;
import yummly_app.modelo.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByUsuarioIdUsuario(Long idUsuario);
    List<Receta> findByTituloContainingIgnoreCase(String titulo);
    List<Receta> findByCategoria(CategoriaReceta categoria);
    List<Receta> findByUsuarioAliasContainingIgnoreCase(String alias);
    List<Receta> findByEstado(Receta.EstadoReceta estado);
    List<Receta> findByPublico(boolean publico);
    List<Receta> findByEstadoAndPublico(Receta.EstadoReceta estado, boolean publico);
    @Query(value = """
    	    SELECT * FROM Receta r
    	    WHERE NOT EXISTS (
    	        SELECT 1
    	        FROM Receta_Ingrediente ri
    	        WHERE ri.id_receta = r.id_receta AND ri.id_ingrediente = :idIngrediente
    	    )
    	    """, nativeQuery = true)
    	List<Receta> buscarRecetasQueNoTienenIngrediente(@Param("idIngrediente") Long idIngrediente);

}