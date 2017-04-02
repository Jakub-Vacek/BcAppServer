/**
 * @author jakubvacek
 */
package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.Objects;

//This class represents one todo in project
@DatabaseTable(tableName = "todos")
public class Todo {

    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField()
    private Timestamp createdOn;
    @DatabaseField()
    private Timestamp resolveUntil;
    @DatabaseField()
    private String description;
    @DatabaseField()
    private boolean resolved;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "projectID")
    private Project project;
    @DatabaseField()
    private double status;
    @DatabaseField()
    private Timestamp timeToFinish;
    @DatabaseField()
    private Timestamp spentTime;

    public Todo() {
    }

    public Todo(int ID, Timestamp createdOn, Timestamp resolveUntil, String description, boolean resolved, Project project, double status, Timestamp timeToFinish, Timestamp spentTime) {
        this.ID = ID;
        this.createdOn = createdOn;
        this.resolveUntil = resolveUntil;
        this.description = description;
        this.resolved = resolved;
        this.project = project;
        this.status = status;
        this.timeToFinish = timeToFinish;
        this.spentTime = spentTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getResolveUntil() {
        return resolveUntil;
    }

    public void setResolveUntil(Timestamp resolveUntil) {
        this.resolveUntil = resolveUntil;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public double getStatus() {
        return Math.round(status);
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public Timestamp getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(Timestamp timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    public Timestamp getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Timestamp spentTime) {
        this.spentTime = spentTime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.ID;
        hash = 41 * hash + Objects.hashCode(this.createdOn);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.status) ^ (Double.doubleToLongBits(this.status) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.spentTime);
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
        final Todo other = (Todo) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        if (!Objects.equals(this.resolveUntil, other.resolveUntil)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (this.resolved != other.resolved) {
            return false;
        }
        if (Double.doubleToLongBits(this.status) != Double.doubleToLongBits(other.status)) {
            return false;
        }
        if (!Objects.equals(this.timeToFinish, other.timeToFinish)) {
            return false;
        }
        return Objects.equals(this.spentTime, other.spentTime);
    }

    @Override
    public String toString() {
        return "Todo{" + "ID=" + ID + ", createdOn=" + createdOn + ", resolveUntil=" + resolveUntil + ", description=" + description + ", resolved=" + resolved + ", status=" + status + ", timeToFinish=" + timeToFinish + ", spentTime=" + spentTime + ", projectid: " + project+ '}';
    }



}
