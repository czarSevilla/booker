package czar.booker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import czar.booker.entities.BookTag;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {

	@Query("select bt from BookTag bt where bt.idBook = ? and bt.idUser = ? order by bt.tag.name")
	List<BookTag> findByIdBookAndIdUser(Long idBook, Long idUser);
	
	@Query("select bt from BookTag bt where bt.idBook = ? and bt.idUser = ? and bt.tag.idTag = ?")
	BookTag findOneByIdBookAndIdUserAndIdTag(Long idBook, Long idUser, Long idTag);
	
	@Query("select count(bt) from BookTag bt where bt.tag.idTag = ?")
	int countByIdTag(Long idTag);
}
