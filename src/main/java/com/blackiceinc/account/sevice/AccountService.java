package com.blackiceinc.account.sevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blackiceinc.account.dao.AccountDao;
import com.blackiceinc.account.model.Account;

import javax.transaction.Transactional;


@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	private AccountDao accountDao;
	
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account domainUser = accountDao.findByUserName(username);
		
		System.out.println("\n\n\n\n\n\n");
        System.out.println(domainUser);
        System.out.println("\n\n\n\n\n\n");
        
		boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        int roleID = 4;
        if(domainUser.getRole() != null) {
        	roleID = domainUser.getRole().getIntRoleId();
        } 
        return new User(
                domainUser.getTxtUsername(), 
                domainUser.getTxtPassword(), 
                enabled, 
                accountNonExpired, 
                credentialsNonExpired, 
                accountNonLocked,
                getAuthorities(roleID)
        );
	}
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }
	
	public List<String> getRoles(Integer role) {
		 
        List<String> roles = new ArrayList<String>();
        
        System.out.println("\n\n\n\n\n\n");
        System.out.println("role value: " + role.intValue());
        System.out.println("\n\n\n\n\n\n");
        
        if (role.intValue() == 1) {
        	roles.add("ROLE_ADMIN");
            roles.add("ROLE_REGULATOR");
            roles.add("ROLE_USER");
            roles.add("ROLE_GUEST");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_REGULATOR");
            roles.add("ROLE_USER");
            roles.add("ROLE_GUEST");
        } else if (role.intValue() == 3) {
            roles.add("ROLE_USER");
            roles.add("ROLE_GUEST");
        } else if (role.intValue() == 4) {
            roles.add("ROLE_GUEST");
        } else {
        	roles.add("ROLE_GUEST");
        }
        return roles;
    }
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
