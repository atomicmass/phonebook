package za.co.sceoan.phonebook.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Contact extends PanacheEntity implements Serializable {

    @NotBlank(message = "Name may not be blank")
    private String name;
    private String email;
    private String phone;
    private String initial;
    private String ownerEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInitial() {
        return initial;
    }
    
    /**
     * Get a stream of the current user's (owner) contacts
     * @param ownerEmail
     * @return 
     */
    public static Stream<Contact> streamByOwner(String ownerEmail) {
        Stream<Contact> contacts = streamAll();
        return contacts.filter(t -> t.getOwnerEmail().equalsIgnoreCase(ownerEmail));
    }

    /**
     * List all contacts matching an initial
     * @param ownerEmail
     * @param initial
     * @return 
     */
    public static List<Contact> listByInitial(String ownerEmail, String initial) {
        return streamByOwner(ownerEmail)
                .filter(t -> t.getInitial().equalsIgnoreCase(initial))
                .collect(Collectors.toList());
    }

    /**
     * List all contacts matching a name
     * @param ownerEmail
     * @param name
     * @return 
     */
    public static List<Contact> listByName(String ownerEmail, String name) {
        return streamByOwner(ownerEmail)
                .filter(t -> t.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }
    
    /**
     * List distinct initails
     * @param ownerEmail
     * @return 
     */
    public static List<String> listInitials(String ownerEmail) {
        Stream<Contact> contacts = Contact
                .find("select distinct initial from Contact where ownerEmail = ?1", ownerEmail).stream();
        return contacts.map(Contact::getInitial).collect(Collectors.toList());
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
