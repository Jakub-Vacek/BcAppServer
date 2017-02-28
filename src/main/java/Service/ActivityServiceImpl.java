/**
 * @author jakubvacek
 */
package Service;

import Core.StatusException;
import Model.Activity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger LOG = Logger.getLogger(ActivityServiceImpl.class.getName());
    private final String DATABASE_URL = "jdbc:derby:\\MyDB\\demo";
    private Dao<Activity, Integer> dao;

    public ActivityServiceImpl() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
            dao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Activity.class);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createActivity(Activity activity) throws StatusException {
        try {
            if (activity.getLogedUser() != null) {
                dao.create(activity);
            } else {
                throw new StatusException(HttpStatus.NO_CONTENT);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ArrayList<Activity> getActivitiesOfUser(int userId) throws StatusException {
        try {
            return (ArrayList<Activity>) dao.queryBuilder().where().eq("logedUserID", userId).query();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public Activity getActivityByid(int Id) throws StatusException {
        try {
            return dao.queryForId(Id);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.FORBIDDEN);
        }
    }
}
