/**
 * @author jakubvacek
 */

package Controller;

import Core.StatusException;
import Model.TransferObject.UserTo;
import Service.UserServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
    @Resource
    private UserServiceImpl userService;

    /**
     * Checks user credentials and returns loged user
     *
     * @param user user credentials
     * @return loged user and OK status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<UserTo> login(@AuthenticationPrincipal final UserDetails user) {
        try {
            LOG.log(Level.INFO, "Trying to login");
            return new ResponseEntity<>(new UserTo(userService.getUserByUsername(user.getUsername())), HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null,ex);
            return new ResponseEntity<>(ex.status);
        }
    }
}
