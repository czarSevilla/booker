package czar.booker.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "book_rating")
public class BookRating implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idBookRating;
	private Long idBook;
	private Long idUser;
	private Integer rating;
	private Date creation;
	private String createdBy;
	private Date modification;
	private String modifiedBy;
	
	public BookRating() {
		
	}
	
	public BookRating(Long pIdBook, Long pIdUser, Integer pRating, Date pCreation, String pCreatedBy) {
		this.idBook = pIdBook;
		this.idUser = pIdUser;
		this.rating = pRating;
		this.creation = pCreation;
		this.createdBy = pCreatedBy;
	}

	@Id
	@Column(name = "id_book_rating")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdBookRating() {
		return idBookRating;
	}

	public void setIdBookRating(Long idBookRating) {
		this.idBookRating = idBookRating;
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

	@Column(nullable = false)
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@Column(name = "created_by", nullable = false)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column
	public Date getModification() {
		return modification;
	}

	public void setModification(Date modification) {
		this.modification = modification;
	}

	@Column(name = "modified_by")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
