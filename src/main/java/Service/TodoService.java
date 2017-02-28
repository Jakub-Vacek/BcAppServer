package Service;

import Core.StatusException;
import Model.Todo;
import java.util.ArrayList;

/**
 *
 * @author jakubvacek
 */
public interface TodoService {

    /**
     * Gets todo with specified id
     * @param todoId
     * @return Todo
     * @throws StatusException
     */
    Todo getTodoById(int todoId) throws StatusException;

    /**
     * Gets todos of specified project
     * @param projectId 
     * @return ArrayList of todos
     * @throws StatusException
     */
    ArrayList<Todo> getTodosOfProject(int projectId) throws StatusException;

    /**
     * Creates new todo object in database
     * @param todo 
     * @throws StatusException
     */
    void createTodo(Todo todo) throws StatusException;

    /**
     * Updates todo
     * @param todo
     * @throws StatusException
     */
    void updateTodo(Todo todo) throws StatusException;

    /**
     * Deletes todo with specified id
     * @param todoId
     * @throws StatusException
     */
    void deleteTodo(int todoId) throws StatusException; 
    
    /**
     * Deletes todos of specified project
     * @param projectId
     * @throws StatusException
     */
    void deleteTodosOfProject(int projectId) throws StatusException;
}
