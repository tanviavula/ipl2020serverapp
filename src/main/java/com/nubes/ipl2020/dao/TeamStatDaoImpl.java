package com.nubes.ipl2020.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.nubes.ipl2020.dto.MaxAmountPlayerByRoleDTO;
import com.nubes.ipl2020.dto.PlayerDTO;
import com.nubes.ipl2020.dto.RoleAmountDTO;
import com.nubes.ipl2020.dto.RoleCountDTO;
import com.nubes.ipl2020.dto.TeamAmountDTO;
import com.nubes.ipl2020.dto.TeamDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;

@Repository
public class TeamStatDaoImpl implements TeamStatDao {

	private static final Logger LOG = LoggerFactory.getLogger(TeamStatDaoImpl.class);
	private MongoOperations mongoOperations;

	@Autowired
	public TeamStatDaoImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	@Override
	public TeamLabelDTO getTeamLabels() {
		Aggregation aggr = newAggregation(group("null").addToSet("label").as("labels"), project().andExclude("_id"));

		AggregationResults<TeamLabelDTO> res = mongoOperations.aggregate(aggr, "team", TeamLabelDTO.class);
		TeamLabelDTO labels = res.getUniqueMappedResult();
		return labels;
	}

	@Override
	public List<PlayerDTO> getPlayersByTeam(String teamLabel) {
		Aggregation aggr = newAggregation(match(Criteria.where("label").is(teamLabel)), unwind("players"),
				project().andExclude("_id").and("label").as("label").and("players.player").as("name")
						.and("players.price").as("price").and("players.role").as("role"));

		AggregationResults<PlayerDTO> res = mongoOperations.aggregate(aggr, "team", PlayerDTO.class);
		List<PlayerDTO> playersDetails = res.getMappedResults();
		return playersDetails;
	}

	@Override
	public List<RoleCountDTO> getRoleCountByTeam(String teamLabel) {
		Aggregation aggr = newAggregation(match(Criteria.where("label").is(teamLabel)), unwind("players"),
				group("players.role").count().as("count"),
				project().and("_id").as("roleName").and("count").as("count").andExclude("_id")

		);
		AggregationResults<RoleCountDTO> res = mongoOperations.aggregate(aggr, "team", RoleCountDTO.class);
		List<RoleCountDTO> roleCount = res.getMappedResults();
		return roleCount;
	}

	@Override
	public List<PlayerDTO> getPlayersByTeamAndRole(String teamLabel, String role) {
		Aggregation aggr = newAggregation(match(Criteria.where("label").is(teamLabel)), unwind("players"),
				project().andExclude("_id").and("label").as("label").and("players.player").as("name")
						.and("players.price").as("price").and("players.role").as("role"),
				match(Criteria.where("role").is(role)));
		AggregationResults<PlayerDTO> res = mongoOperations.aggregate(aggr, "team", PlayerDTO.class);
		List<PlayerDTO> playersDetails = res.getMappedResults();
		return playersDetails;
	}

	@Override
	public List<TeamDTO> getTeamDetails() {
		Query query = new Query();
		query.fields().exclude("_id").exclude("players");
		List<TeamDTO> list = mongoOperations.find(query, TeamDTO.class, "team");
		return list;
	}

	@Override
	public List<TeamAmountDTO> getTotalAmountByTeam() {
		Aggregation aggr = newAggregation(unwind("players"), group("label").sum("players.price").as("amount"),
				project().and("_id").as("teamName").and("amount").as("amount"));

		AggregationResults<TeamAmountDTO> res = mongoOperations.aggregate(aggr, "team", TeamAmountDTO.class);
		List<TeamAmountDTO> totalAmount = res.getMappedResults();
		return totalAmount;
	}

	@Override
	public List<MaxAmountPlayerByRoleDTO> getMaxAountPlayerByRole() {

		Document obj = new Document();
		obj.put("name", "$players.player");
		obj.put("label", "$label");
		obj.put("price", "$players.price");
		obj.put("role", "$players.role");

		Aggregation aggr = newAggregation(unwind("players"),
				group("players.role").max("players.price").as("maxprice").push(obj).as("players"),

				project().and("_id").as("role").and("maxprice").as("amount").and("players").as("players")
						.and(new AggregationExpression() {
							@Override
							public Document toDocument(AggregationOperationContext aggregationOperationContext) {
								Document filterExpression = new Document();
								filterExpression.put("input", "$players");
								filterExpression.put("as", "p");
								filterExpression.put("cond",
										new Document("$eq", Arrays.<Object>asList("$$p.price", "$maxprice")));
								return new Document("$filter", filterExpression);
							}
						}).as("players"));
		System.out.println(aggr);
		AggregationResults<MaxAmountPlayerByRoleDTO> res = mongoOperations.aggregate(aggr, "team",
				MaxAmountPlayerByRoleDTO.class);
		List<MaxAmountPlayerByRoleDTO> totalAmount = res.getMappedResults();
		return totalAmount;
	}

	@Override
	public List<PlayerDTO> getAllPlayers() {
		Aggregation aggr = newAggregation(
				unwind("players"), project().and("label").as("label").and("players.player").as("name")
						.and("players.price").as("price").and("players.role").as("role").andExclude("_id"),
				sort(Direction.DESC, "price"));
		AggregationResults<PlayerDTO> res = mongoOperations.aggregate(aggr, "team", PlayerDTO.class);
		List<PlayerDTO> playersList = res.getMappedResults();
		LOG.info("Total players : {}", playersList.size());
		return playersList;

	}

	@Override
	public List<PlayerDTO> search(String name) {
		Aggregation aggr = newAggregation(unwind("players"),
				match(Criteria.where("players.player").regex(".*" + name + ".*", "i")),
				project().and("label").as("label").and("players.player").as("name").and("players.price").as("price")
						.and("players.role").as("role").andExclude("_id"),
				sort(Direction.DESC, "price"));
		AggregationResults<PlayerDTO> res = mongoOperations.aggregate(aggr, "team", PlayerDTO.class);
		List<PlayerDTO> playersList = res.getMappedResults();
		LOG.info("Search Mathched players count : {}", playersList.size());
		return playersList;
	}

	@Override
	public List<RoleAmountDTO> getRoleAmountTeam(String teamLabel) {
		Aggregation aggr = newAggregation(
				unwind("players"),
				match(Criteria.where("label").is(teamLabel)),
				group("players.role").sum("players.price").as("amount"),
				project().and("_id").as("roleName").and("amount").as("amount").andExclude("_id"));
		AggregationResults<RoleAmountDTO> res = mongoOperations.aggregate(aggr, "team", RoleAmountDTO.class);
		List<RoleAmountDTO> roleAmountList = res.getMappedResults();
		LOG.info("Search Mathched players count : {}", roleAmountList.size());
		return roleAmountList;
	}

}
