package br.com.zupacademy.eduardo.mercadolivre.repository;

import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);
}
