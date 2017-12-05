package challenge.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int getIdForUser(String username) {

        String sql = "select count(*) from T_ACTOR where first_name = :firstName and last_name = :lastName";

//        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(exampleActor);
//
//        return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
        return 0;
    }
}
