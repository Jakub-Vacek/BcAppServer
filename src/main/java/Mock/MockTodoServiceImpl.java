/**
 * @author jakubvacek
 */
package Mock;

import Core.StatusException;
import Model.Todo;
import Service.TodoService;
import java.util.ArrayList;
import java.util.HashMap;

public class MockTodoServiceImpl implements TodoService {

    HashMap<Integer, Todo> mockDao = new HashMap<>();
    Integer mockId = 0;

    @Override
    public Todo getTodoById(int todoId) throws StatusException {
        return mockDao.get(todoId);
    }

    @Override
    public ArrayList<Todo> getTodosOfProject(int projectId) throws StatusException {
        ArrayList<Todo> todosOfUser = new ArrayList<>();
        for (Todo todo : (Todo[]) mockDao.values().toArray()) {
            if (todo.getProject().getID() == projectId) {
                todosOfUser.add(todo);
            }
        }
        return todosOfUser;

    }

    @Override
    public void createTodo(Todo todo) throws StatusException {
        todo.setID(mockId);
        mockDao.put(mockId, todo);
        mockId++;
    }

    @Override
    public void updateTodo(Todo todo) throws StatusException {
        mockDao.replace(todo.getID(), todo);
    }

    @Override
    public void deleteTodo(int todoId) throws StatusException {
        mockDao.remove(todoId);

    }

    @Override
    public void deleteTodosOfProject(int projectId) throws StatusException {
        for (Todo todo : (Todo[]) mockDao.values().toArray()) {
            if (todo.getProject().getID() == projectId) {
                mockDao.remove(todo.getID());
            }
        }
    }

}
