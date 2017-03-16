/**
 * @author jakubvacek
 */
//Simplified model used in tables
package Model.TransferObject;

import Model.Activity;
import java.sql.Timestamp;

public class ActivityTo {

    //Basic info
    private final int ID;
    private final String description;
    private final Timestamp createdOn;
    //User
    private final String username;
    private final int userId;
    private final String userRole;
    //Todo
    private final String todoDescription;
    private final Integer todoId;
    //Project
    private final String projectName;
    private final Integer projectId;

    public ActivityTo(Activity activity) {
        this.ID = activity.getID();
        this.createdOn = activity.getCreatedOn();
        this.description = activity.getDescription();
        //User
        this.userId = activity.getLogedUser().getID();
        this.username = activity.getLogedUser().getUsername();
        this.userRole = activity.getLogedUser().getRole();
        //Todo
        if (activity.getTodo() != null) {
            this.todoDescription = activity.getTodo().getDescription();
            this.todoId = activity.getTodo().getID();

        } else {
            this.todoDescription = null;
            this.todoId = null;
        }
        //Project
        if (activity.getProject() != null) {
            this.projectName = activity.getProject().getName();
            this.projectId = activity.getProject().getID();

        } else {
            this.projectName = null;
            this.projectId = null;

        }

    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public Integer getTodoId() {
        return todoId;
    }

    public String getProjectName() {
        return projectName;
    }

    public Integer getProjectId() {
        return projectId;
    }

  

}
