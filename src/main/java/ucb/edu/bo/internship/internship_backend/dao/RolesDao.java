package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Roles;

public interface RolesDao extends JpaRepository<Roles, Integer>{
    Roles findByRol(String role);
}
