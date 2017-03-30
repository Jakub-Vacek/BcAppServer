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
    private final String userRole;
    //Todo
    private final String todoDescription;
    //Project
    private final String projectName;
    //Detail information
    private final TodoTo todo;
    private final ProjectTo project;
    private final UserTo logedUser;
    private final UserTo selectedUser;

    public ActivityTo(Activity activity, boolean fetchDetailInfo) {
        this.ID = activity.getID();
        this.createdOn = activity.getCreatedOn();
        this.description = activity.getDescription();
        //Users
        this.username = activity.getLogedUser().getUsername();
        this.userRole = activity.getLogedUser().getRole();
        if (fetchDetailInfo) {
            this.logedUser = new UserTo(activity.getLogedUser());
            this.selectedUser = new UserTo(activity.getSelectedUser());
        } else {
            this.selectedUser = null;
            this.logedUser = null;
        }
        //Todo
        if (activity.getTodo() != null) {
            this.todoDescription = activity.getTodo().getDescription();
            if (fetchDetailInfo) {
                this.todo = new TodoTo(activity.getTodo(), false);
            } else {
                this.todo = null;
            }
        } else {
            this.todoDescription = null;
            this.todo = null;
        }
        //Project
        if (activity.getProject() != null) {
            this.projectName = activity.getProject().getName();
            if (fetchDetailInfo) {
                this.project = new ProjectTo(activity.getProject(), false);
            } else {
                this.project = null;
            }
        } else {
            this.projectName = null;
            this.project = null;
        }

    }

    public TodoTo getTodo() {
        return todo;
    }

    public ProjectTo getProject() {
        return project;
    }

    public UserTo getLogedUser() {
        return logedUser;
    }

    public UserTo getSelectedUser() {
        return selectedUser;
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

    public String getUserRole() {
        return userRole;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public String getProjectName() {
        return projectName;
    }
}
