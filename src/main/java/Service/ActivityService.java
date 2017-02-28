package Service;

import Core.StatusException;
import Model.Activity;
import java.util.ArrayList;

/**
 *
 * @author jakubvacek
 */
public interface ActivityService {

    /**
     * Creates new activity in database
     * @param activity activity to be created 
     * @throws StatusException
     */
    void createActivity(Activity activity) throws StatusException;

    /**
     * Gets activities of specified user
     * @param id id of user
     * @return ArrayList of activities
     * @throws StatusException
     */
    ArrayList<Activity> getActivitiesOfUser(int id) throws StatusException;

    /**
     * Gets activity witch specified Id
     * @param Id id of activity
     * @return
     * @throws StatusException
     */
    Activity getActivityByid(int Id) throws StatusException;

}
