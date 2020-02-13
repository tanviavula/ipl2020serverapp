package com.nubes.ipl2020.service;

import java.util.List;

import com.nubes.ipl2020.dto.PlayerDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;

public interface TeamStatService {

	public TeamLabelDTO getTeamLabels();

	public List<PlayerDTO> getPlayersByTeam(String teamLabel);
}
