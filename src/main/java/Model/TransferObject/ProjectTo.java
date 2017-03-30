/**
 * @author jakubvacek
 */
//Simplified model used in tables
package Model.TransferObject;

import Model.Project;
import java.sql.Timestamp;

public class ProjectTo {

    private final int ID;
    private final String name;
    private final String description;
    private final Timestamp createdOn;
    private final double status;

    //detail information
    private final UserTo user;

    public ProjectTo(Project project, boolean fetchDetailInfo) {
        this.ID = project.getID();
        this.name = project.getName();
        this.description = project.getDescription();
        this.createdOn = project.getCreatedOn();
        this.status = project.getStatus();

        if (fetchDetailInfo) {
            this.user = new UserTo(project.getUser());
        } else {
            this.user = null;
        }
    }
    public UserTo getUser() {
        return user;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public double getStatus() {
        return Math.round(status);
    }
}
