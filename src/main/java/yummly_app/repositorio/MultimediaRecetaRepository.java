package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import yummly_app.modelo.MultimediaReceta;
import yummly_app.modelo.Receta;

public interface MultimediaRecetaRepository extends JpaRepository<MultimediaReceta, Long> {
    List<MultimediaReceta> findByReceta_IdReceta(Long idReceta);
    @Modifying
    void deleteByReceta(Receta receta);
}
