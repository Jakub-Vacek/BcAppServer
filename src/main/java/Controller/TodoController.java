/**
 * @author jakubvacek
 */
package Controller;

import Core.StatusException;
import Model.Project;
import Model.Todo;
import Model.ViewModel.ViewTodo;
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
public class TodoController {

    private static final Logger LOG = Logger.getLogger(TodoController.class.getName());

    @Resource
    private TodoServiceImpl todoService;
    @Resource
    private ProjectServiceImpl projectService;
    @Resource
    private UserServiceImpl userService;

    /**
     *
     * Lists all todos
     *
     * @return ArrayList of todo in JSON
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/todoDetail", method = RequestMethod.GET)
    public ResponseEntity<Todo> getTodoDetail(@RequestParam(value = "id") int id) {
        LOG.log(Level.INFO, "Geting detail of todo with id: " + id);
        Todo todo;
        try {
            todo = todoService.getTodoById(id);
            //User user = userService.getUserById(todo.getProject().getUser().getID());
            Project project = projectService.getProjectById(todo.getProject().getID());
            project.setUser(userService.getUserById(project.getUser().getID()));
            todo.setProject(project);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Gets todos of specified list
     *
     * @param projectId id of project
     * @return ArrayList of todo in JSON
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/todosOfProject", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<ViewTodo>> getTodosOProject(@RequestParam(value = "id") int projectId) {
        LOG.log(Level.INFO, "Geting todos of project with id: " + projectId);
        ArrayList<ViewTodo> todos;
        try {
            //Get items and map items to view models
            todos = todoService.getTodosOfProject(projectId)
                    .stream()
                    .map(item -> new ViewTodo(item))
                    .collect(Collectors.toCollection(ArrayList::new));
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Detetes todo
     *
     * @param todoId - id of todo
     * @param projectId - id of project
     * @return ResponeseEntity with OK/NOT_FOUND status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/todo", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTodo(@RequestParam(value = "todoId") int todoId, @RequestParam(value = "listId") int projectId) {
        LOG.log(Level.INFO, "Deleting todo with id: " + todoId);
        try {
            todoService.deleteTodo(todoId);
            this.updateProjectStatus(projectId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * Updates todo
     *
     * @param todo updated todo
     * @param projectId id of project
     * @return ResponeseEntity with OK/NOT_FOUND status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    public ResponseEntity<Void> updateTodo(@RequestBody Todo todo, @RequestParam(value = "id", defaultValue = "0") int projectId) {
        LOG.log(Level.INFO, "Updating todo");
        try {
            Todo todoFromDatabase = todoService.getTodoById(todo.getID());
            if (todo.getCreatedOn() == null) {
                todo.setCreatedOn(todoFromDatabase.getCreatedOn());
            }
            if (todo.getResolveUntil() == null) {
                todo.setResolveUntil(todoFromDatabase.getResolveUntil());
            }
            todo.setProject(projectService.getProjectById(projectId));
            todoService.updateTodo(todo);
            this.updateProjectStatus(projectId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    /**
     * createTodo todo Creates new todo, sets its id and createdOn
     *
     * @param todo new todo from client
     * @param projectID id of project
     * @return ResponeseEntity with OK status
     */
    @CrossOrigin(origins = "http://localhost:8383")
    @RequestMapping(value = "/todo", method = RequestMethod.PUT)
    public ResponseEntity<Void> createTodo(@RequestBody Todo todo, @RequestParam(value = "id", defaultValue = "0") int projectID) {
        LOG.log(Level.INFO, "Creating todo");
        try {
            todo.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            todo.setProject(projectService.getProjectById(projectID));
            todoService.createTodo(todo);
            this.updateProjectStatus(projectID);
            System.out.println(todo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StatusException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.status);
        }
    }

    private void updateProjectStatus(int projectId) throws StatusException {
        Project project = projectService.getProjectById(projectId);
        ArrayList<Todo> todos = todoService.getTodosOfProject(projectId);
        if (todos.isEmpty()) {
            project.setStatus(100.0);
        } else {
            double num = 0;
            for (Todo t : todos) {
                if (t.isResolved()) {
                    num++;
                }
            }
            project.setStatus(Math.round((num / (double) todos.size()) * 100));
        }
        projectService.updateProject(project);
    }

}
