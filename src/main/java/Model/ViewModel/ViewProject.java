/**
 * @author jakubvacek
 */

package Model.ViewModel;

import Model.Project;
import java.sql.Timestamp;

public class ViewProject {
    private final int ID;
    private final String name;
    private final String description;
    private final Timestamp createdOn;
    private final double status;
    
    public ViewProject(Project project){
    this.ID = project.getID();
    this.name = project.getName();
    this.description = project.getDescription();
    this.createdOn = project.getCreatedOn();
    this.status = project.getStatus(); 
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
