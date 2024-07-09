package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Curriculums;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.util.Collection;
import java.util.List;

public interface CurriculumsDao extends JpaRepository<Curriculums, Integer>{
    Curriculums findByTitulo(String titulo);
    List<Curriculums> findByUsuariosIdusuarios(Usuarios usuario);
}
