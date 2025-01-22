
package enterprise.general_back_api.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import enterprise.general_back_api.token.Token;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
@Builder

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false)
    @NotBlank
    private String user_name;

    @Email(message = "email no valido")
    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;

    // @Size(min = 4, message = "contraseña minimo 4 digitos") //enripta la
    // contraseña
    @Column(nullable = false)
    @NotBlank
    private String password;

    @Size(max = 8, min = 8, message = "8 digitos dni")
    @Column(nullable = false, unique = true, length = 8)
    @Pattern(regexp = "\\d{8}", message = "solo debe tener numeros")
    private String dni;

    @Override
    public String getUsername() {
        return email;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false/* , columnDefinition = "varchar(255) default 'USER'" */)
    @NotNull
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthrities();
    }

}
