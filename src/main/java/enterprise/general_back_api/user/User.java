
package enterprise.general_back_api.user;

import java.util.List;

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

/**
 * Represents a User entity with various attributes and constraints.
 * 
 * <p>
 * This entity is mapped to the "Users" table in the database and includes
 * fields for username, email, password, dni, role, and tokens. The class uses
 * various validation annotations to enforce constraints on the fields.
 * </p>
 * 
 * <ul>
 * <li><b>ID:</b> Unique identifier for the user, generated using UUID
 * strategy.</li>
 * <li><b>username:</b> The username of the user, cannot be blank.</li>
 * <li><b>email:</b> The email of the user, must be a valid email format and
 * unique.</li>
 * <li><b>password:</b> The password of the user, cannot be blank.</li>
 * <li><b>dni:</b> The DNI of the user, must be exactly 8 digits and
 * unique.</li>
 * <li><b>role:</b> The role of the user, cannot be null and is an enumerated
 * type.</li>
 * <li><b>tokens:</b> A list of tokens associated with the user.</li>
 * </ul>
 * 
 * <p>
 * Annotations used:
 * </p>
 * <ul>
 * <li>{@link jakarta.persistence.Entity}</li>
 * <li>{@link jakarta.persistence.Table}</li>
 * <li>{@link jakarta.persistence.Id}</li>
 * <li>{@link jakarta.persistence.GeneratedValue}</li>
 * <li>{@link jakarta.persistence.Column}</li>
 * <li>{@link jakarta.validation.constraints.Email}</li>
 * <li>{@link jakarta.validation.constraints.NotBlank}</li>
 * <li>{@link jakarta.validation.constraints.Size}</li>
 * <li>{@link jakarta.validation.constraints.Pattern}</li>
 * <li>{@link jakarta.persistence.Enumerated}</li>
 * <li>{@link jakarta.persistence.EnumType}</li>
 * <li>{@link jakarta.validation.constraints.NotNull}</li>
 * <li>{@link jakarta.persistence.OneToMany}</li>
 * </ul>
 * 
 * <p>
 * Lombok annotations are used to generate getters and setters:
 * </p>
 * <ul>
 * <li>{@link lombok.Getter}</li>
 * <li>{@link lombok.Setter}</li>
 * </ul>
 * 
 * @see enterprise.general_back_api.user.Role
 * @see enterprise.general_back_api.token.Token
 */

@Entity
@Table(name = "Users")
@Getter
@Setter
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false)
    @NotBlank
    private String username;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

}
