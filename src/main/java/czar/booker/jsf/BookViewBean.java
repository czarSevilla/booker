package czar.booker.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import czar.booker.entities.Book;
import czar.booker.entities.BookNote;
import czar.booker.entities.BookTag;
import czar.booker.entities.User;
import czar.booker.service.BookService;
import czar.booker.service.TagService;

@ManagedBean(name = "bookViewBean")
@ViewScoped
public class BookViewBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private Long idBook;
	
	private Book book;
	
	private boolean deprecated;	
	
	private Long idUser;
	
	private Integer rating;
	
	private Logger logger = LoggerFactory.getLogger(BookViewBean.class);
	
	private List<BookNote> notes;
	
	private List<BookTag> tags;
	
	private String searchTag;
	

	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	@ManagedProperty("#{tagService}")
	private TagService tagService;
	
	public BookViewBean() {
		
	}
	
	@PostConstruct
	public void init() {
		String idBookParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idBook");		
		User user = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() instanceof User) {
			user = (User) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		} else if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() instanceof UsernamePasswordAuthenticationToken) {
			user = (User) ((UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal()).getPrincipal();
		}
		
		this.idUser = user.getIdUser();
		
		this.notes = new ArrayList<>();
		this.tags = new ArrayList<>();
		
		if (idBookParam != null && idBookParam.matches("\\d+")) {
			this.idBook = Long.valueOf(idBookParam);
			this.book = bookService.findById(idBook);
			
			if (book != null) {
				this.deprecated = bookService.isDeprecated(this.idBook, this.idUser);
				this.rating = bookService.findRatingByIdBookAndIdUser(idBook, idUser);
				bookService.saveBookAccess(idBook, idUser);
				this.notes = bookService.findNotesByIdBookAndIdUser(idBook, idUser);
				this.tags = bookService.findTagsByIdBookAndIdUser(idBook, idUser);
			}
		}
	}	
	
	public void toogleDeprecate() {
		this.deprecated = bookService.toogleDeprecate(this.idBook, this.idUser);
	}
	
	public void onRate() {
		bookService.asignRating(this.idBook, this.idUser, this.rating);
	}
	
	public void showAuthorDetail(Long idAuthor) {
		logger.info(String.format("showAuthorDetail(%d)", idAuthor));
		Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 640);
        options.put("height", 340);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        
        Map<String, List<String>> params = new HashMap<>();
        params.put("idAuthor", Arrays.asList(String.valueOf(idAuthor)));
         
        RequestContext.getCurrentInstance().openDialog("listBooksByAuthor", options, params);
	}
	
	public void showPublisherDetail(Long idPublisher) {
		logger.info(String.format("showPublisherDetail(%d)", idPublisher));
		Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 640);
        options.put("height", 340);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        
        Map<String, List<String>> params = new HashMap<>();
        params.put("idPublisher", Arrays.asList(String.valueOf(idPublisher)));
         
        RequestContext.getCurrentInstance().openDialog("listBooksByPublisher", options, params);
	}
	
	public void showAddNote() {
		logger.info(String.format("showAddNote()"));
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("width", 640);
        options.put("height", 400);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("handleNote", options, null);
	}
	
	public void handleNote(SelectEvent event) {
		BookNote note = (BookNote) event.getObject();
		if (note.getIdBookNote() != null) {
			note.setModification(new Date());
			note.setModifiedBy("booker");
		} else {
			note.setIdBook(idBook);
			note.setIdUser(idUser);
			note.setCreation(new Date());
			note.setCreatedBy("booker");
		}
		bookService.saveBookNote(note);
		
		this.notes = bookService.findNotesByIdBookAndIdUser(idBook, idUser);
	}
	
	public void showEditNote(Long idBookNote) {
		logger.info(String.format("showAddNote()"));
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("width", 640);
        options.put("height", 400);
        options.put("modal", true);
        
        Map<String, List<String>> params = new HashMap<>();
        params.put("idBookNote", Arrays.asList(String.valueOf(idBookNote)));
        
        RequestContext.getCurrentInstance().openDialog("handleNote", options, params);
	}
	
	public void removeBookTag(Long idBookTag) {
		logger.info(String.format("removeBookTag(%d)", idBookTag));
		bookService.removeBookTag(idBookTag);
		this.tags = bookService.findTagsByIdBookAndIdUser(idBook, idUser);
	}
	
	public void addBookTag() {
		logger.info(String.format("addBookTag(%s)", this.searchTag));
		bookService.addBookTag(idBook, idUser, searchTag);
		tags = bookService.findTagsByIdBookAndIdUser(idBook, idUser);
		this.searchTag = null;
	}
	
	public List<String> completeTag(String query) {
		return tagService.findTagNames(query);
	}
	
	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}	

	public Long getIdBook() {
		return idBook;
	}

	public void setIdBook(Long idBook) {
		this.idBook = idBook;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public List<BookNote> getNotes() {
		return notes;
	}

	public void setNotes(List<BookNote> notes) {
		this.notes = notes;
	}

	public List<BookTag> getTags() {
		return tags;
	}

	public void setTags(List<BookTag> tags) {
		this.tags = tags;
	}

	public String getSearchTag() {
		return searchTag;
	}

	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

}
