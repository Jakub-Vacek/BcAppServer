/**
 * @author jakubvacek
 */
package Mock;

import Core.StatusException;
import Model.Activity;
import Service.ActivityService;
import java.util.ArrayList;
import java.util.HashMap;

public class MockActivityServiceImpl implements ActivityService {

    HashMap<Integer, Activity> mockDao = new HashMap<>();
    Integer mockId = 0;

    @Override
    public void createActivity(Activity activity) throws StatusException {
        activity.setID(mockId);
        mockDao.put(mockId, activity);
        mockId++;
    }

    @Override
    public ArrayList<Activity> getActivitiesOfUser(int id) throws StatusException {
        ArrayList<Activity> activitiesOfUser = new ArrayList<>();
        for (Activity activity : (Activity[]) mockDao.values().toArray()) {
            if (activity.getLogedUser().getID() == id) {
                activitiesOfUser.add(activity);
            }
        }
        return activitiesOfUser;
    }

    @Override
    public Activity getActivityByid(int Id) throws StatusException {
        return mockDao.get(Id);
    }

}
