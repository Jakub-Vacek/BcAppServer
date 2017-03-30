/**
 * @author jakubvacek
 */
package Controller;

import Core.StatusException;
import Model.Project;
import Model.User;
import Model.TransferObject.UserTo;
import Service.ProjectServiceImpl;
import Service.TodoServiceImpl;
import Service.UserServiceImpl;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    @Resource
    private UserServiceImpl userService;
    @Resource
    private ProjectServiceImpl projectService;
    @Resource
    private TodoServiceImpl todoService;

    /**
     * List all users
     *
     * @return ArrayList of user in JSON
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<UserTo>> getUsers() {
        LOG.log(Level.INFO, "Geting users");
        ArrayList<UserTo> users;
        try {
            //Get items and sort them acording to createdOn then map items to view models
            users = userService.getUsers()
                    .stream().sorted((first, second)
                            -> first.getCreatedOn().compareTo(second.getCreatedOn()))
                    .map(item -> new UserTo(item))
                    .collect(Collectors.toCollection(ArrayList::new));
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Creates user and sets createdOn
     *
     * @param user
     * @return id of created user
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        LOG.log(Level.INFO, "Creating user");
        try {
            user.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Detetes user with specified id
     *
     * @param userId of user
     * @return ResponeseEntity with OK/NOT_FOUND status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "userId", defaultValue = "0") int userId) {
        LOG.log(Level.INFO, "Deleting user with id: " + userId);
        try {
            ArrayList<Project> projects = projectService.getProjectsOfUser(userId);
            for (Project p : projects) {
                todoService.deleteTodosOfProject(p.getID());
                projectService.deleteProjectById(p.getID());
            }
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * get User detail
     *
     * @param userId of project from client
     * @return ResponeseEntity with OK status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/users", method = RequestMethod.GET, params="userId")
    public ResponseEntity<UserTo> getUserDetail(@RequestParam(value = "userId") int userId) {
        try {
            LOG.log(Level.INFO, "Geting detil of user with id: " + userId);
            return new ResponseEntity<>(new UserTo(userService.getUserById(userId)), HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

}
