package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;

public interface InstitucionesDao extends JpaRepository<Instituciones, Integer>{
    @Query("SELECT i FROM Instituciones i WHERE i.activo = ?1")
    Page<Instituciones> findAllByActivo(Boolean activo, Pageable pageable);
    @Query("SELECT i FROM Instituciones i WHERE i.idinstituciones = ?1 AND i.activo = ?2")
    Instituciones findByIdinstitucionesAndActivo(Integer idinstituciones, Boolean activo);
}
