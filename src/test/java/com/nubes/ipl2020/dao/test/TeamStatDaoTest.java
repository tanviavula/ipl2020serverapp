package com.nubes.ipl2020.dao.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nubes.ipl2020.SeedData;
import com.nubes.ipl2020.dao.TeamStatDao;
import com.nubes.ipl2020.domain.Team;
import com.nubes.ipl2020.dto.RoleCountDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;
import com.nubes.ipl2020.repo.TeamRepository;

@SpringBootTest
public class TeamStatDaoTest {

	@Autowired
	private TeamStatDao teamStatDao;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private SeedData seedData;

	@BeforeEach
	public void init() throws IOException {
		List<Team> teams = seedData.loadDataFromFile();
		teamRepository.saveAll(teams);
	}

	@AfterEach
	public void destory() {
		 teamRepository.deleteAll();
	}

	@Test
	public void teamLabels() {
		List<Team> list = teamRepository.findAll();
		System.out.println("Size of teams:" + list.size());
		TeamLabelDTO lables = teamStatDao.getTeamLabels();
		assertEquals(2, lables.getLabels().size());
	}

	@Test
	public void roleCountWiseByTeam() {
		List<RoleCountDTO> result = teamStatDao.getRoleCountByTeam("MI");
	    List<String> roleList = seedData.getRoleNames();
		Assertions.assertThat(result).isNotEmpty().hasSize(4);
		List<String> roles = result.stream().map(t -> t.getRoleName()).collect(Collectors.toList());
		Assertions.assertThat(roles).hasSize(roleList.size()).containsAll(roleList);
	}

}
