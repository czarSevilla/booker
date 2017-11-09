package czar.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.booker.entities.BookRating;

@Repository("bookRatingRepository")
public interface BookRatingRepository extends JpaRepository<BookRating, Long> {
	BookRating findOneByIdBookAndIdUser(Long idBook, Long idUser);
}
