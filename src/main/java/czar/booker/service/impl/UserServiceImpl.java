package czar.booker.service.impl;

import java.util.Date;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import czar.booker.entities.Authority;
import czar.booker.entities.User;
import czar.booker.repository.UserRepository;
import czar.booker.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void saveUser(User user, Set<String> authorities) {
		Date now = new Date();
		String createdBy = "booker-webapp";
		String encodePass = passwordEncoder.encode(user.getPassword());
		user.setEnabled(true);
		user.setPassword(encodePass);
		user.setCreatedBy(createdBy);
		user.setCreation(now);
		if (authorities != null && authorities.size() > 0) {
			for (String auth : authorities) {
				user.addAuthority(new Authority(auth, createdBy, now));
			}
		}		
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

}
