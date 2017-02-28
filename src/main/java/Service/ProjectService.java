package Service;

import Core.StatusException;
import Model.Project;
import java.util.ArrayList;

/**
 *
 * @author jakubvacek
 */
public interface ProjectService {

    /**
     * Creates new Project object in database
     * @param project 
     * @throws StatusException
     */
    void createProject(Project project) throws StatusException;

    /**
     * Deletes project with specified id
     * @param projecId
     * @throws StatusException
     */
    void deleteProjectById(int projecId) throws StatusException;

    /**
     * Gets project with specified Id
     * @param projecId
     * @return project
     * @throws StatusException
     */
    Project getProjectById(int projecId) throws StatusException;

    /**
     * Gets projects od specified user
     * @param userId
     * @return ArrayList of project
     * @throws StatusException
     */
    ArrayList<Project> getProjectsOfUser(int userId) throws StatusException;

    /**
     * Updates project
     * @param project to be updated
     * @throws StatusException
     */
    void updateProject(Project project) throws StatusException;
}
