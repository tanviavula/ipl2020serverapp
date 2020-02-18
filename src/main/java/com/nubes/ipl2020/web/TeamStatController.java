package com.nubes.ipl2020.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nubes.ipl2020.dto.PlayerDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;
import com.nubes.ipl2020.service.TeamStatService;

@RestController
@RequestMapping("/ipl2020/team/")
public class TeamStatController {

	@Autowired
	private TeamStatService teamStatService;

	@GetMapping("labels")
	public TeamLabelDTO teamLabels() {
		return teamStatService.getTeamLabels();
	}
	@GetMapping("{teamLabel}")
	public List<PlayerDTO> getPlayersByTeam(@PathVariable("teamLabel") String teamLabel) {
		try {
			List<PlayerDTO> players = teamStatService.getPlayersByTeam(teamLabel);
			return players;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team label can't be empty", e);
		}
	}
}
