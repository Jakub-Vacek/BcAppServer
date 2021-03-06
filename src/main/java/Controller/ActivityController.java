/**
 * @author jakubvacek
 */
package Controller;

import Core.StatusException;
import Model.Activity;
import Model.User;
import Model.TransferObject.ActivityTo;
import Service.ActivityServiceImpl;
import Service.ProjectServiceImpl;
import Service.TodoServiceImpl;
import Service.UserServiceImpl;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ActivityController {

    private static final Logger LOG = Logger.getLogger(ActivityController.class.getName());

    @Resource
    private ProjectServiceImpl projectService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private TodoServiceImpl todoService;
    @Resource
    private ActivityServiceImpl activityService;

    /**
     * Creates new activity, sets its id and createdOn
     *
     * @param activity new todo from client
     * @param projectId id of project
     * @param todoId id of todo
     * @param logedUserId id of curently loged user
     * @param selectedUserId id of selected user - loged user can be working on someone's else task
     * @return ResponeseEntity with OK status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/activities", method = RequestMethod.POST)
    public ResponseEntity<Void> createActivity(@RequestBody Activity activity, @RequestParam(value = "projectId", required = false) Integer projectId, @RequestParam(value = "todoId", required = false) Integer todoId, @RequestParam(value = "logedUserId") int logedUserId, @RequestParam(value = "selectedUserId") int selectedUserId) {
        LOG.log(Level.INFO, "Creating activity with todoID: {0}, projectId: {1}, logedUserID: {2} selectedUser {3} activity message: {4}", new Object[]{todoId, projectId, logedUserId, selectedUserId, activity.getDescription()});
        activity.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        try {
            if (todoId != null) {
                activity.setTodo(todoService.getTodoById(todoId));
            }
            if (projectId != null) {
                activity.setProject(projectService.getProjectById(projectId));
            }
            activity.setSelectedUser(userService.getUserById(selectedUserId));
            if (activity.getSelectedUser() == null) {
                LOG.log(Level.WARNING, "Selected user is NULL");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            activity.setLogedUser(userService.getUserById(logedUserId));
            if (activity.getLogedUser() == null) {
                LOG.log(Level.WARNING, "Loged user is NULL");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            activityService.createActivity(activity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Gets activities of specified user
     *
     * @param userId id of user
     * @return ArrayList of viewActivity in JSON
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/activities", method = RequestMethod.GET, params="userId")
    public ResponseEntity<ArrayList<ActivityTo>> getActivitiesOfUser(@RequestParam(value = "userId") int userId) {
        LOG.log(Level.INFO, "Geting activities transfer object of user with ID: {0}", userId);
        ArrayList<Activity> activitis;
        ArrayList<ActivityTo> viewActivitis = new ArrayList<>();
        try {
            User logedUser = userService.getUserById(userId);
            User selectedUser;
            logedUser.setPasswordHash("");
            activitis = activityService.getActivitiesOfUser(userId);
            for (Activity a : activitis) {
                a.setLogedUser(logedUser);
                selectedUser = userService.getUserById(a.getSelectedUser().getID());
                selectedUser.setPasswordHash("");
                a.setSelectedUser(selectedUser);
                if (a.getProject() != null) {
                    a.setProject(projectService.getProjectById(a.getProject().getID()));
                }
                if (a.getTodo() != null) {
                    a.setTodo(todoService.getTodoById(a.getTodo().getID()));
                }
                viewActivitis.add(new ActivityTo(a,false));
            }
            return new ResponseEntity<>(viewActivitis, HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Gets detail of specified activity
     *
     * @param Id id of activity
     * @return Activity witch all atributes fetched in JSON
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/activities", method = RequestMethod.GET, params="activityId")
    public ResponseEntity<ActivityTo> getActivityDetail(@RequestParam(value = "activityId") int Id) {
        LOG.log(Level.INFO, "Geting activity detail of activity with ID:: {0}", Id);
        try {
            Activity activity = activityService.getActivityByid(Id);
            //Seting loged and selected user
            User logedUser = userService.getUserById(activity.getLogedUser().getID());
            User selectedUser = userService.getUserById(activity.getSelectedUser().getID());
            activity.setLogedUser(logedUser);
            activity.setSelectedUser(selectedUser);
            //seting project
            if (activity.getProject() != null) {
                activity.setProject(projectService.getProjectById(activity.getProject().getID()));
            }
            //seting todo
            if (activity.getTodo() != null) {
                activity.setTodo(todoService.getTodoById(activity.getTodo().getID()));
            }
            return new ResponseEntity<>(new ActivityTo(activity,true), HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

}
