package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.MultimediaReceta;
import yummly_app.repositorio.MultimediaRecetaRepository;

@Repository
public class MultimediaRecetaDAO {

    @Autowired
    private MultimediaRecetaRepository multimediaRecetaRepository;

    // Crear o guardar multimedia
    public MultimediaReceta guardarMultimedia(MultimediaReceta multimedia) {
        return multimediaRecetaRepository.save(multimedia);
    }

    // Obtener multimedia por ID
    public Optional<MultimediaReceta> obtenerMultimediaPorId(Long idMultimedia) {
        return multimediaRecetaRepository.findById(idMultimedia);
    }

    // Obtener todas las multimedia de una receta
    public List<MultimediaReceta> obtenerMultimediaPorIdReceta(Long idReceta) {
        return multimediaRecetaRepository.findByReceta_IdReceta(idReceta);
    }

    // Eliminar multimedia por ID
    @Transactional
    public void eliminarMultimediaPorId(Long idMultimedia) {
        multimediaRecetaRepository.deleteById(idMultimedia);
    }

    // Eliminar todas las multimedia asociadas a una receta
    @Transactional
    public void eliminarMultimediaPorIdReceta(Long idReceta) {
        List<MultimediaReceta> multimedia = multimediaRecetaRepository.findByReceta_IdReceta(idReceta);
        multimediaRecetaRepository.deleteAll(multimedia);
    }
}
