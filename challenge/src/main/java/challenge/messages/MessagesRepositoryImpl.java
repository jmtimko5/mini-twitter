package challenge.messages;

import challenge.model.Message;
import challenge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MessagesRepositoryImpl implements MessagesRepository{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Message> getMessagesForUser(int id) {
        String query = "select * from MESSAGES where person_id = :person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("person_id", id);

        return (List<Message>) namedParameterJdbcTemplate.query(query,
                namedParameters, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return new Message(resultSet.getInt("ID"), resultSet.getInt("PERSON_ID"), resultSet.getString("CONTENT"));
                    }});
    }
}
