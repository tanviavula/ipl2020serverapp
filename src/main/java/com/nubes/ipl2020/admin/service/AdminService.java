package com.nubes.ipl2020.admin.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.nubes.ipl2020.domain.Player;
import com.nubes.ipl2020.domain.Team;

public interface AdminService {

	Team addNewTeam(Team team);

	Team updateTeam(Team team);

	boolean deleteTeam(String teamid);

	Player addPlayerToTeam(Player player, String teamid);

	Player addPlayersToTeam(List<Player> players, String teamid);

	Player updatePlayer(Player player, ObjectId id);

	boolean deletePlayer(ObjectId id);

}
