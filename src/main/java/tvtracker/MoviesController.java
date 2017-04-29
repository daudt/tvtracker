package tvtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tvtracker.repo.IMovieRepository;
import tvtracker.model.Movie;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "/movies")
public class MoviesController {

    private IMovieRepository repo;

    @Autowired
    public MoviesController(IMovieRepository repo) {
        this.repo = repo;
    }

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
    public Collection<Movie> getAllMovies() {
        return repo.findAll();
    }


    // UPDATE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Movie> updateMovie(@PathVariable long id, @Valid @RequestBody Movie newMovie) {
        Movie originalMovie = getMovie(id);
        originalMovie.setTitle(newMovie.getTitle());
        originalMovie.setYear(newMovie.getYear());
        return new ResponseEntity<>(repo.save(originalMovie), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Movie> partiallyUpdateMovie(@PathVariable long id, @RequestBody Movie newMovie) {
        Movie originalMovie = getMovie(id);
        originalMovie = patchMovie(originalMovie, newMovie);
        return new ResponseEntity<>(repo.save(originalMovie), HttpStatus.OK);
    }


    // DELETE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteMovie(@PathVariable long id) {
        repo.delete(getMovie(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    private Movie getMovie(long id) {
        Movie movie = repo.findOne(id);
        if(movie != null) {
            return movie;
        }
        throw new MovieNotFoundException(id);
    }

    private Movie patchMovie(Movie originalMovie, Movie newMovie) {
        String title = newMovie.getTitle();
        Integer year = newMovie.getYear();
        if(title != null) {
            originalMovie.setTitle(title);
        }
        if(year != null) {
            originalMovie.setYear(year);
        }
        return originalMovie;
    }

}