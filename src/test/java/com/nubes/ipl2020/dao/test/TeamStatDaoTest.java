package com.nubes.ipl2020.dao.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nubes.ipl2020.SeedData;
import com.nubes.ipl2020.dao.TeamStatDao;
import com.nubes.ipl2020.domain.Team;
import com.nubes.ipl2020.dto.RoleCountDTO;
import com.nubes.ipl2020.dto.TeamLabelDTO;
import com.nubes.ipl2020.repo.TeamRepository;
@ExtendWith(SpringExtension.class)
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
		TeamLabelDTO lables = teamStatDao.getTeamLabels();
		assertEquals(3, lables.getLabels().size());	
	}
	
	@Test
	public void roleCountWiseByTeam() {
		List<RoleCountDTO> result = teamStatDao.getRoleCountByTeam("MI");
		List<String> roleList = seedData.getRoleNames();
		assertThat(result).isNotEmpty().hasSize(4);
		List<String> roles = result.stream().map(t->t.getRoleName()).collect(Collectors.toList());
		System.out.println(roles);
		assertThat(roles).hasSize(roleList.size()).containsAll(roleList);
	}
}
