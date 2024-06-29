package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionConPasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionNombreDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesConCOUNTPasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.RecuentoPaginaInicioDto;
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
    @Query(value = """
        SELECT i.*, COUNT(p.idpasantias) as pasantias_count
        FROM instituciones i
        JOIN pasantias p ON i.idinstituciones = p.instituciones_idinstituciones
        WHERE EXISTS (
            SELECT 1
            FROM jsonb_array_elements_text(i.sectores) as sector
            WHERE LOWER(sector) IN (
                SELECT LOWER(jsonb_array_elements_text(sectores)::text)
                FROM instituciones
                WHERE idinstituciones = ?1
            )
        )
        AND i.idinstituciones != ?1
        AND i.activo = true
        GROUP BY i.idinstituciones
        ORDER BY pasantias_count DESC
        LIMIT 4
    """, nativeQuery = true)
    List<Instituciones> findTop4InstitucionesBySectoresAndPasantias(Integer idInstitucion);
    @Query("SELECT new ucb.edu.bo.internship.internship_backend.dto.InstitucionesConCOUNTPasantiasDto(i.idinstituciones,i.nombre, COUNT(p.idpasantias)) FROM Instituciones i JOIN Pasantias p ON i.idinstituciones = p.institucionesIdinstituciones.idinstituciones WHERE i.activo = ?1 GROUP BY i.idinstituciones order by COUNT(p.idpasantias) DESC")
    Page<InstitucionesConCOUNTPasantiasDto> getAllNameAndCountPasantiasByActivo(boolean b, Pageable pageable);
    @Query("select count (*) from Instituciones i where i.activo = true")
    Long countAllByActivo();

    Boolean existsByIdinstitucionesAndActivoIsTrue(Integer idInstitucion);
}
