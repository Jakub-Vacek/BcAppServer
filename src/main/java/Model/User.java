/**
 * @author jakubvacek
 */
package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.Objects;

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField(uniqueCombo = true, canBeNull = false)
    private String username;
    @DatabaseField(uniqueCombo = true, canBeNull = false)
    private String passwordHash;
    @DatabaseField(canBeNull = false)
    private String role;
    @DatabaseField()
    private String description;
    @DatabaseField()
    private Timestamp createdOn;

    public User() {
    }

    public User(int id, String username, String passwordHash, String role, String description, Timestamp createdOn) {
        this.ID = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.description = description;
        this.createdOn = createdOn;
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

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.ID;
        hash = 89 * hash + Objects.hashCode(this.username);
        hash = 89 * hash + Objects.hashCode(this.passwordHash);
        hash = 89 * hash + Objects.hashCode(this.description);
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
        final User other = (User) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", username=" + username + ", role=" + role + ", description=" + description + ", createdOn=" + createdOn + '}';
    }


}
