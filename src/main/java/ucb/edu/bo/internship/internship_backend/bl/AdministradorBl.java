package ucb.edu.bo.internship.internship_backend.bl;

import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuarioConCorreoYNombreCompletoYFotoDto;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;

import java.util.List;

@Service
public class AdministradorBl {
    private final InstitucionesDao institucionesDao;
    public AdministradorBl(InstitucionesDao institucionesDao) {
        this.institucionesDao = institucionesDao;
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
}
