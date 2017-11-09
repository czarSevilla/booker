package czar.booker.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book_tag")
public class BookTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idBookTag;
	private Long idBook;
	private Long idUser;
	private Tag tag;
	
	public BookTag() {
		
	}
	
	public BookTag(Long pIdBook, Long pIdUser, Tag pTag) {
		this.idBook = pIdBook;
		this.idUser = pIdUser;
		this.tag = pTag;
	}

	@Id
	@Column(name = "id_book_tag")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdBookTag() {
		return idBookTag;
	}

	public void setIdBookTag(Long idBookTag) {
		this.idBookTag = idBookTag;
	}

	@Column(name = "id_book", nullable = false)
	public Long getIdBook() {
		return idBook;
	}

	public void setIdBook(Long idBook) {
		this.idBook = idBook;
	}

	@Column(name = "id_user", nullable = false)
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tag", nullable = false)
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
