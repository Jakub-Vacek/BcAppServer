/**
 * @author jakubvacek
 */
package Controller;

import Core.StatusException;
import Model.Project;
import Model.TransferObject.ProjectTo;
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
public class ProjectController {

    private static final Logger LOG = Logger.getLogger(ProjectController.class.getName());
    @Resource
    private ProjectServiceImpl projectService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private TodoServiceImpl todoService;

    /**
     * Creates new project, sets its id
     *
     * @param project new project from client
     * @param userId id of user who creates project
     * @return ResponeseEntity with OK status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity<Void> createProject(@RequestBody Project project, @RequestParam(value = "userId") int userId) {
        try {
            LOG.log(Level.INFO, "Creating project");
            project.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            project.setUser(userService.getUserById(userId));
            projectService.createProject(project);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * get Project detail
     *
     * @param projectId of project from client
     * @param fetchDetailInfo if true method fetches all the details 
     * @return ResponeseEntity with OK status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/projects", method = RequestMethod.GET, params = "projectId")
    public ResponseEntity<ProjectTo> getProjectDetail(@RequestParam(value = "projectId") int projectId, @RequestParam(value = "fetchDetailInfo") boolean fetchDetailInfo) {
        try {
            if (fetchDetailInfo) {
                LOG.log(Level.INFO, "Geting project detail with id: {0}", projectId);
            } else {
                LOG.log(Level.INFO, "Geting project with id: {0}", projectId);
            }
            Project project = projectService.getProjectById(projectId);
            project.setUser(userService.getUserById(project.getUser().getID()));
            return new ResponseEntity<>(new ProjectTo(project, fetchDetailInfo), HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Gets lists of specified user
     *
     * @param userId id of user
     * @return ArrayList of list in JSON
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/projects", method = RequestMethod.GET, params = "userId")
    public ResponseEntity<ArrayList<ProjectTo>> getProjectsOfUser(@RequestParam(value = "userId") int userId) {
        ArrayList<ProjectTo> projects;
        try {
            LOG.log(Level.INFO, "Geting projects of user with id: {0}", userId);
            //Get items and sort them acording to createdOn then map items to view models
            projects = projectService.getProjectsOfUser(userId)
                    .stream().sorted((first, second)
                            -> first.getCreatedOn().compareTo(second.getCreatedOn()))
                    .map(item -> new ProjectTo(item, false)) //dont fetch detail information
                    .collect(Collectors.toCollection(ArrayList::new));
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Detetes list with specified id
     *
     * @param projectId of project
     * @return ResponeseEntity with OK/NOT_FOUND status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/projects", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteProject(@RequestParam(value = "projectId") int projectId) {
        try {
            LOG.log(Level.INFO, "Deleting project with id: {0}", projectId);
            todoService.deleteTodosOfProject(projectId);
            projectService.deleteProjectById(projectId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }
}
