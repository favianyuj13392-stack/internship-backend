package ucb.edu.bo.internship.internship_backend.bl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
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

    private final PasantiaBl pasantiasBl;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final InstitucionesDao institucionesDao;
    private final RolesDao rolesDao;
    private final CarrerasDao carreasDao;
    private final IKeycloakService keycloakService;

    public UsuariosBL(UsuariosDao usuariosDao,
                      PersonasDao personasDao, UsuariosInstitucionesDao usuariosInstitucionesDao,
                      InstitucionesDao institucionesDao, RolesDao rolesDao, IKeycloakService keycloakService, CarrerasDao carreasDao,
                      PasantiaBl pasantiasBl) {
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.institucionesDao = institucionesDao;
        this.rolesDao = rolesDao;
        this.keycloakService = keycloakService;
        this.carreasDao = carreasDao;
        this.pasantiasBl = pasantiasBl;
    }

    public UsuariosDto obtenerUsuario(String kcUuid){
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        return UsuariosDto.fromEntity(usuario);
    }


    public Boolean obtenerUsuarioExistencia(String kcUuid){
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        return usuario != null;
    }

    public Boolean userIs(String kcUuid,String role){
        return usuariosDao.userIs(kcUuid,role);
    }

    public PersonasDto agregarPersona(PersonasDto personasDto){
        if(personasDto.getFotoPerfil() == null){
            //TODO: Cambiar la foto por defecto con el url de la imagen por defecto en minio
            personasDto.setFotoPerfil("https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png");
        }
        if(personasDto.getBannerPerfil()==null){
            //TODO: Cambiar la foto por defecto con el url de la imagen por defecto en minio
            personasDto.setBannerPerfil("https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png");
        }
       return PersonasDto.fromEntity(personasDao.save(personasDto.toEntity()));
    }

    public UsuariosDto agregarUsuario(UsuariosDto usuariosDto){
        Roles roles = rolesDao.findById(usuariosDto.getIdRoles()).orElse(null);
        if(roles != null){
            List<String> rolesList = new ArrayList<>();
            rolesList.add(roles.getRol());
            keycloakService.addRealmRoleToUser(usuariosDto.getKc_UUID(),rolesList.get(0) );
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
    private UsuariosDto agregarUsuarioInstitucion(UsuariosDto usuariosDto)
    {
        Roles roles = rolesDao.findById(usuariosDto.getIdRoles()).orElse(null);
        if(roles != null){
            List<String> rolesList = new ArrayList<>();
            rolesList.add(roles.getRol());
            keycloakService.addRealmRoleToUser(usuariosDto.getKc_UUID(),rolesList.get(0) );
        }
        Date date = new Date(System.currentTimeMillis());
        usuariosDto.setFechaRegistro(date);
        Time time = new Time(System.currentTimeMillis());
        usuariosDto.setHoraRegistro(time);

        Usuarios usuario = usuariosDto.toEntity();
        usuario.setPersonasIdpersonas(personasDao.findById(usuariosDto.getIdPersonas()).orElse(null));
        usuario.setRolesIdroles(roles);
        return UsuariosDto.fromEntityInstitucion(usuariosDao.save(usuario));
    }

    public UsuariosInstitucionesDto agregarUsuarioInstitucion(String kcUuid, Integer idInstitucion, String cargo){
    try {
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        Instituciones institucion = institucionesDao.findById(idInstitucion).orElse(null);
        if(usuario != null && institucion != null){
            Usuariosinstituciones usuariosinstituciones = new Usuariosinstituciones();
            usuariosinstituciones.setUsuariosIdusuarios(usuario);
            usuariosinstituciones.setInstitucionesIdinstituciones(institucion);
            usuariosinstituciones.setCargo(cargo);
            usuariosinstituciones.setActivo(false);
            System.out.println(usuariosinstituciones);
            usuariosinstituciones = usuariosInstitucionesDao.save(usuariosinstituciones);
            return UsuariosInstitucionesDto.fromEntity(usuariosinstituciones);
        }
        return null;
    }catch (Exception e){
        logger.error("Error al agregar usuario institucion",e);
        return null;
    }
    }

    public UsuarioRegistroCompletoDto agregarUsuarioCompleto(UsuarioRegistroCompletoDto usuarioRegistroCompletoDto){
        PersonasDto persona_aux = usuarioRegistroCompletoDto.getPersona();
        persona_aux.setHabilidades(persona_aux.getHabilidades().toString());
        persona_aux.setHabilidadesSeleccionada(persona_aux.getHabilidadesSeleccionada().toString());
        persona_aux.setExperiencia(persona_aux.getExperiencia().toString());
        persona_aux.setRedesSociales(persona_aux.getRedesSociales().toString());
        usuarioRegistroCompletoDto.setPersona(persona_aux);
        PersonasDto personaAgregada = agregarPersona(usuarioRegistroCompletoDto.getPersona());
        usuarioRegistroCompletoDto.setPersona(personaAgregada);      
        usuarioRegistroCompletoDto.setIdPersonas(personaAgregada.getIdPersona());

        UsuariosDto usuarioAgregado = agregarUsuario(usuarioRegistroCompletoDto);

        InstitucionesDto institucion = usuarioRegistroCompletoDto.getInstitucion();

        if(institucion == null){
            Roles roles = rolesDao.findByRol("ESTUDIANTE");
            usuarioRegistroCompletoDto.setIdRoles(
                    roles.getIdroles()
            );
            return new UsuarioRegistroCompletoDto(usuarioAgregado, personaAgregada, null, null);
        }else{
            usuarioRegistroCompletoDto.setIdRoles(
                    rolesDao.findByRol("EMPRESA").getIdroles()
            );
            Instituciones instituciones = institucionesDao.findByNombre(institucion.getNombre());
            if(instituciones == null){
                instituciones = institucionesDao.save(institucion.toEntity());
            }
            UsuariosInstitucionesDto usuarioInstitucionAgregado = agregarUsuarioInstitucion(usuarioRegistroCompletoDto.getKc_UUID(), instituciones.getIdinstituciones(), usuarioRegistroCompletoDto.getCargo());
            return new UsuarioRegistroCompletoDto(usuarioAgregado, personaAgregada, institucion, usuarioInstitucionAgregado.getCargo());
        }


    }
    @Transactional
    public UsuarioRegistroCompletoDto agregarUsuarioCompletoInstitucion(UsuarioRegistroCompletoDto usuarioRegistroCompletoDto) {
        try {
            PersonasDto persona_aux = usuarioRegistroCompletoDto.getPersona();
            persona_aux.setHabilidades(persona_aux.getHabilidades().toString());
            persona_aux.setHabilidadesSeleccionada(persona_aux.getHabilidadesSeleccionada().toString());
            persona_aux.setExperiencia(persona_aux.getExperiencia().toString());
            persona_aux.setRedesSociales(persona_aux.getRedesSociales().toString());
            usuarioRegistroCompletoDto.setPersona(persona_aux);
            //Agregar Persona
            usuarioRegistroCompletoDto.setPersona(agregarPersona(usuarioRegistroCompletoDto.getPersona()));
            //Poner la persona agregada en el usuario
            usuarioRegistroCompletoDto.setIdPersonas(usuarioRegistroCompletoDto.getPersona().getIdPersona());
            //Agregar Usuario
            usuarioRegistroCompletoDto.setCargo(usuarioRegistroCompletoDto.getCargo());
            usuarioRegistroCompletoDto.setIdRoles(rolesDao.findByRol("EMPRESA").getIdroles());
            usuarioRegistroCompletoDto = new UsuarioRegistroCompletoDto(agregarUsuarioInstitucion(usuarioRegistroCompletoDto), usuarioRegistroCompletoDto.getPersona(), usuarioRegistroCompletoDto.getInstitucion(), usuarioRegistroCompletoDto.getCargo());
            //Agregar Institucion




            InstitucionesDto institucionesDto = usuarioRegistroCompletoDto.getInstitucion();
            Instituciones instituciones = new Instituciones();
            if(institucionesDto.getIdInstituciones()==null){
                Instituciones instituciones1 = institucionesDto.toEntity();
                instituciones1.setActivo(false);
                instituciones = institucionesDao.save(instituciones1);
            }else{
                instituciones = institucionesDao.findById(institucionesDto.getIdInstituciones()).orElse(null);
            
            }

            if(instituciones == null){
                instituciones = institucionesDao.save(institucionesDto.toEntity());
            }           


            institucionesDto = InstitucionesDto.fromEntity(instituciones);
            usuarioRegistroCompletoDto.setInstitucion(institucionesDto);

            //Agregar UsuarioInstitucion
            UsuariosInstitucionesDto usuariosInstitucionesDto = agregarUsuarioInstitucion(usuarioRegistroCompletoDto.getKc_UUID(),
                    usuarioRegistroCompletoDto.getInstitucion().getIdInstituciones(),
                    usuarioRegistroCompletoDto.getCargo()
            );
           

            usuarioRegistroCompletoDto.setCargo(usuariosInstitucionesDto.getCargo());

           



            return usuarioRegistroCompletoDto;
        }catch (Exception e){
            logger.error("Error al agregar usuario completo institucion",e);
            return null;
        }
    }
}
