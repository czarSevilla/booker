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
@Table(name = "book_access")
public class BookAccess implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idBookAccess;
	private Long idBook;
	private Long idUser;
	private Date access;
	
	public BookAccess() {
		
	}
	
	public BookAccess(Long pIdBook, Long pIdUser, Date pAccess) {
		this.idBook = pIdBook;
		this.idUser = pIdUser;
		this.access = pAccess;
	}

	@Id
	@Column(name = "id_book_access")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdBookAccess() {
		return idBookAccess;
	}

	public void setIdBookAccess(Long idBookAccess) {
		this.idBookAccess = idBookAccess;
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

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAccess() {
		return access;
	}

	public void setAccess(Date access) {
		this.access = access;
	}
}
