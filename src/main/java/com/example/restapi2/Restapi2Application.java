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

		userService.saveUser(new User("Laurent", "GINA", "laurentgina@mail.com",
				/* passwordEncoder.encode("laurent") */"laurent"));
		userService.saveUser(new User("Sophie", "FONCEK", "sophiefoncek@mail.com",
				/* passwordEncoder.encode("sophie") */"sophie"));
		userService.saveUser(new User("Agathe", "FEELING", "agathefeeling@mail.com",
				/* passwordEncoder.encode("agathe") */"agathe"));
	}

}
