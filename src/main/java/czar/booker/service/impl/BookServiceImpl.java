package czar.booker.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import czar.booker.entities.Book;
import czar.booker.entities.BookAccess;
import czar.booker.entities.BookNote;
import czar.booker.entities.BookRating;
import czar.booker.entities.BookTag;
import czar.booker.entities.DeprecatedBook;
import czar.booker.entities.Tag;
import czar.booker.repository.BookAccessRepository;
import czar.booker.repository.BookNoteRepository;
import czar.booker.repository.BookRatingRepository;
import czar.booker.repository.BookRepository;
import czar.booker.repository.BookTagRepository;
import czar.booker.repository.DeprecatedBookRepository;
import czar.booker.repository.TagRepository;
import czar.booker.service.BookService;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
	
	private Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private DeprecatedBookRepository deprecatedBookRepository;
	
	@Autowired
	private BookRatingRepository bookRatingRepository;
	
	@Autowired
	private BookAccessRepository bookAccessRepository;
	
	@Autowired
	private BookNoteRepository bookNoteRepository;
	
	@Autowired
	private BookTagRepository bookTagRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Override
	public Page<Book> findAll() {
		PageRequest pageable = new PageRequest(1, 100, Sort.Direction.DESC, "year", "publicationDate");
		return bookRepository.findAll(pageable);
	}
	
	@Override
	public Book findById(Long idBook) {
		logger.info(String.format("findById(%d)", idBook));
		return bookRepository.findOne(idBook);
	}
	
	@Override
	public boolean isDeprecated(Long idBook, Long idUser) {
		DeprecatedBook deprecatedBook =  deprecatedBookRepository.findOneByIdBookAndIdUser(idBook, idUser);
		return deprecatedBook != null;
	}
	
	@Override
	public boolean toogleDeprecate(Long idBook, Long idUser) {
		try {
			DeprecatedBook deprecatedBook =  deprecatedBookRepository.findOneByIdBookAndIdUser(idBook, idUser);
			if (deprecatedBook != null) {
				deprecatedBookRepository.delete(deprecatedBook);
				return false;
			} else {
				deprecatedBook = new DeprecatedBook(idBook, idUser);
				deprecatedBookRepository.save(deprecatedBook);
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Integer findRatingByIdBookAndIdUser(Long idBook, Long idUser) {
		BookRating bookRating = bookRatingRepository.findOneByIdBookAndIdUser(idBook, idUser);
		if (bookRating != null) {
			return bookRating.getRating();
		}
		return null;
	}

	@Override
	public void asignRating(Long idBook, Long idUser, Integer rating) {
		BookRating bookRating = bookRatingRepository.findOneByIdBookAndIdUser(idBook, idUser);
		if (bookRating != null) {
			if (rating != null) {
				bookRating.setRating(rating);
				bookRating.setModification(new Date());
				bookRating.setModifiedBy("booker");
				bookRatingRepository.save(bookRating);
			} else {
				bookRatingRepository.delete(bookRating);
			}
		} else {
			if (rating != null) {
				bookRating = new BookRating(idBook, idUser, rating, new Date(), "booker");
				bookRatingRepository.save(bookRating);
			}
		}
	}

	@Override
	public void saveBookAccess(Long idBook, Long idUser) {
		BookAccess bookAccess = bookAccessRepository.findOneByIdBookAndIdUser(idBook, idUser);
		if (bookAccess == null) {
			bookAccess = new BookAccess(idBook, idUser, new Date());
		} else {
			bookAccess.setAccess(new Date());
		}
		bookAccessRepository.save(bookAccess);		
	}

	@Override
	public List<Book> findByIdAuthor(Long idAuthor) {
		return bookRepository.findByIdAuthor(idAuthor);
	}

	@Override
	public List<Book> findByIdPublisher(Long idPublisher) {
		return bookRepository.findByIdPublisher(idPublisher);
	}

	@Override
	public List<BookNote> findNotesByIdBookAndIdUser(Long idBook, Long idUser) {
		return bookNoteRepository.findByIdBookAndIdUser(idBook, idUser, true);
	}

	@Override
	public BookNote findOneBookNoteById(Long idBookNote) {
		return bookNoteRepository.findOne(idBookNote);
	}

	@Override
	public void saveBookNote(BookNote bookNote) {
		bookNoteRepository.save(bookNote);		
	}

	@Override
	public List<BookTag> findTagsByIdBookAndIdUser(Long idBook, Long idUser) {
		return bookTagRepository.findByIdBookAndIdUser(idBook, idUser);
	}

	@Override
	public void addBookTag(Long idBook, Long idUser, String tagName) {
	    if (tagName != null && !tagName.trim().equals("")) {
	        Tag tag = tagRepository.findOneByName(tagName);
	        
	        if (tag == null) {
	            tag = new Tag();
	            tag.setName(tagName);
	            tag.setCreatedBy("booker");
	            tag.setCreation(new Date());
	            tagRepository.save(tag);
	            
	            BookTag bookTag = new BookTag();
	            bookTag.setIdBook(idBook);
	            bookTag.setIdUser(idUser);
	            bookTag.setTag(tag);
	            bookTagRepository.save(bookTag);
	        } else {
	            BookTag bookTag = bookTagRepository.findOneByIdBookAndIdUserAndIdTag(idBook, idUser, tag.getIdTag());
	            if (bookTag == null) {
	                bookTag = new BookTag();
	                bookTag.setIdBook(idBook);
	                bookTag.setIdUser(idUser);
	                bookTag.setTag(tag);
	                bookTagRepository.save(bookTag);
	            }
	        }

	    }
	}

	@Override
	public List<Book> findLast100View(Long idUser) {
		Pageable top100 = new PageRequest(0, 100);
		Page<Book> books = bookRepository.findLastView(idUser, top100);
		return books.getContent();
	}

	@Override
	public List<Book> findTop100News() {
		Pageable top100 = new PageRequest(0, 100);
		Page<Book> books = bookRepository.findNews(top100);
		return books.getContent();
	}

	@Override
	public List<Book> findTop100Deprecated(Long idUser) {
		Pageable top100 = new PageRequest(0, 100);
		Page<Book> books = bookRepository.findDeprecated(idUser, top100);
		return books.getContent();
	}

	@Override
	public List<Book> findBestTop100(Long idUser) {
		Pageable top100 = new PageRequest(0, 100);
		Page<Book> books = bookRepository.findBest(idUser, top100);
		return books.getContent();
	}

    @Override
    public void assignTag(Long idTag, Long idUser, String idsBooks) {
        String[] ids = idsBooks.split(",");
        Tag tag = new Tag();
        tag.setIdTag(idTag);
        for (String idBookStr : ids) {
            Long idBook = Long.valueOf(idBookStr);
            BookTag bt = bookTagRepository.findOneByIdBookAndIdUserAndIdTag(idBook, idUser, idTag);
            if (bt == null) {
                bt = new BookTag(idBook, idUser, tag);
                bookTagRepository.save(bt);
            }
        }        
    }

    @Override
    public List<Book> findByTagName(String tagName, Long idUser) {
        return bookRepository.findByTagName(tagName, idUser);
    }

    @Override
    public void removeBookTag(Long idBookTag) {
        Tag tag = null;
        BookTag bookTag = bookTagRepository.findOne(idBookTag);
        if (bookTag != null) {
            tag = bookTag.getTag();
            bookTagRepository.delete(bookTag);
            int total = bookTagRepository.countByIdTag(tag.getIdTag());
            if (total == 0) {
                tagRepository.delete(tag);
            }
        }        
    }

}
