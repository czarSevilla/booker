package czar.booker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.booker.entities.BookAccess;

@Repository("bookAccessRepository")
public interface BookAccessRepository extends JpaRepository<BookAccess, Long> {
	BookAccess findOneByIdBookAndIdUser(Long idBook, Long idUser);
}
