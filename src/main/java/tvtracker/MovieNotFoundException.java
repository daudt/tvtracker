package tvtracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(long id) {
        super("could not find movie with id " + id);
    }
}
