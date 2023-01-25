package defensoria.pa.def.br.oauth_server.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import defensoria.pa.def.br.oauth_server.dto.auth.AuthenticationRequest;
import defensoria.pa.def.br.oauth_server.dto.auth.AuthenticationResponse;
import defensoria.pa.def.br.oauth_server.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    @PostMapping(value="/login")
    public ResponseEntity<AuthenticationResponse> authenticationUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }   
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public String handleValidationExceptions() {
        return "Usuario ou senha incorretos.";
    }
}
