package czar.booker.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import czar.booker.entities.Book;
import czar.booker.service.BookService;

@ManagedBean(name = "bookListByPublisherBean")
@ViewScoped
public class BookListByPublisherBean {
		
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	private List<Book> books;
	
	private String publisherName;
	
	public BookListByPublisherBean() {
		
	}
	
	@PostConstruct
	public void init() {
		String idPublisherParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPublisher");		
				
		if (idPublisherParam != null && idPublisherParam.matches("\\d+")) {
			this.books = bookService.findByIdPublisher(Long.valueOf(idPublisherParam));
		}
	}	

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
}
