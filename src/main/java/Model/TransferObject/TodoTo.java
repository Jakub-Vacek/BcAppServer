/**
 * @author jakubvacek
 */
package Model.TransferObject;

import Model.Todo;
import java.sql.Timestamp;

//This class represents transfer model of todo
public class TodoTo {

    private final int ID;
    private final String description;
    private final Timestamp timeToFinish;
    private final Timestamp spentTime;
    private final boolean resolved;
    private final double status;
    //detail information
    private final ProjectTo project;
    private final Timestamp createdOn;
    private final Timestamp resolveUntil;

    public TodoTo(Todo todo, boolean fecthDetailInfo) {
        this.ID = todo.getID();
        this.description = todo.getDescription();
        this.timeToFinish = todo.getTimeToFinish();
        this.spentTime = todo.getSpentTime();
        this.resolved = todo.isResolved();
        this.status = todo.getStatus();
        if(fecthDetailInfo){
        this.project = new ProjectTo(todo.getProject(),true);
        this.createdOn = todo.getCreatedOn();
        this.resolveUntil = todo.getResolveUntil();
        }
        else{
        this.project = null;
        this.createdOn = null;
        this.resolveUntil = null;                
        }
    }

    public ProjectTo getProject() {
        return project;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public Timestamp getResolveUntil() {
        return resolveUntil;
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
