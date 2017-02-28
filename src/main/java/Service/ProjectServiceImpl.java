/**
 * @author jakubvacek
 */
package Service;

import Core.StatusException;
import Model.Project;
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
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOG = Logger.getLogger(ProjectServiceImpl.class.getName());
    private final String DATABASE_URL = "jdbc:derby:\\MyDB\\demo";
    private Dao<Project, Integer> dao;

    public ProjectServiceImpl() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
            dao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Project.class);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createProject(Project project) throws StatusException {
        try {
            dao.create(project);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteProjectById(int projectId) throws StatusException {
        try {
            if (dao.idExists(projectId)) {
                dao.deleteById(projectId);
            } else {
                throw new StatusException(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Project getProjectById(int projectId) throws StatusException {
        try {
            return dao.queryForId(projectId);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ArrayList<Project> getProjectsOfUser(int userId) throws StatusException {
        try {
            return (ArrayList<Project>) dao.queryBuilder().where().eq("userID", userId).query();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateProject(Project project) throws StatusException {
        try {
            dao.update(project);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new StatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
