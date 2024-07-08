package ucb.edu.bo.internship.internship_backend.bl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.List;

@Service
public class AdministradorBl {
    private final InstitucionesDao institucionesDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final PasantiasDao pasantiasDao;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    public AdministradorBl(InstitucionesDao institucionesDao, UsuariosInstitucionesDao usuariosInstitucionesDao, PasantiasDao pasantiasDao, UsuariosDao usuariosDao, PersonasDao personasDao) {
        this.institucionesDao = institucionesDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.pasantiasDao = pasantiasDao;
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
    }

    public InstitucionesDto cambiarEstadoInstitucion(Integer idInstituciones, Boolean estado) {
        try {
            Instituciones instituciones = institucionesDao.findById(idInstituciones).orElse(null);
            if (instituciones != null) {
                instituciones.setActivo(estado);
                instituciones = institucionesDao.save(instituciones);
                return new InstitucionesDto(instituciones);
            }else{
                throw new InstitucionNotFoundException("Institucion no encontrada");
            }
        }catch (InstitucionNotFoundException e){
            throw new InstitucionNotFoundException("Institucion no encontrada");
        }catch (Exception e){
            throw new RuntimeException("Error al cambiar el estado de la institucion",e);
        }
    }

    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerUsuariosInstitucion(Integer idInstituciones) {
        try {
            return institucionesDao.findById(idInstituciones)
                    .orElseThrow(() -> new InstitucionNotFoundException("Institucion no encontrada"))
                    .getUsuariosinstitucionesList()
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (InstitucionNotFoundException e) {
            throw new InstitucionNotFoundException("Institucion no encontrada");
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener los usuarios de la institucion", e);
        }
    }
    @Transactional
    public UsuarioConCorreoYNombreCompletoYFotoDto relacionarUsuarioInstitucion(Integer idInstituciones, Integer idUsuariosInstituciones, Integer idUsuarios, Boolean estado) {
        try {
            Usuariosinstituciones usuariosInstituciones = usuariosInstitucionesDao.findById(idUsuariosInstituciones).orElse(null);
            if(usuariosInstituciones == null){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            if(!estado){
                //Borrar la relacion y los datos asociados
                Usuarios usuarios = usuariosInstituciones.getUsuariosIdusuarios();
                Personas personas = usuarios.getPersonasIdpersonas();
                Instituciones instituciones = usuariosInstituciones.getInstitucionesIdinstituciones();
                usuariosInstitucionesDao.delete(usuariosInstituciones);
                if(instituciones.getActivo()){
                    institucionesDao.delete(instituciones);
                }
                usuariosDao.delete(usuarios);
                personasDao.delete(personas);
                return new UsuarioConCorreoYNombreCompletoYFotoDto();
            }
            //Aceptar la relacion
            usuariosInstituciones.getUsuariosIdusuarios().setActivo(true);
            usuariosInstituciones.getInstitucionesIdinstituciones().setActivo(true);
            usuariosInstituciones.setActivo(true);
            usuariosInstituciones = usuariosInstitucionesDao.save(usuariosInstituciones);
            return new UsuarioConCorreoYNombreCompletoYFotoDto(usuariosInstituciones);
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al relacionar el usuario con la institucion",e);
        }
    }

    public PasantiasDto aceptarPasantia(Integer idPasantias) {
        try {
            Pasantias pasantias = pasantiasDao.findById(idPasantias).orElseThrow(() -> new InstitucionNotFoundException("Pasantia no encontrada"));
            if(pasantias.getActivo()){
                throw new UsuarioYaRelacionadoException("No se puede aceptar una pasantia activa");
            }
            pasantias.setActivo(true);
            pasantias = pasantiasDao.save(pasantias);
            return new PasantiasDto(pasantias);
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e ){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al aceptar la pasantia",e);
        }
    }

    public PasantiasDto rechazarPasantia(Integer idPasantias) {
        try {
            Pasantias pasantias = pasantiasDao.findById(idPasantias).orElseThrow(() -> new InstitucionNotFoundException("Pasantia no encontrada"));
            if(pasantias.getActivo()){
                throw new UsuarioYaRelacionadoException("No se puede eliminar una pasantia activa");
            }
            pasantiasDao.delete(pasantias);
            return new PasantiasDto();
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e ){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al rechazar la pasantia",e);
        }
    }

    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerSuscripcionAInstituciones(String nombreInstitucion){
        try {
            if(nombreInstitucion.isBlank()){
                return usuariosInstitucionesDao.findByActivoFalse()
                        .stream()
                        .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                        .toList();
            }
            return usuariosInstitucionesDao.findByInstitucionesIdinstitucionesNombreContainsAndActivoFalse(nombreInstitucion)
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener las suscripciones a las instituciones",e);
        }
    }

    public SolicitudIndormacionDto obtenerTodaLaInformacionDeSolicitud(Integer idInstitucion, Integer idSolicitud, Integer idUsuario) {
        try {
            Usuariosinstituciones usuariosinstituciones = usuariosInstitucionesDao.findById(idSolicitud).orElse(null);
            if(usuariosinstituciones == null){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            if(usuariosinstituciones.getActivo()){
                throw new InstitucionNotFoundException("La relacion entre el usuario y la institucion esta activa, no se puede obtener la informacion");
            }
            Usuarios usuarios = usuariosDao.findById(idUsuario).orElse(null);
            if (usuarios == null) throw new InstitucionNotFoundException("Usuario no encontrado");
            Instituciones instituciones = institucionesDao.findById(idInstitucion).orElse(null);
            if (instituciones == null) throw new InstitucionNotFoundException("Institucion no encontrada");
            if(usuariosinstituciones.getUsuariosIdusuarios().equals(usuarios) && usuariosinstituciones.getInstitucionesIdinstituciones().equals(instituciones)){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            return new SolicitudIndormacionDto(UsuariosDto.fromEntityInstitucion(usuarios),PersonasDto.fromEntity(usuarios.getPersonasIdpersonas()),InstitucionesDto.fromEntity(instituciones),UsuariosInstitucionesDto.fromEntity(usuariosinstituciones));
        }catch (InstitucionNotFoundException e){
            throw e;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener la informacion de la solicitud", e);
        }
    }
}
