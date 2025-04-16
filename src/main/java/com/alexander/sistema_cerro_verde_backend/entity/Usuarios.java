package com.alexander.sistema_cerro_verde_backend.entity;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuarios implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuarios;
    private String username; 
    private String password;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private boolean enable = true; 
    private String  perfil;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="usuario")
    @JsonIgnore //por que a la hora de listar estamos obteniendo lo json y como son de tipo peresosa se devulve los datos repetidos
    private Set<UsuarioRoles> usuarioRoles= new HashSet<>();

    @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades= new HashSet<>();
        this.usuarioRoles.forEach(usuarioRol->{
            autoridades.add(new Authority(usuarioRol.getRol().getNombreRol()));
        });
        return autoridades;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() { return this.enable; }  

    
}