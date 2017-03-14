/**
 * @author jakubvacek
 */
package Mock;

import Core.StatusException;
import Model.Project;
import Service.ProjectService;
import java.util.ArrayList;
import java.util.HashMap;

public class MockProjectServiceImpl implements ProjectService {

    HashMap<Integer, Project> mockDao = new HashMap<>();
    Integer mockId = 0;

    @Override
    public void createProject(Project project) throws StatusException {
        project.setID(mockId);
        mockDao.put(mockId, project);
        mockId++;
    }

    @Override
    public void deleteProjectById(int projectId) throws StatusException {
        if (mockDao.get(projectId) != null) {
            mockDao.remove(projectId);
        }

    }

    @Override
    public Project getProjectById(int projectId) throws StatusException {
        return mockDao.get(projectId);
    }

    @Override
    public ArrayList<Project> getProjectsOfUser(int userId) throws StatusException {
        ArrayList<Project> projectsOfUser = new ArrayList<>();
        for (Project project : (Project[]) mockDao.values().toArray()) {
            if (project.getUser().getID() == userId) {
                projectsOfUser.add(project);
            }
        }
        return projectsOfUser;
    }

    @Override
    public void updateProject(Project project) throws StatusException {
        mockDao.replace(project.getID(), project);
    }

}
