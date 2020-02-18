package com.nubes.ipl2020.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nubes.ipl2020.domain.Team;

public interface TeamRepository extends MongoRepository<Team,String> {

		public Team findByCity(String city);
		public Team findByLabel(String teamLabel);
}
