package uib.info323.twitterAWSM;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import uib.info323.twitterAWSM.io.impl.MySQLUserFactory;

public class Blackhatter {
	
	@Autowired
	private MySQLUserFactory mySqlUserFactory;
	@Autowired
	private RestTemplate restTemplate;
	
	public Blackhatter() {
		
	
	}
	
	public static void main(String[] args) {
		new Blackhatter();
		
	}
	
	
	

}
