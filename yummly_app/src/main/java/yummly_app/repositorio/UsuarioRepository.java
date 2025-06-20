package yummly_app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yummly_app.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByAlias(String alias);
    Optional<Usuario> findByEmail(String email);
    boolean existsByAlias(String alias);
    boolean existsByEmail(String email);
	void deleteByEmail(String email);
}

