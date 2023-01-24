package defensoria.pa.def.br.oauth_server.repository.usuario;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import defensoria.pa.def.br.oauth_server.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}
