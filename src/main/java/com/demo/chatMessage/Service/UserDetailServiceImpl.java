package com.demo.chatMessage.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.chatMessage.Model.User;
import com.demo.chatMessage.Repository.UserRepo;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
     
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found "+username);
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				getAuthority(user)
				);
	}
    private List<GrantedAuthority> getAuthority(User user){
    	List<GrantedAuthority> list = new ArrayList<>();
    	list.add(new SimpleGrantedAuthority(user.getRole()));
    	return list;
    }
}
