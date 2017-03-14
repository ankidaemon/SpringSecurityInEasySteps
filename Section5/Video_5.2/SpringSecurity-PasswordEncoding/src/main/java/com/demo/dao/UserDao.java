package com.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.demo.to.User;

@Repository
@Component
@Scope("session")
public class UserDao {


	private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	@Autowired
	DriverManagerDataSource ds;
	 
	public String save(User userto){
	    User user = new User();
	    user.setUsername(userto.getUsername());
	    user.setPassword(passwordEncoder.encode(userto.getPassword()));
	    Connection con = null;
		PreparedStatement ps = null;
		boolean enabled=true;
		String sql = "insert into users (username,password,enabled) values(?,?,?)";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setBoolean(3,enabled);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "User already Exists! Please choose a different user name";
		} 
		return "Successful";
	}
	
}
