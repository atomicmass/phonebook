package za.co.sceoan.phonebook.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import za.co.sceoan.phonebook.dto.User;

@ApplicationScoped
public class UserDao {
    @Inject
    private Validator validator;
    
    /**
     * Create a new user
     * The data is validated first for completeness and uniqueness
     * @param user
     * @return The newly created user
     * @throws ValidationException Data not supplied correctly 
     */
    @Transactional
    public User persistUser(User user) throws ValidationException {
        //First user is always the admin
        if(User.count() == 0) {
            user.setRole(User.Role.ADMIN);
        } else {
            user.setRole(User.Role.USER);
        }
        
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
        
        if(User.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("[email] already exists");
        }
        
        user.persist();
        return user;
    }
    
    /**
     * Retrieve the user based on the username
     * The password is not returned
     * @param username
     * @return 
     */
    public User retrieveUser(String email) {
        User u = User.findByEmail(email);
        if (u != null) {
            u.setPassword(null);
        }
        return u;
    }

    /**
     * List all users on the system - without passwords :)
     * @return 
     */
    public List<User> retrieveAllUsers() {
        List<User> u = User.listAll();
        u.stream().forEach(n -> n.setPassword(null));
        return u;
    }
    
}
