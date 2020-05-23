package com.nubes.ipl2020.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nubes.ipl2020.domain.Team;
import com.nubes.ipl2020.repo.TeamRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	TeamRepository teamRepo;

	@Override
	public Team addNewTeam(Team team) {
		Assert.notNull(team, "TeamDTO cant be null");
		team = teamRepo.save(team);
		if (team != null) {
			log.info("Team added successfully into DB with ID {} ", team.getId());
		}else {
			log.info("Something went wrong while adding new Team with label {} ",team.getLabel());
		}

		return team;
	}

}
