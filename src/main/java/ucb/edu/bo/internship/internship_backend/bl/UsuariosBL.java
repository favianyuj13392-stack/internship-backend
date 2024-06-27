package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.PersonasDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosInstitucionesDto;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Roles;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;
import ucb.edu.bo.internship.internship_backend.service.IKeycloakService;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuariosBL {

    private final Logger logger = LoggerFactory.getLogger(UsuariosBL.class);

    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final InstitucionesDao institucionesDao;
    private final RolesDao rolesDao;
    private final CarrerasDao carreasDao;
    private final IKeycloakService keycloakService;

    public UsuariosBL(UsuariosDao usuariosDao,
                      PersonasDao personasDao, UsuariosInstitucionesDao usuariosInstitucionesDao,
                      InstitucionesDao institucionesDao, RolesDao rolesDao, IKeycloakService keycloakService, CarrerasDao carreasDao) {
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.institucionesDao = institucionesDao;
        this.rolesDao = rolesDao;
        this.keycloakService = keycloakService;
        this.carreasDao = carreasDao;
    }

    public UsuariosDto obtenerUsuario(String kcUuid){
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        return UsuariosDto.fromEntity(usuario);
    }

    public PersonasDto agregarPersona(PersonasDto personasDto){
        if(personasDto.getFotoPerfil() == null){
            //TODO: Cambiar la foto por defecto con el url de la imagen por defecto en minio
            personasDto.setFotoPerfil("https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png");
        }
       return PersonasDto.fromEntity(personasDao.save(personasDto.toEntity()));
    }

    public UsuariosDto agregarUsuario(UsuariosDto usuariosDto){
        Roles roles = rolesDao.findById(usuariosDto.getIdRoles()).orElse(null);
        if(roles != null){
            List<String> rolesList = new ArrayList<>();
            rolesList.add(roles.getRol());
            keycloakService.updateUserRoles(usuariosDto.getKc_UUID(), rolesList);
        }
        Date date = new Date(System.currentTimeMillis());
        usuariosDto.setFechaRegistro(date);
        Time time = new Time(System.currentTimeMillis());
        usuariosDto.setHoraRegistro(time);

        Usuarios usuario = usuariosDto.toEntity();
        usuario.setPersonasIdpersonas(personasDao.findById(usuariosDto.getIdPersonas()).orElse(null));
        usuario.setRolesIdroles(roles);
        usuario.setCarrerasIdcarreras(carreasDao.findById(usuariosDto.getIdCarreras()).orElse(null));

        return UsuariosDto.fromEntity(usuariosDao.save(usuario));
    }

    public UsuariosInstitucionesDto agregarUsuarioInstitucion(String kcUuid, Integer idInstitucion, String cargo){
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        Instituciones institucion = institucionesDao.findById(idInstitucion).orElse(null);
        if(usuario != null && institucion != null){
            Usuariosinstituciones usuariosinstituciones = new Usuariosinstituciones();
            usuariosinstituciones.setUsuariosIdusuarios(usuario);
            usuariosinstituciones.setInstitucionesIdinstituciones(institucion);
            usuariosinstituciones.setCargo(cargo);
            usuariosInstitucionesDao.save(usuariosinstituciones);
            return UsuariosInstitucionesDto.fromEntity(usuariosinstituciones);
        }
        return null;
    }

}
