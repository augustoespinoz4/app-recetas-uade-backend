package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.MultimediaPaso;

public interface MultimediaPasoRepository extends JpaRepository<MultimediaPaso, Long> {
    List<MultimediaPaso> findByPaso_IdPaso(Long idPaso);
}
