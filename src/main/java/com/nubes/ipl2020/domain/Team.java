package com.nubes.ipl2020.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {

		@Id
	    private String id;
		private String city;
		private String coach;
		private String home;
		private String label;
		private String team;
		private List<Player> players;
}
