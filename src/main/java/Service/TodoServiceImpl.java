/**
 * @author jakubvacek
 */
package Service;

import Core.StatusException;
import Model.Todo;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private static final Logger LOG = Logger.getLogger(TodoServiceImpl.class.getName());
    private final String DATABASE_URL = "jdbc:derby:\\MyDB\\demo;";
    private Dao<Todo, Integer> dao;

    public TodoServiceImpl() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
            dao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Todo.class);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Todo getTodoById(int todoId) throws StatusException {
        try {
            Todo t = dao.queryForId(todoId);
            return t;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ArrayList<Todo> getTodosOfProject(int projectId) throws StatusException {
        try {
            return (ArrayList<Todo>) dao.queryBuilder().where().eq("projectID", projectId).query();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void createTodo(Todo todo) throws StatusException {
        try {
            dao.create(todo);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateTodo(Todo todo) throws StatusException {
        try {
            if (dao.idExists(todo.getID())) {
                dao.update(todo);
            } else {
                throw new StatusException(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteTodo(int todoId) throws StatusException {
        try {
            if (dao.idExists(todoId)) {
                dao.deleteById(todoId);
            } else {
                throw new StatusException(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void deleteTodosOfProject(int projectId) throws StatusException {
        try {
            dao.delete((ArrayList<Todo>) dao.queryBuilder().where().eq("projectID", projectId).query());
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
