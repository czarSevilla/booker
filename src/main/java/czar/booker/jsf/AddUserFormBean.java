package czar.booker.jsf;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import czar.booker.entities.User;
import czar.booker.service.UserService;

@ManagedBean(name = "addUserForm")
@ViewScoped
public class AddUserFormBean implements Serializable {
	
	Log logger = LogFactory.getLog(AddUserFormBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Set<String> authorities;
	
	@ManagedProperty("#{userService}")
	private UserService userService;
		
	public AddUserFormBean() {
		authorities = new HashSet<>();
	}
	
	public void saveUser() {
		logger.info("saveUser()");
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		userService.saveUser(u, authorities);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
}
