package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

public interface UsuariosDao extends JpaRepository<Usuarios, Integer>{
    
}
