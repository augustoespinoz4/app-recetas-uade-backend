package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.CategoriaReceta;
import yummly_app.modelo.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByUsuarioIdUsuario(Long idUsuario);
    List<Receta> findByTituloContainingIgnoreCase(String titulo);
    List<Receta> findByCategoria(CategoriaReceta categoria);
    List<Receta> findByUsuarioAliasContainingIgnoreCase(String alias);
    List<Receta> findByEstado(Receta.EstadoReceta estado);
    List<Receta> findByPublico(boolean publico);
    List<Receta> findByEstadoAndPublico(Receta.EstadoReceta estado, boolean publico);
}