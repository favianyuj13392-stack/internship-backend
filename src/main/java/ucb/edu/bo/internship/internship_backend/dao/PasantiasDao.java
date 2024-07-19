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
    Page<Pasantias> findAllByActivoIsTrueAndTituloContainingIgnoreCase(
            String titulo,
            Pageable pageable
    );

    @Query(value = """
            SELECT DISTINCT p.*\s
            FROM pasantias p
            JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
            WHERE p.activo = true\s
            AND p.fechacierre > :fechacierre\s
            AND pc.carreras_idcarreras = :idCarrera
            """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndCarreras(
            Date fechacierre,
            Integer idCarrera,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        WHERE p.activo = true
          AND p.fechacierre > :fechacierre
          AND EXISTS (
              SELECT 1
              FROM jsonb_array_elements_text(p.areas) AS area
              WHERE area IN (:areas)
          )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndAreas(
            Date fechacierre,
            @Param("areas") List<String> areas,
            Pageable pageable
    );


    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
        WHERE p.activo = true
          AND p.fechacierre > :fechacierre
          AND pc.carreras_idcarreras = :idCarrera
          AND EXISTS (
              SELECT 1
              FROM jsonb_array_elements_text(p.areas) AS area
              WHERE area IN (:areas)
          )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndAreasAndCarreras(
            Date fechacierre,
            @Param("areas") List<String> areas,
            Integer idCarrera,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
        WHERE p.activo = true
            AND p.fechacierre > :fechacierre
            AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND pc.carreras_idcarreras = :idCarrera
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloAndCarreras(
            Date fechacierre,
            String titulo,
            Integer idCarrera,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        WHERE p.activo = true
            AND p.fechacierre > :fechacierre
            AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND EXISTS (
                SELECT 1
                FROM jsonb_array_elements_text(p.areas) AS area
                WHERE area IN (:areas)
            )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreas(
            Date fechacierre,
            String titulo,
            @Param("areas") List<String> areas,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT p.*
        FROM pasantias p
        JOIN pasantiascarreras pc ON p.idpasantias = pc.pasantias_idpasantias
        WHERE p.activo = true
            AND p.fechacierre > :fechacierre
            AND LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND pc.carreras_idcarreras = :idCarrera
            AND EXISTS (
                SELECT 1
                FROM jsonb_array_elements_text(p.areas) AS area
                WHERE area IN (:areas)
            )
        """, nativeQuery = true)
    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfterAndTituloAndAreasAndCarreras(
            Date fechacierre,
            String titulo,
            @Param("areas") List<String> areas,
            Integer idCarrera,
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


//obtener las pasantias que conecten con el usuario institucion y el campo uuid de la tabla usuario hazlo query nativo
@Query(value = """
    SELECT
	pasantias.*
FROM
	pasantias
	INNER JOIN
	usuarios
	ON 
		pasantias.usuarios_idusuarios = usuarios.idusuarios
WHERE
	usuarios.kc_uuid = :uuid
    ORDER BY pasantias.idpasantias DESC
""", nativeQuery = true)
List<Pasantias> findPasantiasByInstitucionesIdinstitucionesUsuarioUUID(String uuid);

}
