/**
 * @author jakubvacek
 */
package Mock;

import Core.StatusException;
import Model.User;
import Service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MockUserServiceImpl implements UserService {

    HashMap<Integer, User> mockDao = new HashMap<>();
    Integer mockId = 0;

    @Override
    public User getUserById(int userId) throws StatusException {
        return mockDao.get(userId);
    }

    @Override
    public User getUserByUsername(String username) throws StatusException {
        User result = null;
        for (User user : (User[]) mockDao.values().toArray()) {
            if (user.getUsername().equals(username)) {
                result = user;
            }
        }
        return result;
    }

    @Override
    public ArrayList<User> getUsers() throws StatusException {
        return new ArrayList<>(mockDao.values());
    }

    @Override
    public void deleteUserById(int userId) throws StatusException {
        if (mockDao.get(userId) != null) {
            mockDao.remove(userId);
        }    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDetails userDetails = null;
            User dbUser = this.getUserByUsername(username);
            if (dbUser == null) {
                throw new UsernameNotFoundException("HiMVC Security:: Error in retrieving user(username=" + dbUser.getUsername() + ")");
            }
            userDetails = new org.springframework.security.core.userdetails.User(
                    dbUser.getUsername(),
                    dbUser.getPasswordHash(),//here you can put a clear text password
                    true,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority(dbUser.getRole()))
            );
            return userDetails;
        } catch (StatusException ex) {
            throw new UsernameNotFoundException("HiMVC Security:: Error in retrieving user(username=" + username + ")");
        }
    }

    @Override
    public void createUser(User user) throws StatusException {
        user.setID(mockId);
        mockDao.put(mockId, user);
        mockId++;
    }

}
