package tvtracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tvtracker.model.Movie;

public interface IMovieRepository extends JpaRepository<Movie, Long> {}
