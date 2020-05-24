package com.nubes.ipl2020.dao.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.nubes.ipl2020.domain.Team;

@SpringBootTest
class QuickAPITest {

	@Autowired
	private MongoOperations mongoOperations;

	@Test
	void test() {
		Query query = new Query();
		Criteria findPlayerCriteria = Criteria.where("players")
				.elemMatch(Criteria.where("_id").is("5ec93a2bbfbcb52a512c4509"));
		query.addCriteria(findPlayerCriteria);

		Team team = mongoOperations.findOne(query, Team.class);

		//System.out.println(team.getHome());

		System.out.println(team.toString());
	}

}
