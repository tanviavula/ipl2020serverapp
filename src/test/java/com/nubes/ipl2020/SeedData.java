package com.nubes.ipl2020;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubes.ipl2020.domain.Team;

@Component
public class SeedData {

	public List<Team> loadDataFromFile() throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("ipl2020.json").getFile());
		ObjectMapper objectMapper = new ObjectMapper();
		Team[] retObject = objectMapper.readValue(new FileInputStream(file.getPath()), Team[].class);
		List<Team> teams = new ArrayList<Team>();
		for (Team t : retObject) {
			teams.add(t);
		}
		return teams;
	}

	public List<String> getRoleNames() {
		List<String> roleList = Arrays.asList("Batsman,Bowler,All-Rounder,Wicket Keeper".split(","));
		return roleList;
	}
}
