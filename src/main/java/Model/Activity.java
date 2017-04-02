/**
 * @author jakubvacek
 */
package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.Objects;

//This class represents one activity of user
@DatabaseTable(tableName = "Activities")
public class Activity {
    @DatabaseField(canBeNull = false, foreign = true, columnName = "logedUserID")
    private User logedUser;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "selectedUserID")
    private User selectedUser;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "projectID")
    private Project project;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "todoID")
    private Todo todo;
    @DatabaseField()
    private Timestamp createdOn;
    @DatabaseField(canBeNull = true)
    private Timestamp duration;
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField(canBeNull = false)
    private String description;

    public Activity(String description) {
        this.description = description;
    }

    public Activity() {
    }

    public User getLogedUser() {
        return logedUser;
    }

    public void setLogedUser(User logedUser) {
        this.logedUser = logedUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

   

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getDuration() {
        return duration;
    }

    public void setDuration(Timestamp duration) {
        this.duration = duration;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.createdOn);
        hash = 97 * hash + Objects.hashCode(this.duration);
        hash = 97 * hash + Objects.hashCode(this.description);
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
        final Activity other = (Activity) obj;
        if (!Objects.equals(this.selectedUser, other.selectedUser)) {
            return false;
        }
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.todo, other.todo)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }

    @Override
    public String toString() {
        return "Activity{" + "createdOn=" + createdOn + ", duration=" + duration + ", ID=" + ID + ", description=" + description + '}';
    }



    
}
