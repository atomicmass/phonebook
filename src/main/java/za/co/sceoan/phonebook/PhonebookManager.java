package za.co.sceoan.phonebook;

import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import za.co.sceoan.phonebook.dto.Contact;

@ApplicationScoped
public class PhonebookManager {
    @Inject
    private Validator validator;
    
    @Transactional
    public Contact persistContact(String ownerEmail, Contact contact) throws ValidationException {
        contact.setOwnerEmail(ownerEmail);
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
        
        contact.setInitial(contact.getName().substring(0, 1));

        contact.persist();
        return contact;
    }
    
    public List<Contact> listByInitail(String ownerEmail, String initial) {
        return Contact.listByInitial(ownerEmail, initial);
    }
    
    public List<String> listInitials(String ownerEmail) {
        return Contact.listInitials(ownerEmail);
    }

}
