package challenge.messages;

import challenge.exceptions.DataQueryException;
import challenge.model.Message;
import challenge.model.User;
import challenge.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Override
    public List<Message> getMessagesForUser(int id) throws DataQueryException {
        String query = "select * from MESSAGES where person_id = :person_id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("person_id", id);
        try{
            return (List<Message>) namedParameterJdbcTemplate.query(query,
                    namedParameters, new RowMapper() {
                        public Object mapRow(ResultSet resultSet, int rowNum)
                                throws SQLException {
                            return new Message(resultSet.getInt("ID"), resultSet.getInt("PERSON_ID"), resultSet.getString("CONTENT"));
                        }});
        } catch(Exception e){
            logger.error(String.format("Error querying messages for id %d: %s", id, e.getMessage()));
            throw new DataQueryException(e.getMessage());
        }
    }
}
