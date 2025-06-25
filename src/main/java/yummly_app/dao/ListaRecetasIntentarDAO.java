package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.ListaRecetasIntentar;
import yummly_app.modelo.ListaRecetasIntentarDetalle;
import yummly_app.modelo.Receta;
import yummly_app.modelo.Usuario;
import yummly_app.repositorio.ListaRecetasIntentarDetalleRepository;
import yummly_app.repositorio.ListaRecetasIntentarRepository;

@Repository
public class ListaRecetasIntentarDAO {

    @Autowired
    private ListaRecetasIntentarRepository listaRepository;
    
    @Autowired
    private ListaRecetasIntentarDetalleRepository detalleRepository;


    // Crear una nueva lista
    public ListaRecetasIntentar crearLista(ListaRecetasIntentar lista) {
        return listaRepository.save(lista);
    }

    // Obtener todas las listas
    public List<ListaRecetasIntentar> obtenerTodasLasListas() {
        return listaRepository.findAll();
    }

    // Obtener lista por ID
    public Optional<ListaRecetasIntentar> obtenerListaPorId(Long idLista) {
        return listaRepository.findById(idLista);
    }

    // Obtener lista por usuario
    public Optional<ListaRecetasIntentar> obtenerListaPorUsuario(Usuario usuario) {
        return listaRepository.findByUsuario(usuario);
    }

    // Obtener lista por ID de usuario
    public Optional<ListaRecetasIntentar> obtenerListaPorIdUsuario(Long idUsuario) {
        return listaRepository.findByUsuarioIdUsuario(idUsuario);
    }
    
    public List<Receta> obtenerRecetasGuardadasUsuario(Usuario usuario) {
        return detalleRepository.findRecetasByUsuario(usuario);
    }

    // Agregar una receta a la lista del usuario
    @Transactional
    public void agregarRecetaALista(Usuario usuario, Receta receta) {
        ListaRecetasIntentar lista = listaRepository.findByUsuario(usuario)
            .orElseThrow(() -> new RuntimeException("Lista no encontrada para el usuario"));

        boolean yaExiste = lista.getDetalles().stream()
            .anyMatch(detalle -> detalle.getReceta().equals(receta));

        if (!yaExiste) {
            ListaRecetasIntentarDetalle nuevoDetalle = new ListaRecetasIntentarDetalle(lista, receta);
            lista.getDetalles().add(nuevoDetalle);
            listaRepository.save(lista);
        }
    }
    
    // Eliminar una receta de la lista del usuario
    @Transactional
    public void eliminarRecetaDeLista(Usuario usuario, Receta receta) {
        ListaRecetasIntentar lista = listaRepository.findByUsuario(usuario)
            .orElseThrow(() -> new RuntimeException("Lista no encontrada para el usuario"));

        ListaRecetasIntentarDetalle detalleAEliminar = lista.getDetalles().stream()
            .filter(detalle -> detalle.getReceta().equals(receta))
            .findFirst()
            .orElse(null);

        if (detalleAEliminar != null) {
            lista.getDetalles().remove(detalleAEliminar);
            listaRepository.save(lista);
        }
    }

    // Actualizar nombre de la lista
    @Transactional
    public ListaRecetasIntentar actualizarNombreLista(Long idLista, String nuevoNombre) {
        return listaRepository.findById(idLista)
            .map(lista -> {
                lista.setNombreLista(nuevoNombre);
                return listaRepository.save(lista);
            })
            .orElseThrow(() -> new RuntimeException("Lista no encontrada con id " + idLista));
    }

    // Eliminar lista por ID
    @Transactional
    public void eliminarListaPorId(Long idLista) {
        listaRepository.deleteById(idLista);
    }
}
