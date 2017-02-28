/**
 * @author jakubvacek
 */
package Mock;

import Model.Activity;
import Model.Project;
import Model.Todo;
import Model.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MockDataInitializer {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String DATABASE_URL = "jdbc:derby:\\MyDB\\demo;create=true";
    private Dao<Todo, Integer> todoDao;
    private Dao<Project, Integer> projectDao;
    private Dao<User, Integer> userDao;
    private Dao<Activity, Integer> activityDao;

    public MockDataInitializer() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
            todoDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Todo.class);
            projectDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Project.class);
            userDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, User.class);
            activityDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Activity.class);
           
            setUpDatabase(connectionSource);
            dummyData();
        } catch (SQLException ex) {
            Logger.getLogger(MockDataInitializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void setUpDatabase(ConnectionSource connectionSource) throws SQLException {
        //todo
        TableUtils.dropTable(todoDao, true);
        TableUtils.createTable(connectionSource, Todo.class);
        //list
        TableUtils.dropTable(projectDao, true);
        TableUtils.createTable(connectionSource, Project.class);
        //user
        TableUtils.dropTable(userDao, true);
        TableUtils.createTable(connectionSource, User.class);
        //activity
        TableUtils.dropTable(activityDao, true);
        TableUtils.createTable(connectionSource, Activity.class);

    }

    public final void dummyData() throws SQLException {
        User a = new User(0, "Franta Lala", "a", "ADMIN", "mraveniště", Timestamp.valueOf(LocalDateTime.now()));
        a.setID(this.createUser(a));
        User ad = new User(0, "Admin", "a", "ADMIN", "admin", Timestamp.valueOf(LocalDateTime.now()));
        ad.setID(this.createUser(ad));
        User franta = new User(0, "User", "a", "USER", "test-user", Timestamp.valueOf(LocalDateTime.now()));
        franta.setID(this.createUser(franta));
    }

    public int createUser(User user) throws SQLException {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            return userDao.create(user);
    }
}
