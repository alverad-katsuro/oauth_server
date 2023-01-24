package defensoria.pa.def.br.oauth_server.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity(name = "Grupo")
public class Grupo implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_grupo", nullable = false, unique = true)
    private Integer idGrupo;

    @Column(name = "nome_grupo", nullable = false, unique = true, length = 50)
    private String nomeGrupo;

    @ManyToMany(mappedBy = "grupos", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private Set<Usuario> usuarios = new HashSet<>();

    @Override
    public String getAuthority() {
        return this.nomeGrupo;
    }
}
