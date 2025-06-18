package yummly_app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import yummly_app.modelo.RecetaIngrediente;
import yummly_app.modelo.RecetaIngredienteId;
import yummly_app.repositorio.RecetaIngredienteRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecetaIngredienteDAO {

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    public RecetaIngrediente guardar(RecetaIngrediente recetaIngrediente) {
        return recetaIngredienteRepository.save(recetaIngrediente);
    }

    public Optional<RecetaIngrediente> buscarPorId(RecetaIngredienteId id) {
        return recetaIngredienteRepository.findById(id);
    }

    public List<RecetaIngrediente> buscarTodos() {
        return recetaIngredienteRepository.findAll();
    }

    public void eliminar(RecetaIngredienteId id) {
        recetaIngredienteRepository.deleteById(id);
    }

    public List<RecetaIngrediente> buscarPorIdReceta(Long idReceta) {
        return recetaIngredienteRepository.findByRecetaIdReceta(idReceta);
    }

    public List<RecetaIngrediente> buscarPorIdIngrediente(Long idIngrediente) {
        return recetaIngredienteRepository.findByIngredienteIdIngrediente(idIngrediente);
    }
}
