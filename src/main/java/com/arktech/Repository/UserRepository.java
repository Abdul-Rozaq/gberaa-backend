package com.arktech.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arktech.entity.User;
import com.arktech.util.AppUserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("UPDATE User a SET a.enabled = TRUE WHERE a.email = ?1")
	int enableAppUser(String email);
	
	List<User> findByRole(AppUserRole role);
}
