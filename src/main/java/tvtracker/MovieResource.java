package tvtracker;

import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class MovieResource extends ResourceSupport {
    private final Movie movie;

    public MovieResource(Movie movie) {
        long theId = movie.getId();
        this.movie = movie;
        this.add(linkTo(methodOn(MoviesController.class,theId).getOneMovie(theId)).withSelfRel());
    }

}
