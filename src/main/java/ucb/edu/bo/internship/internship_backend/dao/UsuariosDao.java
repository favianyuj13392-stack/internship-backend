package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

public interface UsuariosDao extends JpaRepository<Usuarios, Integer>{

    Usuarios findByKcUuid(String kcUuid);
    @Query("SELECT COUNT(u) from Usuarios u where u.activo = true and u.rolesIdroles.rol = 'ESTUDIANTE'")
    Long countAllByActivoAndRolesIdrolesRolEqualsESTUDIANTE();
}
