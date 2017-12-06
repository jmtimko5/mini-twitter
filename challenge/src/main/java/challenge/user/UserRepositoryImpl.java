package challenge.user;

import challenge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public User getIdForUser(String username) {

        String query = "select * from PEOPLE where handle = :handle";

        SqlParameterSource namedParameters = new MapSqlParameterSource("handle", "batman");

        return (User) namedParameterJdbcTemplate.queryForObject(query,
                namedParameters, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return new User(resultSet.getInt("ID"), resultSet.getString("HANDLE"), resultSet.getString("NAME"));
                    }
                });
    }
}
