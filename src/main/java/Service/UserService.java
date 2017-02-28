package Service;

import Core.StatusException;
import Model.User;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author jakubvacek
 */
public interface UserService extends UserDetailsService {

    /**
     * Gets user with specified id
     * @param userId
     * @return User
     * @throws StatusException
     */
    User getUserById(int userId) throws StatusException;

    /**
     * Gets user with specified username
     * @param username
     * @return User
     * @throws StatusException
     */
    User getUserByUsername(String username) throws StatusException;

    /**
     * Gets all users in database
     * @return ArrayList odo Users
     * @throws StatusException
     */
    ArrayList<User> getUsers() throws StatusException;

    /**
     * Deletes user with specified id
     * @param userId
     * @throws StatusException
     */
    void deleteUserById(int userId) throws StatusException;

    /**
     * Used for autentication
     * @param username o user
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Creates new user object in database
     * @param user to be crated
     * @throws StatusException
     */
    void createUser(User user) throws StatusException;

}
