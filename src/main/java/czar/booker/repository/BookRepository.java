package czar.booker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import czar.booker.entities.Book;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("select b from Book b left join b.authors a where a.idAuthor = ? order by b.publicationDate desc")
	List<Book> findByIdAuthor(Long idAuthor);
	
	@Query("select b from Book b where b.publisher.idPublisher = ? order by b.publicationDate desc")
	List<Book> findByIdPublisher(Long idPublisher);
	
	@Query("select b from Book b, BookAccess ba where b.idBook = ba.idBook and ba.idUser = ? order by ba.access desc")
	Page<Book> findLastView(Long idUser, Pageable pageable);
	
	@Query("select b from Book b order by b.creation desc")
	Page<Book> findNews(Pageable pageable);
	
	@Query("select b from Book b, DeprecatedBook deb where b.idBook = deb.idBook and deb.idUser = ? order by deb.idDeprecated desc")
	Page<Book> findDeprecated(Long idUser, Pageable pageable);
	
	@Query("select b from Book b, BookRating br where b.idBook = br.idBook and br.idUser = ? order by br.rating desc, br.creation desc, br.modification desc")
	Page<Book> findBest(Long idUser, Pageable pageable);
	
	@Query("select b from Book b, BookTag bt join bt.tag t where b.idBook = bt.idBook and t.name = ? and bt.idUser = ? order by bt.idBookTag desc")
	List<Book> findByTagName(String tagName, Long idUser);
}
