package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import yummly_app.modelo.Receta;
import yummly_app.modelo.RecetaIngrediente;
import yummly_app.modelo.RecetaIngredienteId;
import yummly_app.repositorio.RecetaIngredienteRepository;

@Repository
public class RecetaIngredienteDAO {

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    public RecetaIngrediente guardarIngrediente(RecetaIngrediente recetaIngrediente) {
        return recetaIngredienteRepository.save(recetaIngrediente);
    }

    public Optional<RecetaIngrediente> buscarPorId(RecetaIngredienteId id) {
        return recetaIngredienteRepository.findById(id);
    }

    public List<RecetaIngrediente> obtenerTodasRecetaIngrediente() {
        return recetaIngredienteRepository.findAll();
    }
    
    // Trae todos los ingredientes de una receta específica
    public List<RecetaIngrediente> obtenerIngredientesDeReceta(Long idReceta) {
        return recetaIngredienteRepository.findAllByRecetaIdReceta(idReceta);
    }

    // Busca un ingrediente específico dentro de una receta
    public Optional<RecetaIngrediente> obtenerIngredienteDeReceta(Long idReceta, Long idIngrediente) {
        return recetaIngredienteRepository.findByRecetaIdRecetaAndIngredienteIdIngrediente(idReceta, idIngrediente);
    }

    public List<RecetaIngrediente> obtenerPorIdIngrediente(Long idIngrediente) {
        return recetaIngredienteRepository.findByIngredienteIdIngrediente(idIngrediente);
    }

    public void eliminar(RecetaIngredienteId id) {
        recetaIngredienteRepository.deleteById(id);
    }
    
    // Elimina todos los ingredientes asociados a una receta
    public void eliminarTodosLosIngredientesDeReceta(Receta receta) {
        recetaIngredienteRepository.deleteAllByReceta(receta);
    }

    // Elimina un ingrediente específico de una receta
    public void eliminarIngredienteDeReceta(Long idReceta, Long idIngrediente) {
        recetaIngredienteRepository.deleteByRecetaIdRecetaAndIngredienteIdIngrediente(idReceta, idIngrediente);
    }
}
