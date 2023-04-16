package com.roadpricing.user.Repo;

import com.roadpricing.user.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
