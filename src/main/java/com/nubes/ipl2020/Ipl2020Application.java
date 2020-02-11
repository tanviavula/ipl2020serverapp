package com.nubes.ipl2020;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nubes.ipl2020.dao.TeamStatDao;
import com.nubes.ipl2020.dto.PlayerDTO;

@SpringBootApplication
public class Ipl2020Application {
	@Autowired
	private TeamStatDao teamStatDao;

	public static void main(String[] args) {
		SpringApplication.run(Ipl2020Application.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return (args) -> {
			 List<PlayerDTO> list = teamStatDao.search("ni");
			 System.out.println("Team information....");
			list.stream().forEach(e->{
					System.out.println(e.getRole());
					System.out.println(e.getName());
			});
		};
	}

}
