package com.demo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.demo.to.UserTo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * @author ankidaemon
 *
 * This is to insert dummy users in collection=UserTo of DB=local of MongoDB
 */
public class InsertIntoMongo {

	public static void main(String s[]){
		final String host="127.0.0.1";
		final int port=27017;
		final String database="local";
		ServerAddress server=new ServerAddress(host,port);
		MongoClient mongo=new MongoClient(server);
		MongoTemplate mongoTem=new MongoTemplate(mongo,database);
		MongoOperations mongoOperation = (MongoOperations)mongoTem;
		mongoOperation.dropCollection(UserTo.class);
		UserTo user = new UserTo("ankidaemon", "password", true);
		mongoOperation.save(user);
		UserTo user1 = new UserTo("test", "test", true);
		mongoOperation.save(user1);
		
		//Retrieve all users as a list
		List<UserTo> users = mongoOperation.findAll(UserTo.class);
		for(UserTo userVar:users){
			System.out.println(userVar.getUsername()+" "+userVar.getPassword());
		}
		
		//Find User based on query
		UserTo userVar=mongoOperation.findOne(
				 new Query(Criteria.where("username").is("ankidaemon")),
				 UserTo.class);
		System.out.println(userVar.getUsername()+" "+userVar.getPassword());
	}
	
}
