package yummly_app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.ListaRecetasIntentar;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Usuario;

public interface ListaRecetasIntentarRepository extends JpaRepository<ListaRecetasIntentar, Long> {
    Optional<ListaRecetasIntentar> findByUsuarioIdUsuario(Long idUsuario);
    Optional<ListaRecetasIntentar> findByUsuario(Usuario usuario);
    
}