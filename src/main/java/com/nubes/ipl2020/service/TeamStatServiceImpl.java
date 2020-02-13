package com.nubes.ipl2020.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nubes.ipl2020.dao.TeamStatDao;
import com.nubes.ipl2020.dto.PlayerDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;

@Service
public class TeamStatServiceImpl implements TeamStatService {

	private TeamStatDao teamStatDao;
	private static final Logger LOG = LoggerFactory.getLogger(TeamStatServiceImpl.class);

	@Autowired
	public TeamStatServiceImpl(TeamStatDao teamStatDao) {
		this.teamStatDao = teamStatDao;
	}

	@Override
	public TeamLabelDTO getTeamLabels() {
		TeamLabelDTO teamLables = teamStatDao.getTeamLabels();
		LOG.info("Total labels found :{}", teamLables.getLabels().size());
		return teamLables;
	}

	@Override
	public List<PlayerDTO> getPlayersByTeam(String teamLabel) {
		Assert.notNull(teamLabel, "Team label can't be null or empty");
		List<PlayerDTO> players = teamStatDao.getPlayersByTeam(teamLabel);
		LOG.info("Total players found for {} is : {}", teamLabel, players.size());
		return players;
	}

}
