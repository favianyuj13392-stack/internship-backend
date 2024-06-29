package ucb.edu.bo.internship.internship_backend.bl;

import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosInstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuarioConCorreoYNombreCompletoYFotoDto;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.List;

@Service
public class AdministradorBl {
    private final InstitucionesDao institucionesDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    public AdministradorBl(InstitucionesDao institucionesDao, UsuariosInstitucionesDao usuariosInstitucionesDao) {
        this.institucionesDao = institucionesDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
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

    public UsuarioConCorreoYNombreCompletoYFotoDto relacionarUsuarioInstitucion(Integer idInstituciones, Integer idUsuarios, Boolean estado) {
        try {
            Instituciones instituciones = institucionesDao.findById(idInstituciones).orElse(null);
            if (instituciones != null) {
                Usuariosinstituciones usuariosInstituciones = usuariosInstitucionesDao.findByIdInstitucionesAndIdUsuarios(idInstituciones, idUsuarios).orElse(null);
                if (usuariosInstituciones != null) {
                    if(usuariosInstituciones.getActivo()){
                        throw new UsuarioYaRelacionadoException("El usuario ya esta relacionado con la institucion");
                    }
                    if(!estado){
                        usuariosInstitucionesDao.delete(usuariosInstituciones);
                        return new UsuarioConCorreoYNombreCompletoYFotoDto();
                    }
                    usuariosInstituciones.setActivo(true);
                    usuariosInstituciones = usuariosInstitucionesDao.save(usuariosInstituciones);
                    return new UsuarioConCorreoYNombreCompletoYFotoDto(usuariosInstituciones);
                }else{
                    throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
                }
            }else{
                throw new InstitucionNotFoundException("Institucion no encontrada");
            }
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al relacionar el usuario con la institucion",e);
        }
    }
}
