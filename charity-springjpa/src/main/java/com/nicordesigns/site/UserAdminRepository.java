package com.nicordesigns.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAdminRepository extends JpaRepository<UserAdmin, Integer> {
    @Query("SELECT u.password FROM UserAdmin u WHERE u.username = :username")
    String getPasswordForUser(@Param("username") String username);

    UserAdmin findByUsername(String username);
}