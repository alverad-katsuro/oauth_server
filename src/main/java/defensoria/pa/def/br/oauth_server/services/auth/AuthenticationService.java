package defensoria.pa.def.br.oauth_server.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import defensoria.pa.def.br.oauth_server.dto.auth.AuthenticationRequest;
import defensoria.pa.def.br.oauth_server.dto.auth.AuthenticationResponse;
import defensoria.pa.def.br.oauth_server.services.jwt.TokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        return AuthenticationResponse.builder().token(tokenService.generateToken(auth)).build();
    }
    
}
