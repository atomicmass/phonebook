package za.co.sceoan.phonebook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import za.co.sceoan.phonebook.dto.User;

@ApplicationScoped
public class UserManager {

    @Inject
    private Validator validator;
    private final Map<String, User> userCache;
    
    public UserManager() {
        userCache = new HashMap<>();
    }

    /**
     * Create a new user The data is validated first for completeness and
     * uniqueness
     *
     * @param user
     * @return The newly created user
     * @throws ValidationException Data not supplied correctly
     */
    @Transactional
    public User persistUser(User user) throws ValidationException {
        //First user is always the admin
        if (User.count() == 0) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        if (User.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email already exists");
        }

        user.persist();
        return user;
    }

    /**
     * Retrieve the user based on the username The password is not returned
     *
     * @param email
     * @return
     */
    public User retrieveUser(String email) {
        User u = cacheUser(email);
        if (u != null) {
            u.setPassword(null);
        }
        return u;
    }

    /**
     * List all users on the system - without passwords
     *
     * @return
     */
    public List<User> retrieveAllUsers() {
        List<User> u = User.listAll();
        u.stream().forEach(n -> n.setPassword(null));
        return u;
    }
    
    /**
     * Checks the cache for the specified user and adds it if not there
     * @param email
     * @return The cached user if it exists, else null
     */
    private User cacheUser(String email) {
        if(userCache.containsKey(email)) {
            return userCache.get(email);
        }
        User u = User.findByEmail(email);
        if(u != null) {
            userCache.put(email, u);
        }
        return u;
    }

    /**
     * Authenticates the provided user
     *
     * @param user
     * @return true if authenticated
     */
    public boolean authenticateUser(User user) {
        User u = cacheUser(user.getEmail());
        return u != null && u.getPassword().equals(user.getPassword());
    }

}
