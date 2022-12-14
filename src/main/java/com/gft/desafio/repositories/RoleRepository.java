package com.gft.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gft.desafio.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
	Role findByRole(String role);
	
}
