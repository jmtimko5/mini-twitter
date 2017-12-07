package challenge.user;

import challenge.exceptions.DataQueryException;
import challenge.exceptions.ObjectNotFoundException;
import challenge.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public User getUserByHandle(String handle) throws DataQueryException {

        String query = "select * from PEOPLE where handle = :handle";

        SqlParameterSource namedParameters = new MapSqlParameterSource("handle", handle);

        try{
            return (User) namedParameterJdbcTemplate.queryForObject(query,
                    namedParameters, new RowMapper() {
                        public Object mapRow(ResultSet resultSet, int rowNum)
                                throws SQLException {
                            return new User(resultSet.getInt("ID"), resultSet.getString("HANDLE"), resultSet.getString("NAME"), resultSet.getString("PASSWORD"));
                        }
                    });
        } catch(Exception e){
            logger.error(String.format("Error querying for user by handle %s: %s", handle, e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }

    }

    @Override
    public User getUserById(int id) throws DataQueryException {

        String query = "select * from PEOPLE where id = :id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        try{
            return (User) namedParameterJdbcTemplate.queryForObject(query,
                    namedParameters, new RowMapper() {
                        public Object mapRow(ResultSet resultSet, int rowNum)
                                throws SQLException {
                            return new User(resultSet.getInt("ID"), resultSet.getString("HANDLE"), resultSet.getString("NAME"), resultSet.getString("PASSWORD"));
                        }
                    });
        } catch(Exception e){
            logger.error(String.format("Error querying for user by ic %d: %s", id, e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }


    }

    @Override
    public List<User> getAllFollowingForUser(int id) throws DataQueryException {
        String query = "select * from FOLLOWERS where follower_person_id = :follower_person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("follower_person_id", id);

        try{
            return (List<User>) namedParameterJdbcTemplate.query(query,
                    namedParameters, new RowMapper() {
                        public Object mapRow(ResultSet resultSet, int rowNum)
                                throws SQLException {
                                return getUserById(resultSet.getInt("PERSON_ID"));
                        }});
        } catch(Exception e){
            logger.error(String.format("Error querying following for user %d: %s", id, e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllFollowersForUser(int id) throws DataQueryException {
        String query = "select * from FOLLOWERS where person_id = :person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("person_id", id);

        try{
            return (List<User>) namedParameterJdbcTemplate.query(query,
                    namedParameters, new RowMapper() {
                        public Object mapRow(ResultSet resultSet, int rowNum)
                                throws SQLException {
                                return getUserById(resultSet.getInt("FOLLOWER_PERSON_ID"));
                        }});
        } catch(Exception e){
            logger.error(String.format("Error querying followers for user %d: %s", id, e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }

    }

    @Override
    public User followUser(int id, int idToFollow) throws DataQueryException, ObjectNotFoundException {

        String sql = "insert into FOLLOWERS (PERSON_ID, FOLLOWER_PERSON_ID) values (:person_id, :follower_person_id)";
        String sqlCheck = "select count(*) from FOLLOWERS where PERSON_ID = :person_id AND FOLLOWER_PERSON_ID = :follower_person_id";
        String sqlValidate = "select * from FOLLOWERS where PERSON_ID = :person_id and FOLLOWER_PERSON_ID = :follower_person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("person_id", idToFollow)
                .addValue("follower_person_id", id);

        try {
            boolean exists = this.namedParameterJdbcTemplate.queryForObject(sqlCheck, namedParameters, Integer.class) > 0;

            if (!exists) {
                namedParameterJdbcTemplate.update(sql, namedParameters);
            }
        } catch(Exception e){
            logger.error(String.format("Error updating relationship between users %d and %d: %s", id, idToFollow, e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }

        try{
            return (User) namedParameterJdbcTemplate.queryForObject(sqlValidate,
                    namedParameters, new RowMapper() {
                        public Object mapRow(ResultSet resultSet, int rowNum)
                                throws SQLException {
                            return getUserById(resultSet.getInt("PERSON_ID"));
                        }
                    });
        } catch(Exception e){
            logger.error(String.format("Update of follow between users %d and %d was successful but cannot be found: %s", id, idToFollow, e.getMessage()));
            throw new ObjectNotFoundException(e.getMessage());
        }

    }

    @Override
    public User unfollowUser(int id, int idToUnfollow) throws DataQueryException {
        String sql = "delete from FOLLOWERS where PERSON_ID = :person_id and FOLLOWER_PERSON_ID = :follower_person_id";
        String sqlCheck = "select count(*) from FOLLOWERS where PERSON_ID = :person_id AND FOLLOWER_PERSON_ID = :follower_person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("person_id", idToUnfollow)
                .addValue("follower_person_id", id);

        boolean exists;
        try{
            exists =  this.namedParameterJdbcTemplate.queryForObject(sqlCheck, namedParameters, Integer.class) > 0;

            if(exists){
                namedParameterJdbcTemplate.update(sql, namedParameters);
            }

            exists =  this.namedParameterJdbcTemplate.queryForObject(sqlCheck, namedParameters, Integer.class) > 0;

        } catch(Exception e){
            logger.error(String.format("Error querying for relationship between users %d and %d: %s", id, idToUnfollow, e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }

        if(!exists){
            return getUserById(idToUnfollow);
        } else{
            logger.error(String.format("Delete of ID:%d following ID:%d executed but did not modify database", id, idToUnfollow));
            throw new DataQueryException(String.format("Delete of ID:%d following ID:%d executed but did not modify database", id, idToUnfollow));
        }
    }

    @Override
    public List<User> getAllUsers() throws DataQueryException {
        String sql = "select * from PEOPLE";

        try{
            return (List<User>) namedParameterJdbcTemplate.query(sql, new RowMapper() {
                public Object mapRow(ResultSet resultSet, int rowNum)
                        throws SQLException {
                    return new User(resultSet.getInt("ID"), resultSet.getString("HANDLE"), resultSet.getString("NAME"), resultSet.getString("PASSWORD"));
                }
            });
        } catch(Exception e){
            logger.error(String.format("Error querying for all users: %s", e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }
    }
}
