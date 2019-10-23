package za.co.sceoan.phonebook;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import za.co.sceoan.phonebook.dto.Contact;

@ApplicationScoped
public class PhonebookManager {

    @Inject
    Validator validator;

    /**
     * Persist contact to DB. Performs validations on contact details first.
     *
     * @param ownerEmail Email address of the owner of the record. The user who
     * created this record
     * @param contact
     * @return
     * @throws ValidationException
     */
    @Transactional
    public Contact persistContact(String ownerEmail, Contact contact) throws ValidationException {
        if (contact == null) {
            throw new ValidationException("Contact not populated");
        }

        contact.setOwnerEmail(ownerEmail);
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        contact.setInitial(contact.getName().substring(0, 1));

        contact.persist();
        return contact;
    }

    /**
     * Lilst all contacts belonging to owner with the matching initial
     * @param ownerEmail Email of user who owns the contact
     * @param initial Initial letter of contact name
     * @return 
     */
    public List<Contact> listByInitial(String ownerEmail, String initial) {
        return Contact.listByInitial(ownerEmail, initial);
    }

    /**
     * List all the initials of the contacts belongnig to the owner
     * @param ownerEmail Email of user who owns the contact
     * @return 
     */
    public List<String> listInitials(String ownerEmail) {
        return Contact.listInitials(ownerEmail);
    }

    /**
     * List all contacts belonginf to specified owner
     * @param ownerEmail Email of user who owns the contact
     * @return 
     */
    public List<Contact> listAll(String ownerEmail) {
        return Contact.listAll(ownerEmail);
    }

    /**
     * Find the specific contact based on the ID
     * @param ownerEmail Email of user who owns the contact
     * @param id ID of record to find
     * @return 
     */
    public Contact find(String ownerEmail, Long id) {
        Contact c = Contact.findById(id);
        if (c.getEmail().equalsIgnoreCase(ownerEmail)) {
            return c;
        }
        return null;
    }

    /**
     * Searches through the user's contacts for matches based on the regex
     *
     * @param ownerEmail Email of user who owns the contact
     * @param regex Regular expression to use in search. Will search all fields
     * @return
     */
    public List<Contact> search(String ownerEmail, String regex) {
        return Contact.streamByOwner(ownerEmail)
                .filter(t -> t.matches(regex))
                .collect(Collectors.toList());
    }

    /**
     * Delete the specified contact
     * @param ownerEmail Email of user who owns the contact
     * @param id ID of the contact to delete
     */
    public void delete(String ownerEmail, Long id) {
        Contact c = find(ownerEmail, id);
        if (c != null) {
            c.delete();
        }
    }

}
