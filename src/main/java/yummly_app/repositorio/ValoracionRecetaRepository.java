package yummly_app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yummly_app.modelo.ValoracionReceta;

public interface ValoracionRecetaRepository extends JpaRepository<ValoracionReceta, Long> {
    List<ValoracionReceta> findByReceta_IdReceta(Long idReceta);
    List<ValoracionReceta> findByReceta_IdRecetaAndEstado(Long idReceta, ValoracionReceta.EstadoValoracion estado);
    Optional<ValoracionReceta> findByReceta_IdRecetaAndUsuario_IdUsuario(Long idReceta, Long idUsuario);
    @Query(
    	    value = """
    	        SELECT * 
    	        FROM Valoracion_Receta 
    	        WHERE id_receta = :idReceta AND estado = 'APROBADO'
    	        ORDER BY fecha_valoracion DESC
    	        """,
    	    nativeQuery = true
    	)
	List<ValoracionReceta> obtenerValoracionesAprobadasPorIdReceta(@Param("idReceta") Long idReceta);
    @Query(
    	    value = """
    	        SELECT * 
    	        FROM Valoracion_Receta 
    	        WHERE id_receta = :idReceta AND estado = 'PENDIENTE'
    	        ORDER BY fecha_valoracion DESC
    	        """,
    	    nativeQuery = true
    	)
	List<ValoracionReceta> obtenerValoracionesPendientes(@Param("idReceta") Long idReceta);
}
