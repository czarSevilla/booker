package czar.booker.service;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import czar.booker.entities.User;

public interface UserService extends UserDetailsService {
	void saveUser(User user, Set<String> authorities);
}
