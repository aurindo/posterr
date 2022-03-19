package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
