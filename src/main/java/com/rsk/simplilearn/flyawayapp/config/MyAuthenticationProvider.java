package com.rsk.simplilearn.flyawayapp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rsk.simplilearn.flyawayapp.entities.Authority;
import com.rsk.simplilearn.flyawayapp.entities.Customer;
import com.rsk.simplilearn.flyawayapp.repositories.CustomerRepository;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		List<Customer> customer = this.customerRepository.findByEmail(username);

		if (this.passwordEncoder.matches(pwd, customer.get(0).getPwd())) { 
			return new UsernamePasswordAuthenticationToken(username, pwd,
					getGrantedAuthorities(customer.get(0).getAuthorities()));
		} else {
			throw new BadCredentialsException("OOPS..!! Please Enter Valid Credentials Boss..!!");
		}

	}

	private List<GrantedAuthority> getGrantedAuthorities(List<Authority> authorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Authority authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}
		return grantedAuthorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
