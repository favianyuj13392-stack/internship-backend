package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionConPasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionNombreDto;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;

import java.util.HashMap;
import java.util.List;

public interface InstitucionesDao extends JpaRepository<Instituciones, Integer>{
    @Query("SELECT i FROM Instituciones i WHERE i.activo = ?1")
    Page<Instituciones> findAllByActivo(Boolean activo, Pageable pageable);
    @Query("SELECT i FROM Instituciones i WHERE i.idinstituciones = ?1 AND i.activo = ?2")
    Instituciones findByIdinstitucionesAndActivo(Integer idinstituciones, Boolean activo);
    @Query("SELECT i FROM Instituciones i WHERE i.nombre ILIKE CONCAT('%', ?1, '%') AND i.activo = ?2")
    Page<Instituciones> findAllByNombreContainingAndActivo(String search, Boolean activo, Pageable pageable);
    @Query("SELECT new ucb.edu.bo.internship.internship_backend.dto.InstitucionNombreDto(i.idinstituciones, i.nombre) FROM Instituciones i WHERE i.activo = ?1")
    List<InstitucionNombreDto> getAllIdAndNameByActivo(Boolean activo);
}
