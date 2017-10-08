package czar.booker.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import czar.booker.entities.BookNote;
import czar.booker.service.BookService;

@ManagedBean(name = "handleNoteBean")
@ViewScoped
public class HandleNoteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookNote bookNote;
	
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	@PostConstruct
	public void init() {
		String idBookNoteParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idBookNote");		
		
		if (idBookNoteParam != null && idBookNoteParam.matches("\\d+")) {
			this.bookNote = bookService.findOneBookNoteById(Long.valueOf(idBookNoteParam));
		} else {
			this.bookNote = new BookNote();
		}
	}
	
	public HandleNoteBean() {
		
	}
	
	public void handleNote() {
		RequestContext.getCurrentInstance().closeDialog(this.bookNote);
	}

	public BookNote getBookNote() {
		return bookNote;
	}

	public void setBookNote(BookNote bookNote) {
		this.bookNote = bookNote;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}
