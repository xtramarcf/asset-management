package de.fortmeier.asset_management.iam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing user data from the database.
 */
@Repository
public interface IamRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

    @Query ("select u from User u")
    <T> List<T> findAll(Class<T> projection);
}
