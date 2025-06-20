package yummly_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import yummly_app.modelo.Ingrediente;
import yummly_app.repositorio.IngredienteRepository;

@Repository
public class IngredienteDAO {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    // Crear un nuevo ingrediente
    public Ingrediente crearIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    // Obtener todos los ingredientes
    public List<Ingrediente> obtenerTodosLosIngredientes() {
        return ingredienteRepository.findAll();
    }

    // Obtener un ingrediente por ID
    public Optional<Ingrediente> obtenerIngredientePorId(Long idIngrediente) {
        return ingredienteRepository.findById(idIngrediente);
    }

    // Obtener un ingrediente por nombre
    public Optional<Ingrediente> obtenerIngredientePorNombre(String nombre) {
        return ingredienteRepository.findByNombre(nombre);
    }

    // Verificar existencia por nombre
    public boolean existePorNombre(String nombre) {
        return ingredienteRepository.existsByNombre(nombre);
    }

    // Verificar existencia por ID
    public boolean existePorId(Long idIngrediente) {
        return ingredienteRepository.existsById(idIngrediente);
    }

    // Actualizar un ingrediente
    @Transactional
    public Ingrediente actualizarIngrediente(Long idIngrediente, Ingrediente ingredienteActualizado) {
        return ingredienteRepository.findById(idIngrediente)
                .map(ingrediente -> {
                    ingrediente.setNombre(ingredienteActualizado.getNombre());
                    // No se actualiza la lista de recetas desde aquí por simplicidad
                    return ingredienteRepository.save(ingrediente);
                })
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con id " + idIngrediente));
    }

    // Eliminar un ingrediente por ID
    @Transactional
    public void eliminarIngredientePorId(Long idIngrediente) {
        ingredienteRepository.deleteById(idIngrediente);
    }
}
