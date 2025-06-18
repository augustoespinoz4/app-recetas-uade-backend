package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.PasoReceta;
import yummly_app.repositorio.PasoRecetaRepository;

@Repository
public class PasoRecetaDAO {

    @Autowired
    private PasoRecetaRepository pasoRecetaRepository;

    // Crear o guardar un paso
    public PasoReceta guardarPaso(PasoReceta paso) {
        return pasoRecetaRepository.save(paso);
    }

    // Obtener paso por ID
    public Optional<PasoReceta> obtenerPasoPorId(Long idPaso) {
        return pasoRecetaRepository.findById(idPaso);
    }

    // Obtener todos los pasos de una receta, ordenados
    public List<PasoReceta> obtenerPasosDeReceta(Long idReceta) {
        return pasoRecetaRepository.findByReceta_IdRecetaOrderByNumeroPasoAsc(idReceta);
    }

    // Eliminar un paso por ID
    @Transactional
    public void eliminarPasoPorId(Long idPaso) {
        pasoRecetaRepository.deleteById(idPaso);
    }

    // Eliminar todos los pasos de una receta
    @Transactional
    public void eliminarPasosPorIdReceta(Long idReceta) {
        List<PasoReceta> pasos = pasoRecetaRepository.findByReceta_IdRecetaOrderByNumeroPasoAsc(idReceta);
        pasoRecetaRepository.deleteAll(pasos);
    }
}
