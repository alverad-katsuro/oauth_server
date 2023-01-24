package defensoria.pa.def.br.oauth_server.dto.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
