package defensoria.pa.def.br.oauth_server.controller.usuario;

import org.springframework.web.bind.annotation.RestController;

import defensoria.pa.def.br.oauth_server.model.Usuario;
import defensoria.pa.def.br.oauth_server.services.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @PostMapping(value="/cadastrar")
    public ResponseEntity<Usuario> postMethodName(@RequestBody @Valid Usuario usuario) {
        usuario = usuarioService.save(usuario);        
        return ResponseEntity.ok(usuario);
    }
    
}
