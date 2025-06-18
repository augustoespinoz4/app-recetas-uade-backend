package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.MultimediaReceta;

public interface MultimediaRecetaRepository extends JpaRepository<MultimediaReceta, Long> {
    List<MultimediaReceta> findByReceta_IdReceta(Long idReceta);
}
