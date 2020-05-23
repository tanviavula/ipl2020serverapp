package com.nubes.ipl2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nubes.ipl2020.admin.service.AdminService;
import com.nubes.ipl2020.domain.Team;

@RestController
@RequestMapping("/ipl2020/admin/")
public class TeamController {

	@Autowired
	private AdminService adminService;

	@PostMapping("addteam")
	public Team addTeam(@RequestBody Team team) {
		return adminService.addNewTeam(team);
	}

	// CRUD team
	// CURD player

}
