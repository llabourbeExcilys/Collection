package com.excilys.loic.collection.dao;

import java.util.Optional;

import com.excilys.loic.collection.model.Role;
import com.excilys.loic.collection.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}