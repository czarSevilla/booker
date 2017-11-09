package czar.booker.service;

import java.util.List;

import org.springframework.data.domain.Page;

import czar.booker.entities.Book;
import czar.booker.entities.BookNote;
import czar.booker.entities.BookTag;

public interface BookService {
	
	Page<Book> findAll();
	Book findById(Long idBook);
	boolean isDeprecated(Long idBook, Long idUser);
	boolean toogleDeprecate(Long idBook, Long idUser);
	Integer findRatingByIdBookAndIdUser(Long idBook, Long idUser);
	void asignRating(Long idBook, Long idUser, Integer rating);
	void saveBookAccess(Long idBook, Long idUser);
	List<Book> findByIdAuthor(Long idAuthor);
	List<Book> findByIdPublisher(Long idPublisher);
	List<BookNote> findNotesByIdBookAndIdUser(Long idBook, Long idUser);
	BookNote findOneBookNoteById(Long idBookNote);
	void saveBookNote(BookNote bookNote);
	List<BookTag> findTagsByIdBookAndIdUser(Long idBook, Long idUser);
	void addBookTag(Long idBook, Long idUser, String tagName);
	List<Book> findLast100View(Long idUser);
	List<Book> findTop100News();
	List<Book> findTop100Deprecated(Long idUser);
	List<Book> findBestTop100(Long idUser);
	void assignTag(Long idTag, Long idUser, String idsBooks);
	List<Book> findByTagName(String tagName, Long idUser);
	void removeBookTag(Long idBookTag);
}
