package za.co.sceoan.phonebook.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User extends PanacheEntity implements Serializable {
    
    public enum Role {
        USER, ADMIN
    }

    @NotBlank(message = "[password] may not be blank")
    private String password;
    @NotBlank(message = "[email] may not be blank")
    private String email;
    @NotBlank(message = "[name] may not be blank")
    private String name;
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
