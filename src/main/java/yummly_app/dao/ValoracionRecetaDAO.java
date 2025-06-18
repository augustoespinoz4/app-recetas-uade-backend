package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.ValoracionReceta;
import yummly_app.modelo.ValoracionReceta.EstadoValoracion;
import yummly_app.repositorio.ValoracionRecetaRepository;

@Repository
public class ValoracionRecetaDAO {

    @Autowired
    private ValoracionRecetaRepository valoracionRecetaRepository;

    // Guardar o actualizar una valoración
    public ValoracionReceta guardarValoracion(ValoracionReceta valoracion) {
        return valoracionRecetaRepository.save(valoracion);
    }

    // Obtener valoración por receta y usuario
    public Optional<ValoracionReceta> obtenerValoracionPorRecetaYUsuario(Long idReceta, Long idUsuario) {
        return valoracionRecetaRepository.findByReceta_IdRecetaAndUsuario_IdUsuario(idReceta, idUsuario);
    }

    // Obtener todas las valoraciones de una receta
    public List<ValoracionReceta> obtenerValoracionesPorReceta(Long idReceta) {
        return valoracionRecetaRepository.findByReceta_IdReceta(idReceta);
    }

    // Obtener valoraciones filtradas por estado
    public List<ValoracionReceta> obtenerValoracionesPorRecetaYEstado(Long idReceta, EstadoValoracion estado) {
        return valoracionRecetaRepository.findByReceta_IdRecetaAndEstado(idReceta, estado);
    }
    
    // Obtener valoraciones aprobadas
    public List<ValoracionReceta> obtenerValoracionesAprobadas(Long idReceta) {
        return valoracionRecetaRepository.obtenerValoracionesAprobadasPorIdReceta(idReceta);
    }

    // Obtener valoraciones pendientes
    public List<ValoracionReceta> obtenerValoracionesPendientes(Long idReceta) {
        return valoracionRecetaRepository.obtenerValoracionesPendientes(idReceta);
    }

    // Eliminar valoración por ID
    @Transactional
    public void eliminarValoracionPorId(Long idValoracion) {
        valoracionRecetaRepository.deleteById(idValoracion);
    }
}
