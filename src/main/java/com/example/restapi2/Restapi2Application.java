package com.example.restapi2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.restapi2.models.User;
import com.example.restapi2.services.UserService;

@SpringBootApplication
public class Restapi2Application implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Restapi2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		userService.saveUser(new User(null, "Laurent", "GINA", "laurentgina@mail.com", "laurent"));
		userService.saveUser(new User(null, "Sophie", "FONCEK", "sophiefoncek@mail.com", "sophie"));
		userService.saveUser(new User(null, "Agathe", "FEELING", "agathefeeling@mail.com", "agathe"));
	}

}
