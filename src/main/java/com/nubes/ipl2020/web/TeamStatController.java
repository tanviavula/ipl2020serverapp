package com.nubes.ipl2020.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nubes.ipl2020.dto.MaxAmountPlayerByRoleDTO;
import com.nubes.ipl2020.dto.PlayerDTO;
import com.nubes.ipl2020.dto.RoleAmountDTO;
import com.nubes.ipl2020.dto.RoleCountDTO;
import com.nubes.ipl2020.dto.TeamAmountDTO;
import com.nubes.ipl2020.dto.TeamDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;
import com.nubes.ipl2020.service.TeamStatService;

@RestController
@RequestMapping("/ipl2020/team/")
public class TeamStatController {

	@Autowired
	private TeamStatService teamStatService;

	/*
	 * Retruns the Team labels
	 */
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

	@GetMapping("role/{teamLabel}")
	public List<RoleCountDTO> roleCountByTeam(@PathVariable("teamLabel") String teamLabel) {
		try {
			List<RoleCountDTO> roleCountList = teamStatService.getRoleCountByTeam(teamLabel);
			return roleCountList;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team label can't be empty", e);
		}
	}

	@GetMapping("{team}/{role}")
	public List<PlayerDTO> playersByTeamAndRole(@PathVariable("team")String teamLabel, @PathVariable("role") String role) {
		try {
			List<PlayerDTO> playersByRole = teamStatService.getPlayersByTeamAndRole(teamLabel, role);
			return playersByRole;
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team label can't be empty", e);
		}
	}

	@GetMapping("all")
	public List<TeamDTO> teamDetails() {
		List<TeamDTO> teamList = teamStatService.getTeamDetails();
		return teamList;
	}

	@GetMapping("/totalamount")
	public List<TeamAmountDTO> totalAmountByTeam() {
		List<TeamAmountDTO> teamAmount = teamStatService.getTotalAmountByTeam();
		return teamAmount;
	}

	@GetMapping("/maxamoutbyrole")
	public List<MaxAmountPlayerByRoleDTO> maxAountPlayerByRole() {
		List<MaxAmountPlayerByRoleDTO> maxAmountByRole = teamStatService.getMaxAountPlayerByRole();
		return maxAmountByRole;
	}

	@GetMapping("players/all")
	public List<PlayerDTO> allPlayers() {
		List<PlayerDTO> players = teamStatService.getAllPlayers();
		return players;
	}

	@GetMapping("players/search/{name}")
	public List<PlayerDTO> search(@PathVariable("name") String name) {
		List<PlayerDTO> players = teamStatService.search(name);
		return players;
	}
	
	@GetMapping("amountbyrole/{teamlabel}")
	public List<RoleAmountDTO> roleAmountByTeam(@PathVariable("teamlabel") String teamLabel) {
		List<RoleAmountDTO> roleAmount = teamStatService.getRoleAmountTeam(teamLabel);
		return roleAmount;
	}

}
