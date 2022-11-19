package com.sistema.blog.repository;

import com.sistema.blog.entity.Role;
import com.sistema.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findByName(String name);

}
