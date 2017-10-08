package czar.booker.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deprecated_book")
public class DeprecatedBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDeprecated;
	private Long idBook;
	private Long idUser;
	
	public DeprecatedBook() {
		
	}
	
	public DeprecatedBook(Long pIdBook, Long pIdUser) {
		this.idBook = pIdBook;
		this.idUser = pIdUser;
	}

	@Id
	@Column(name = "id_deprecated_book")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdDeprecated() {
		return idDeprecated;
	}

	public void setIdDeprecated(Long idDeprecated) {
		this.idDeprecated = idDeprecated;
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
}
