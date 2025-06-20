package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.MultimediaPaso;
import yummly_app.repositorio.MultimediaPasoRepository;

@Repository
public class MultimediaPasoDAO {

    @Autowired
    private MultimediaPasoRepository multimediaPasoRepository;

    // Crear o guardar multimedia
    public MultimediaPaso guardarMultimediaPaso(MultimediaPaso multimedia) {
        return multimediaPasoRepository.save(multimedia);
    }

    // Obtener multimedia por ID
    public Optional<MultimediaPaso> obtenerMultimediaPasoPorId(Long idMultimedia) {
        return multimediaPasoRepository.findById(idMultimedia);
    }

    // Obtener multimedia asociada a un paso
    public List<MultimediaPaso> obtenerMultimediaPorIdPaso(Long idPaso) {
        return multimediaPasoRepository.findByPaso_IdPaso(idPaso);
    }

    // Eliminar multimedia por ID
    @Transactional
    public void eliminarMultimediaPasoPorId(Long idMultimedia) {
        multimediaPasoRepository.deleteById(idMultimedia);
    }

    // Eliminar todas las multimedia de un paso
    @Transactional
    public void eliminarMultimediaPorPaso(Long idPaso) {
        List<MultimediaPaso> multimedia = multimediaPasoRepository.findByPaso_IdPaso(idPaso);
        multimediaPasoRepository.deleteAll(multimedia);
    }
}
