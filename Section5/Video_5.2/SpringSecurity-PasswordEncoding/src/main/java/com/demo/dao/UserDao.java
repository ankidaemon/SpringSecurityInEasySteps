package com.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.demo.to.UserTo;

/**
 * @author ankidaemon
 *
 */
@Repository
@Component
public class UserDao {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	DriverManagerDataSource ds;
	 
	public String save(UserTo userto){
	    UserTo user = new UserTo();
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
		String authQuery = "insert into user_roles  (username,role) values('"+user.getUsername()+"','ROLE_USER')";
		try {
			con=ds.getConnection();
			Statement s=con.createStatement();
			s.executeUpdate(authQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed";
		}
		return "Successful";
	}
	
}
