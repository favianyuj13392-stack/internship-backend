package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;

public interface UsuariosInstitucionesDao extends JpaRepository<Usuariosinstituciones, Integer>{
    @Query("SELECT COUNT(u) > 0 FROM Usuariosinstituciones u WHERE u.usuariosIdusuarios.kcUuid = ?1 AND u.institucionesIdinstituciones.idinstituciones = ?2 and u.usuariosIdusuarios.activo = true and u.activo = true")
    Boolean existsByUsuariosUuidAndInstitucionesIdinstituciones(String uuid, Integer idInstitucion);

    @Query("SELECT COUNT(u) > 0 FROM Usuariosinstituciones u WHERE u.usuariosIdusuarios.kcUuid = ?1 and u.usuariosIdusuarios.activo = true")
    Boolean existsByUsuariosUuid(String uuid);
}
