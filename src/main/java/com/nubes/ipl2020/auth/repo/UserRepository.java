package com.nubes.ipl2020.auth.repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.nubes.ipl2020.auth.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
}