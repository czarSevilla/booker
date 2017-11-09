package czar.booker.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idAuthority;
	private String authority;
	private User user;
	private String createdBy;
	private String modifiedBy;
	private Date creation;
	private Date modification;
	
	public Authority() {
		
	}
	
	public Authority(String pAuthority, String pCreatedBy, Date pCreation) {
		this.authority = pAuthority;
		this.createdBy = pCreatedBy;
		this.creation = pCreation;
	}
	

	@Override
	@Column
	public String getAuthority() {
		return authority;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_authority")
	public Long getIdAuthority() {
		return idAuthority;
	}



	public void setIdAuthority(Long idAuthority) {
		this.idAuthority = idAuthority;
	}


	@Column(name = "created_by", nullable = false)
	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	@Column(name = "modified_by")
	public String getModifiedBy() {
		return modifiedBy;
	}



	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreation() {
		return creation;
	}



	public void setCreation(Date creation) {
		this.creation = creation;
	}


	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModification() {
		return modification;
	}



	public void setModification(Date modification) {
		this.modification = modification;
	}



	public void setAuthority(String authority) {
		this.authority = authority;
	}


	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable=false)
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

}
