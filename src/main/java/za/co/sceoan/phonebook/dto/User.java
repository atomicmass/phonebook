package za.co.sceoan.phonebook.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Combined DTO and DAO for User data
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User extends PanacheEntity implements Serializable {
    @NotBlank(message = "Password may not be blank")
    private String password;
    @NotBlank(message = "Email may not be blank")
    private String email;
    @NotBlank(message = "Name may not be blank")
    private String name;
    private String role;

    public User() {
    }

    public User(String password, String email, String name) {
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
