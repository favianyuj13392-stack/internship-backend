package ucb.edu.bo.internship.internship_backend.dao;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucb.edu.bo.internship.internship_backend.dto.PasantiaNombreDto;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.util.Date;
import java.util.List;

public interface PasantiasDao extends JpaRepository<Pasantias, Integer>{

    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfter(
            Date fechacierre,
            Pageable pageable
    );

    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
            Date fechacierre,
            String titulo,
            Pageable pageable
    );

    @Query(value = """
    WITH pasantia_carreras AS (
        SELECT carreras_idcarreras
        FROM pasantiascarreras
        WHERE pasantias_idpasantias = :pasantiaId
    ),
    pasantias_comunes AS (
        SELECT pc.pasantias_idpasantias
        FROM pasantiascarreras pc
        JOIN pasantia_carreras pc_target ON pc.carreras_idcarreras = pc_target.carreras_idcarreras
        WHERE pc.pasantias_idpasantias <> :pasantiaId
        GROUP BY pc.pasantias_idpasantias
    )
    SELECT p.*
    FROM pasantias p
    JOIN pasantias_comunes pc ON p.idpasantias = pc.pasantias_idpasantias
    WHERE p.activo = true
    ORDER BY (SELECT COUNT(*) FROM aplicacionespasantias ap WHERE ap.pasantias_idpasantias = p.idpasantias) DESC
    LIMIT 3;
""", nativeQuery = true)
    List<Pasantias> findTop3RelatedPasantias(
            @Param("pasantiaId") Integer pasantiaId
    );

    Pasantias findByIdpasantiasAndActivoIsTrue(Integer idpasantias);
    @Query("select count (*) from Pasantias p where p.activo = true")
    Long countAllByActivo();

    Page<Pasantias> findAllByInstitucionesIdinstitucionesAndTituloContainingIgnoreCase(
            Instituciones instituciones,
            String titulo,
            Pageable pageable
    );

    Page<Pasantias> findAllByInstitucionesIdinstituciones(
            Instituciones instituciones,
            Pageable pageable
    );







    //@Query("SELECT Pasantias FROM Pasantias p WHERE p.institucionesIdinstituciones.idinstituciones = ?1")
    List<Pasantias> findPasantiasByInstitucionesIdinstituciones(Instituciones idInstituciones);


    Page<Pasantias> findAllByActivoIsFalseAndTituloContainingIgnoreCase(String search, Pageable pageable);

    Page<Pasantias> findAllByTituloContainingIgnoreCase(String search, Pageable pageable);


    @Query("SELECT COUNT(p) FROM Pasantias p WHERE p.institucionesIdinstituciones.idinstituciones = ?1")
Long countAllByInstitucionesIdinstituciones(Integer idInstituciones);

}
