package com.party.party_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.party.party_management.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
