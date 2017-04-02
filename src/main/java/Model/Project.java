/**
 * @author jakubvacek
 */

package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.Objects;

//This class represents one prject of user
@DatabaseTable(tableName = "projects")
public class Project {
 @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "userID")    
    private User user;
    @DatabaseField()   
    private String name;
    @DatabaseField()   
    private String description;
    @DatabaseField()
    private Timestamp createdOn;
    @DatabaseField()
    private double status;

    public Project(){
    }

    public Project(int ID, User user, String name, String description, Timestamp createdOn, double status) {
        this.ID = ID;
        this.user = user;
        this.name = name;
        this.description = description;
        this.createdOn = createdOn;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public double getStatus() {
        return Math.round(status);
    }

    public void setStatus(double status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.ID;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.createdOn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        return Double.doubleToLongBits(this.status) == Double.doubleToLongBits(other.status);
    }

    @Override
    public String toString() {
        return "Project{" + "ID=" + ID + ", name=" + name + ", description=" + description + ", createdOn=" + createdOn + ", status=" + status + '}';
    }
    
    
    
}
