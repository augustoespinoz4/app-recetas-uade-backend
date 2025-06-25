package yummly_app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yummly_app.modelo.ListaRecetasIntentarDetalle;
import yummly_app.modelo.ListaRecetasIntentarDetalleId;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Usuario;

public interface ListaRecetasIntentarDetalleRepository extends JpaRepository<ListaRecetasIntentarDetalle, ListaRecetasIntentarDetalleId> {

    @Query("SELECT d.receta FROM ListaRecetasIntentarDetalle d WHERE d.lista.usuario = :usuario")
    List<Receta> findRecetasByUsuario(@Param("usuario") Usuario usuario);

}
