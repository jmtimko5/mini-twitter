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
}
