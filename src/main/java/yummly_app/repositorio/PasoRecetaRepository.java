package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import yummly_app.modelo.PasoReceta;
import yummly_app.modelo.Receta;

public interface PasoRecetaRepository extends JpaRepository<PasoReceta, Long> {
    List<PasoReceta> findByReceta_IdRecetaOrderByNumeroPasoAsc(Long idReceta);
    @Modifying
    void deleteByReceta(Receta receta);
}

