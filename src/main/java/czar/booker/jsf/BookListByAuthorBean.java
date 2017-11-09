package czar.booker.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import czar.booker.entities.Book;
import czar.booker.service.BookService;

@ManagedBean(name = "bookListByAuthorBean")
@ViewScoped
public class BookListByAuthorBean {
		
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	private List<Book> books;
	
	private String authorName;
	
	public BookListByAuthorBean() {
		
	}
	
	@PostConstruct
	public void init() {
		String idAuthorParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idAuthor");		
				
		if (idAuthorParam != null && idAuthorParam.matches("\\d+")) {
			this.books = bookService.findByIdAuthor(Long.valueOf(idAuthorParam));
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}	
}
