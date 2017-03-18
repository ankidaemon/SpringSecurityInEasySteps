package com.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.demo.to.UserTo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import org.springframework.security.core.userdetails.User;

/**
 * @author ankidaemon
 *
 */
public class CustomUserDetailsService implements UserDetailsService {

	public MongoTemplate mongoTemplate(){
		final String host="127.0.0.1";
		final int port=27017;
		final String database="local";
		ServerAddress server=new ServerAddress(host,port);
		MongoClient mongo=new MongoClient(server);
		MongoTemplate mongoTemplate=new MongoTemplate(mongo,database);
		return mongoTemplate;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserTo userTo = getUserToDetail(username);
		User userDetail = new User(
				userTo.getUsername(), userTo.getPassword(), userTo.isEnabled(), true, true, true, getAuthorities());
		return userDetail;
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authList;
	}

	public UserTo getUserToDetail(String username) {
		MongoOperations mongoOperation = (MongoOperations) mongoTemplate();
		UserTo userTo = mongoOperation.findOne(new Query(Criteria.where("username").is(username)), UserTo.class);
		return userTo;
	}

}
