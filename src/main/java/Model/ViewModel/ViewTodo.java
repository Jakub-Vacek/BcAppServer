/**
 * @author jakubvacek
 */
package Model.ViewModel;

import Model.Todo;
import java.sql.Timestamp;

public class ViewTodo {

    private final int ID;
    private final String description;
    private final Timestamp timeToFinish;
    private final Timestamp spentTime;
    private final boolean resolved;
    private final double status;

    public ViewTodo(Todo todo) {
        this.ID = todo.getID();
        this.description = todo.getDescription();
        this.timeToFinish = todo.getTimeToFinish();
        this.spentTime = todo.getSpentTime();
        this.resolved = todo.isResolved();
        this.status = todo.getStatus();
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getTimeToFinish() {
        return timeToFinish;
    }

    public Timestamp getSpentTime() {
        return spentTime;
    }

    public boolean isResolved() {
        return resolved;
    }

    public double getStatus() {
        return Math.round(status);
    }

}
