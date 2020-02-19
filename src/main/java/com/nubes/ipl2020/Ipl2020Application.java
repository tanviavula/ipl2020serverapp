package com.nubes.ipl2020;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class Ipl2020Application {

	public static void main(String[] args) {
		final SpringApplication application = new SpringApplication(Ipl2020Application.class);
		application.run(args);
	}

}
