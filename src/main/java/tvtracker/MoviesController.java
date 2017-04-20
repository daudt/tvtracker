package tvtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/movies")
public class MoviesController {

    @Autowired
    private IMovieRepository repo;

    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) {
        return new ResponseEntity<>(repo.save(movie), HttpStatus.CREATED);
    }

    // READ
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Movie getOneMovie(@PathVariable long id) {
        return getMovie(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Movie> getAllMovies() {
        return repo.findAll();
    }


    // UPDATE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Movie updateMovie(@PathVariable long id, @Valid @RequestBody Movie newMovie) {
        Movie movie = getMovie(id);
        movie.setTitle(newMovie.getTitle());
        movie.setYear(newMovie.getYear());
        return repo.save(movie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public Movie partiallyUpdateMovie(@PathVariable long id, @RequestBody Movie jsonString) {
        Movie movie = repo.findOne(id);
        //TODO: There must be a better way to do this...
        if(movie != null) {
            String title = jsonString.getTitle();
            int year = jsonString.getYear();
            if(title != null) {
                movie.setTitle(title);
            }
            if(year != 0) {
                movie.setYear(year);
            }
            return repo.save(movie);
        } else {
            throw new MovieNotFoundException(id);
        }
    }


    // DELETE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteMovie(@PathVariable long id) {
        repo.delete(getMovie(id));
        return ResponseEntity.noContent().build();
    }


    private Movie getMovie(long id) {
        Movie movie = repo.findOne(id);
        if(movie != null) {
            return movie;
        }
        throw new MovieNotFoundException(id);
    }
}