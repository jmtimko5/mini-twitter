package challenge.user;

import challenge.model.Message;
import challenge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{


    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


//
//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//    }

    @Override
    public User getUserByHandle(String handle) {

        String query = "select * from PEOPLE where handle = :handle";

        SqlParameterSource namedParameters = new MapSqlParameterSource("handle", handle);

        return (User) namedParameterJdbcTemplate.queryForObject(query,
                namedParameters, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return new User(resultSet.getInt("ID"), resultSet.getString("HANDLE"), resultSet.getString("NAME"));
                    }
                });
    }

    @Override
    public User getUserById(int id) {

        String query = "select * from PEOPLE where id = :id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

        return (User) namedParameterJdbcTemplate.queryForObject(query,
                namedParameters, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return new User(resultSet.getInt("ID"), resultSet.getString("HANDLE"), resultSet.getString("NAME"));
                    }
                });
    }

    @Override
    public List<User> getAllFollowingForUser(int id) {
        String query = "select * from FOLLOWERS where follower_person_id = :follower_person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("follower_person_id", id);

        return (List<User>) namedParameterJdbcTemplate.query(query,
                namedParameters, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return getUserById(resultSet.getInt("PERSON_ID"));
                    }});
    }

    @Override
    public List<User> getAllFollowersForUser(int id) {
        String query = "select * from FOLLOWERS where person_id = :person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("person_id", id);

        return (List<User>) namedParameterJdbcTemplate.query(query,
                namedParameters, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return getUserById(resultSet.getInt("FOLLOWER_PERSON_ID"));
                    }});
    }

    @Override
    public User followUser(int id, int idToFollow) {

        String sql = "INSERT INTO FOLLOWERS (PERSON_ID, FOLLOWER_PERSON_ID) VALUES (:person_id, :follower_person_id)";
        String sqlCheck = "select count(*) from FOLLOWERS where PERSON_ID = :person_id AND FOLLOWER_PERSON_ID = :follower_person_id";
        String sqlValidate = "select * from FOLLOWERS where PERSON_ID = :person_id and FOLLOWER_PERSON_ID = :follower_person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("person_id", idToFollow)
                .addValue("follower_person_id", id);

        boolean exists =  this.namedParameterJdbcTemplate.queryForObject(sqlCheck, namedParameters, Integer.class) > 0;

        if(!exists){
            namedParameterJdbcTemplate.update(sql, namedParameters);
        }

        return (User) namedParameterJdbcTemplate.queryForObject(sqlValidate,
                namedParameters, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return getUserById(resultSet.getInt("PERSON_ID"));
                    }
                });
    }
}
