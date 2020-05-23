package com.nubes.ipl2020.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nubes.ipl2020.auth.model.AppUserDetails;
import com.nubes.ipl2020.auth.model.Role;
import com.nubes.ipl2020.auth.model.User;
import com.nubes.ipl2020.auth.repo.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("User name Found!"));
		AppUserDetails appUserDetails = new AppUserDetails();
		appUserDetails.setUser(optionalUser.get());
		return appUserDetails;
	}

	public User findByUsername(String username) {
		
		Optional<User> userOpt= userRepo.findByUsername(username);
		
		return userOpt.isPresent()?userOpt.get():null;
	}

	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if(user.getRoles()==null || user.getRoles().size()==0) {
			user.getRoles().add(new Role("USER"));
		}
		return userRepo.save(user);
		
	}

}