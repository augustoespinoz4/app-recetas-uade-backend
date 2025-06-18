package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.PasoReceta;

public interface PasoRecetaRepository extends JpaRepository<PasoReceta, Long> {
    List<PasoReceta> findByReceta_IdRecetaOrderByNumeroPasoAsc(Long idReceta);
}

