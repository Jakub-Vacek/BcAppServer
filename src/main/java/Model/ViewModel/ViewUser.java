/**
 * @author jakubvacek
 */

package Model.ViewModel;

import Model.User;
import java.sql.Timestamp;

public class ViewUser {
    private final int ID;
    private final String username;
    private final String role;
    private final String description;
    private final Timestamp createdOn;
    
    public ViewUser(User user){
    this.ID = user.getID();
    this.username = user.getUsername();
    this.role = user.getRole();
    this.description = user.getDescription();
    this.createdOn = user.getCreatedOn();
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }
    
    
    
}
