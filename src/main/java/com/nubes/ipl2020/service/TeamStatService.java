package com.nubes.ipl2020.service;

import java.util.List;

import com.nubes.ipl2020.dto.MaxAmountPlayerByRoleDTO;
import com.nubes.ipl2020.dto.PlayerDTO;
import com.nubes.ipl2020.dto.RoleCountDTO;
import com.nubes.ipl2020.dto.TeamAmountDTO;
import com.nubes.ipl2020.dto.TeamDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;

public interface TeamStatService {

	public TeamLabelDTO getTeamLabels();
	public List<PlayerDTO> getPlayersByTeam(String teamLabel);
	public List<RoleCountDTO> getRoleCountByTeam(String teamLabel);
	public List<PlayerDTO> getPlayersByTeamAndRole(String teamLabel,String role) ;
	public List<TeamDTO> getTeamDetails();
	public List<TeamAmountDTO> getTotalAmountByTeam();
	public List<MaxAmountPlayerByRoleDTO> getMaxAountPlayerByRole();
	public List<PlayerDTO> getAllPlayers();
	public List<PlayerDTO> search(String name);
}
