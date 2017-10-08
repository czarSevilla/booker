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
@Table(name = "filetype")
public class Filetype implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idFiletype;
	private String code;
	private Date creation;
	private String createdBy;
	private Date modification;
	private String modifiedBy;
	
	public Filetype() {
		
	}

	@Id
	@Column(name = "id_filetype")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdFiletype() {
		return idFiletype;
	}

	public void setIdFiletype(Long idFiletype) {
		this.idFiletype = idFiletype;
	}

	@Column(nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModification() {
		return modification;
	}

	public void setModification(Date modification) {
		this.modification = modification;
	}

	@Column(name = "modified_by", nullable = false)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
