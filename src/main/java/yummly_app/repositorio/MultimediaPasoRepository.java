package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yummly_app.modelo.MultimediaPaso;
import yummly_app.modelo.Receta;

public interface MultimediaPasoRepository extends JpaRepository<MultimediaPaso, Long> {
    List<MultimediaPaso> findByPaso_IdPaso(Long idPaso);
    @Modifying
    @Query(
        value = """
            DELETE FROM Multimedia_Paso
            WHERE id_paso IN (
                SELECT id_paso FROM Paso_Receta WHERE id_receta = :idReceta
            )
        """,
        nativeQuery = true
    )
    void deleteByRecetaId(@Param("idReceta") Long idReceta);
}
