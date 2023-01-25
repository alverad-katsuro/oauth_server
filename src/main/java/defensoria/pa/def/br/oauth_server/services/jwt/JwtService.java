package defensoria.pa.def.br.oauth_server.services.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String extractLogin(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extractedClaims, UserDetails userDetails) {
        Instant now = Instant.now();
        var jwtClaimsSet = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS));
        extractedClaims.forEach((k, v) -> jwtClaimsSet.claim(k, v));
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet.build())).getTokenValue();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractLogin(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return jwtDecoder.decode(token).getExpiresAt().isBefore(Instant.now());
    }
}
