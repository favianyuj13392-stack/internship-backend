package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Curriculums;

public interface CurriculumsDao extends JpaRepository<Curriculums, Integer>{
    Curriculums findByTitulo(String titulo);
}
