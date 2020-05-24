package com.nubes.ipl2020.admin.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.result.UpdateResult;
import com.nubes.ipl2020.domain.Player;
import com.nubes.ipl2020.domain.Team;
import com.nubes.ipl2020.repo.TeamRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	TeamRepository teamRepo;

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public Team addNewTeam(Team team) {
		Assert.notNull(team, "Team object can't be null");
		team = teamRepo.save(team);
		if (team != null) {
			log.info("Team added successfully into DB with ID {} ", team.getId());
		} else {
			log.info("Something went wrong while adding new Team with label {} ", team.getLabel());
		}
		return team;
	}

	@Override
	public Team updateTeam(Team team) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(team.getId()));
		Update update = new Update();
		Team existingTeam = mongoOperations.findOne(query, Team.class);
		if (existingTeam != null) {

			update.set("city", team.getCity());
			update.set("coach", team.getCoach());
			update.set("home", team.getHome());
			update.set("label", team.getLabel());
			update.set("team", team.getTeam());

			UpdateResult res = mongoOperations.upsert(query, update, Team.class);
			query.fields().exclude("_id").exclude("players");

			if (res != null) {
				long count = res.getModifiedCount();
				log.info("Team details updated count {} ", count);
				return mongoOperations.findOne(query, Team.class);
			} else {
				log.info("Something went wrong while updating team");
				return null;
			}
		}
		log.info("No Team found with given ID {} ", team.getId());
		return null;

	}

	@Override
	public Player addPlayerToTeam(Player player, String teamid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(teamid));
		Update update = new Update();
		Team existingTeam = mongoOperations.findOne(query, Team.class);
		if (existingTeam != null) {
			player.setId(new ObjectId());
			update.push("players", player);
			UpdateResult res = mongoOperations.upsert(query, update, Team.class);
			if (res != null) {
				log.info("New Player added to team {} with player id {} ");
				return player;
			} else {
				log.info("Something went wrong while adding new player");
				return null;
			}
		}
		log.info("No Team found with given ID {} ", teamid);
		return null;
	}

	@Override
	public Player addPlayersToTeam(List<Player> players, String teamid) {
		return null;
	}

	@Override
	public Player updatePlayer(Player player) {

		return null;
	}

	@Override
	public boolean deleteTeam(String teamid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(teamid));
		Team team = mongoOperations.findAndRemove(query, Team.class);
		if (team != null) {
			log.info("Team deleted with id {} and {} ", team.getId(), team.getLabel());
			return true;
		}
		log.info("No Team found with given id {} ", teamid);
		return false;
	}

	@Override
	public boolean deletePlayer(ObjectId id) {
		return false;
	}

}
