package com.nubes.ipl2020.service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.nubes.ipl2020.dao.TeamStatDao;
import com.nubes.ipl2020.dto.MaxAmountPlayerByRoleDTO;
import com.nubes.ipl2020.dto.PlayerDTO;
import com.nubes.ipl2020.dto.RoleCountDTO;
import com.nubes.ipl2020.dto.TeamAmountDTO;
import com.nubes.ipl2020.dto.TeamDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;
import com.nubes.ipl2020.repo.TeamRepository;
import com.nubes.ipl2020.service.exception.TeamNotFoundException;

@Service
public class TeamStatServiceImpl implements TeamStatService {

	private TeamStatDao teamStatDao;
	private TeamRepository teamRepository;

	private static final Logger LOG = LoggerFactory.getLogger(TeamStatServiceImpl.class);

	@Autowired
	public TeamStatServiceImpl(TeamStatDao teamStatDao, TeamRepository teamRepository) {
		this.teamStatDao = teamStatDao;
		this.teamRepository = teamRepository;
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
		if (teamRepository.findByLabel(teamLabel) == null) {
			throw new TeamNotFoundException(String.format("Team with given label %s is not found", teamLabel));
		}
		List<PlayerDTO> players = teamStatDao.getPlayersByTeam(teamLabel);
		LOG.info("Total players found for {} is : {}", teamLabel, players.size());
		return players;
	}

	@Override
	public List<RoleCountDTO> getRoleCountByTeam(String teamLabel) {
		Assert.notNull(teamLabel, "Team label can't be null or empty");
		if (teamRepository.findByLabel(teamLabel) == null) {
			throw new TeamNotFoundException(String.format("Team with given label %s is not found", teamLabel));
		}
		List<RoleCountDTO> roleCountList = teamStatDao.getRoleCountByTeam(teamLabel);
		LOG.info("Total Roles found for {} is : {}", teamLabel, roleCountList.size());
		return roleCountList;
	}

	@Override
	public List<PlayerDTO> getPlayersByTeamAndRole(String teamLabel, String role) {
		Assert.notNull(teamLabel, "Team label can't be null or empty");
		Assert.notNull(role, "Role can't be null or empty");
		if (teamRepository.findByLabel(teamLabel) == null) {
			throw new TeamNotFoundException(String.format("Team with given label %s is not found", teamLabel));
		}
		List<PlayerDTO> players = teamStatDao.getPlayersByTeamAndRole(teamLabel, role);
		LOG.info("Total players for Team :{}  and team: {} is {}", teamLabel, role, players.size());
		return players;
	}

	@Override
	public List<TeamDTO> getTeamDetails() {
		List<TeamDTO> teams = teamStatDao.getTeamDetails();
		LOG.info("Total team size:{}", teams.size());
		return teams;
	}

	@Override
	public List<TeamAmountDTO> getTotalAmountByTeam() {
		List<TeamAmountDTO> teamsAmountList = teamStatDao.getTotalAmountByTeam();
		LOG.info("Total team size:{}", teamsAmountList.size());
		return teamsAmountList;

	}

	@Override
	public List<MaxAmountPlayerByRoleDTO> getMaxAountPlayerByRole() {
		List<MaxAmountPlayerByRoleDTO> amountByRoleList = teamStatDao.getMaxAountPlayerByRole();
		LOG.info("Max amount by role size:{}", amountByRoleList.size());
		return amountByRoleList;

	}

	@Override
	public List<PlayerDTO> getAllPlayers() {
		List<PlayerDTO> players = teamStatDao.getAllPlayers();
		LOG.info("Players count:{}", players.size());
		return players;

	}

	@Override
	public List<PlayerDTO> search(String name) {
		List<PlayerDTO> players = teamStatDao.search(name);
		LOG.info("Players count:{} for search string:{}", players.size(), name);
		return players;

	}

}
