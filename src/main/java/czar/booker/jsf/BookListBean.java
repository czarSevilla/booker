package czar.booker.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import czar.booker.dtos.TagCloudItemDto;
import czar.booker.entities.Book;
import czar.booker.entities.Tag;
import czar.booker.entities.User;
import czar.booker.enums.ListBookMenuItem;
import czar.booker.service.BookService;
import czar.booker.service.TagService;

@ManagedBean(name = "bookListBean")
@ViewScoped
public class BookListBean implements Serializable {
	
	private Logger logger = LoggerFactory.getLogger(BookListBean.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Book> books;
	
	private ListBookMenuItem menuItem;
	
	private Long idUser;
	
	private String searchValue;
	
	private String idsSelected;
	
	private boolean selectedGrid;
	
	private Long idTag;
	
	private List<Tag> tags;
	
	private TagCloudModel tagCloudModel;
	
	@ManagedProperty("#{bookService}")
	private BookService bookService;
	
	@ManagedProperty("#{tagService}")
	private TagService tagService;
	
	@PostConstruct
	public void init() {
		
		User user = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() instanceof User) {
			user = (User) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		} else if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() instanceof UsernamePasswordAuthenticationToken) {
			user = (User) ((UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal()).getPrincipal();
		}
		
		this.idUser = user.getIdUser();
		
		books = bookService.findLast100View(idUser);
		menuItem = ListBookMenuItem.LAST_VIEWS;
		tags = tagService.findAll();
		initTagCloud();
	}
	
	public void list(ListBookMenuItem itemSelected) {
		this.menuItem = itemSelected;
		loadBooks();
		this.searchValue = null;
	}
	
	public void loadBooks() {
		switch(menuItem) {
		case ALL:
			books = bookService.findAll().getContent();
			break;
		case DEPRECATED:
			books = bookService.findTop100Deprecated(idUser);
			break;
		case FAVORITES:
			books = bookService.findBestTop100(idUser);
			break;
		case LAST_VIEWS:
			books = bookService.findLast100View(idUser);
			break;
		case NEWS:
			books = bookService.findTop100News();
			break;
		}
	}
	
	public void search() {
		logger.info(String.format("search for: %s", this.searchValue));
		loadBooks();
		List<Book> criteriaBooks = books.stream()
								.filter(b -> b.getTitle().toLowerCase().contains(searchValue.toLowerCase()))
								.collect(Collectors.toList());
		
		this.books = criteriaBooks;
	}
	
	public void asignTag() {
		logger.info(String.format("idTag: %d, selectedGrid: %s, idsSelected: %s", idTag, selectedGrid, idsSelected));
		if (selectedGrid) {
		    idsSelected = books.stream().map(b -> b.getIdBook().toString()).collect(Collectors.joining(","));
		}
		bookService.assignTag(idTag, idUser, idsSelected);
		initTagCloud();
	}
	
	private void initTagCloud() {
	    tagCloudModel = new DefaultTagCloudModel();
        List<TagCloudItemDto> tagCloudItems = tagService.listTagCloud(idUser);
        for (TagCloudItemDto dto : tagCloudItems) {
            tagCloudModel.addTag(dto);
        }
	}
	
	public void onSelectTag(SelectEvent event) {
	    TagCloudItem item = (TagCloudItem) event.getObject();
	    books = bookService.findByTagName(item.getLabel(), idUser);
	    menuItem = null;
	    RequestContext context = RequestContext.getCurrentInstance();
	    context.execute(String.format("CJ.selectTag('%s');", item.getLabel()));
	}
	
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public ListBookMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(ListBookMenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getIdsSelected() {
		return idsSelected;
	}

	public void setIdsSelected(String idsSelected) {
		this.idsSelected = idsSelected;
	}

	public boolean isSelectedGrid() {
		return selectedGrid;
	}

	public void setSelectedGrid(boolean selectedGrid) {
		this.selectedGrid = selectedGrid;
	}

	public Long getIdTag() {
		return idTag;
	}

	public void setIdTag(Long idTag) {
		this.idTag = idTag;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

    public TagCloudModel getTagCloudModel() {
        return tagCloudModel;
    }

    public void setTagCloudModel(TagCloudModel tagCloudModel) {
        this.tagCloudModel = tagCloudModel;
    }
}
