package tvtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private IMovieRepository repo;

    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Movie createMovie(@RequestBody Movie jsonString) {
        Movie movie = new Movie(jsonString.getTitle(), jsonString.getYear());
        return repo.save(movie);
    }

    // READ
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Movie getOneMovie(@PathVariable long id) {
        Movie movie =  repo.findOne(id);
        return movie;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Movie> getAllMovies() {
        return repo.findAll();
    }


    // UPDATE
    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Movie readMovie(@PathVariable long id, String title, Integer year) {
        Movie movie = repo.findOne(id);
        movie.setTitle(title);
        movie.setYear(year);
        return repo.save(movie);
    }

    // DELETE
    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteMovie(@PathVariable long id) {
        try {
            repo.delete(id);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "deletion successful";
    }

    @RequestMapping("/movies/readAllBeforeYear")
    public List<Movie> getMoviesBeforeYear(@RequestParam(value = "year") int year) {
        List<Movie> movies = repo.findByYearLessThan(year);
        return movies;
    }
}