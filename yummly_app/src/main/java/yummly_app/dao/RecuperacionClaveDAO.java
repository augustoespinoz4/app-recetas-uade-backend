package yummly_app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import yummly_app.modelo.RecuperacionClave;
import yummly_app.repositorio.RecuperacionClaveRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecuperacionClaveDAO {

    @Autowired
    private RecuperacionClaveRepository recuperacionClaveRepository;

    public RecuperacionClave guardar(RecuperacionClave recuperacionClave) {
        return recuperacionClaveRepository.save(recuperacionClave);
    }

    public Optional<RecuperacionClave> buscarPorId(Long id) {
        return recuperacionClaveRepository.findById(id);
    }

    public List<RecuperacionClave> buscarTodos() {
        return recuperacionClaveRepository.findAll();
    }

    public void eliminar(Long id) {
        recuperacionClaveRepository.deleteById(id);
    }

    public List<RecuperacionClave> buscarPorIdUsuario(Long idUsuario) {
        return recuperacionClaveRepository.findByUsuarioIdUsuario(idUsuario);
    }
}
