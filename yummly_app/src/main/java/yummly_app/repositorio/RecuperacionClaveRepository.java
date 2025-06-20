package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.RecuperacionClave;

public interface RecuperacionClaveRepository extends JpaRepository<RecuperacionClave, Long> {
    List<RecuperacionClave> findByUsuarioIdUsuario(Long idUsuario);
}