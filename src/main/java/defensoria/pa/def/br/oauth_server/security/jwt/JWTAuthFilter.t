package defensoria.pa.def.br.oauth_server.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.jwt.demojwt.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private static final int TOKEN_EXPIRACAO = 600_000;

    public static final String TOKEN_SENHA = "413F4428472B4B6150645367566B5970337336763979244226452948404D6351";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(),
                    usuario.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao autenticar usuario", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();

        String token = JWT.create().withSubject(userDetails.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
        .sign(Algorithm.HMAC512(TOKEN_SENHA));
        response.getWriter().write(token);
        response.getWriter().flush();
    }

}
