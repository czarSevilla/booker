package czar.booker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import czar.booker.entities.BookNote;

@Repository("bookNoteRepository")
public interface BookNoteRepository extends JpaRepository<BookNote, Long> {
	@Query("select n from BookNote n where idBook = ? and (idUser = ? or general = ?) order by creation, modification")
	List<BookNote> findByIdBookAndIdUser(Long idBook, Long idUser, boolean isGeneral);
}
