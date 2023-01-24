package defensoria.pa.def.br.oauth_server.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import defensoria.pa.def.br.oauth_server.dto.auth.AuthenticationRequest;
import defensoria.pa.def.br.oauth_server.dto.auth.AuthenticationResponse;
import defensoria.pa.def.br.oauth_server.model.Usuario;
import defensoria.pa.def.br.oauth_server.repository.usuario.UsuarioRepository;
import defensoria.pa.def.br.oauth_server.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        Usuario usuario = usuarioRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        Map<String, Object> informacoesAdicionais = new HashMap<>();
        informacoesAdicionais.put("Nome", usuario.getUsername());
        return AuthenticationResponse.builder().token(jwtService.generateToken(informacoesAdicionais, usuario)).build();
    }
    
}
