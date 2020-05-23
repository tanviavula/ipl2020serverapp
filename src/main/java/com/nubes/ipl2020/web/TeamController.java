package com.nubes.ipl2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nubes.ipl2020.admin.service.AdminService;
import com.nubes.ipl2020.domain.Player;
import com.nubes.ipl2020.domain.Team;

@RestController
@RequestMapping("test/ipl2020/admin/")
public class TeamController {

	@Autowired
	private AdminService adminService;

	@PostMapping("addteam")
	public Team addTeam(@RequestBody Team team) {
		return adminService.addNewTeam(team);
	}

	@PutMapping("updateteam")
	public Team updateTeam(@RequestBody Team team) {
		return adminService.updateTeam(team);
	}

	@PostMapping("addPlayer/{teamid}")
	public Player addPlayerToTeam(@RequestBody Player player, @PathVariable String teamid) {
		return adminService.addPlayerToTeam(player, teamid);
	}

	@DeleteMapping("deleteteam/{teamid}")
	public boolean deleteTeam(@PathVariable String teamid) {
		return adminService.deleteTeam(teamid);
	}

	// CRUD team
	// CURD player

}
