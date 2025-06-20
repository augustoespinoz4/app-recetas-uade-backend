package yummly_app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.ValoracionReceta;

public interface ValoracionRecetaRepository extends JpaRepository<ValoracionReceta, Long> {
    List<ValoracionReceta> findByReceta_IdReceta(Long idReceta);
    List<ValoracionReceta> findByReceta_IdRecetaAndEstado(Long idReceta, ValoracionReceta.EstadoValoracion estado);
    Optional<ValoracionReceta> findByReceta_IdRecetaAndUsuario_IdUsuario(Long idReceta, Long idUsuario);
}
