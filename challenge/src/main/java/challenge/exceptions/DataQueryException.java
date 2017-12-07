package challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataQueryException extends SQLException {
    public DataQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataQueryException(String message){
        super(message);
    }
}
