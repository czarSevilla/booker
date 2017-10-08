package czar.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.booker.entities.DeprecatedBook;

@Repository("deprecatedBookRepository")
public interface DeprecatedBookRepository extends JpaRepository<DeprecatedBook, Long> {
	DeprecatedBook findOneByIdBookAndIdUser(Long idBook, Long idUser);
}
