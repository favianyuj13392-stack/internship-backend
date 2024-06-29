package ucb.edu.bo.internship.internship_backend.bl;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosInstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;
import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstitucionBl {
    private final InstitucionesDao institucionesDao;
    private final PasantiasDao pasantiasDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final UsuariosDao usuariosDao;
    private final UsuariosBL usuariosBL;
    public InstitucionBl(InstitucionesDao institucionesDao, PasantiasDao pasantiasDao, UsuariosInstitucionesDao usuariosInstitucionesDao,UsuariosDao usuariosDao, UsuariosBL usuariosBL) {
        this.institucionesDao = institucionesDao;
        this.pasantiasDao = pasantiasDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.usuariosDao = usuariosDao;
        this.usuariosBL = usuariosBL;
    }
    //Agregar una institucion
    @Transactional
    public InstitucionesDto agregarInstitucion(@NotNull InstitucionesDto institucionesDto){
        try {
            institucionesDto.setActivo(false);
            Instituciones instituciones = new Instituciones(institucionesDto);
            instituciones = institucionesDao.save(instituciones);
            return new InstitucionesDto(instituciones);
        }catch (Exception e){
            throw new InstitucionServiceExcepcion("Error al agregar la institucion",e);
        }
    }
    //Obtener todas las instituciones
    public Page<InstitucionesDto> obtenerInstituciones(Integer page, Integer size, String search,String sort){
        try{
            Pageable pageable = buildPageable(page, size, sort);
            Page<Instituciones> instituciones;
            if(search != null && !search.isEmpty()){
                instituciones = institucionesDao.findAllByNombreContainingAndActivo(search, true, pageable);
            }else {
                instituciones = institucionesDao.findAllByActivo(true, pageable);
            }
            return instituciones.map(InstitucionesDto::new);
        }catch (Exception e){
            throw new InstitucionServiceExcepcion("Error al obtener las instituciones",e);
        }
    }
    @NotNull
    private Pageable buildPageable(Integer page, Integer size, String sort){
        return PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));
    }
    @Transactional
    public InstitucionConPasantiasDto obtenerInstitucionById(Integer id) {
        try {
            Instituciones instituciones = institucionesDao.findByIdinstitucionesAndActivo(id, true);
            if (instituciones == null) {
                throw new InstitucionNotFoundException("Institucion no encontrada");
            } else {
                return new InstitucionConPasantiasDto(instituciones, pasantiasDao.findPasantiasByInstitucionesIdinstituciones(id));
            }
        } catch (InstitucionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener la institución", e);
        }
    }

    public List<InstitucionNombreDto> obtenerInstitucionesNombre() {
        try {
            return institucionesDao.getAllIdAndNameByActivo(true);
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener las instituciones", e);
        }
    }
    @Transactional
    public List<InstitucionConPasantiasDto> obtenerCuatroInstitucionesRelacionadas(Integer id) {
        try {
            List<Instituciones> instituciones = institucionesDao.findTop4InstitucionesBySectoresAndPasantias(id);
            List<InstitucionConPasantiasDto> institucionConPasantiasDtos = new ArrayList<>();
            for (Instituciones institucion : instituciones) {
                institucionConPasantiasDtos.add(new InstitucionConPasantiasDto(institucion, pasantiasDao.findPasantiasByInstitucionesIdinstituciones(institucion.getIdinstituciones())));
            }
            return institucionConPasantiasDtos;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener las instituciones relacionadas", e);
        }
    }

    public Page<InstitucionesConCOUNTPasantiasDto> obtenerInstitucionesDestacadas(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return institucionesDao.getAllNameAndCountPasantiasByActivo(true, pageable);
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener las instituciones destacadas", e);
        }
    }
    @Transactional
    public InstitucionesDto actualizarInstitucion(String uuid,InstitucionesDto institucionesDto, Integer id) {
        try {
            if (!validarRelacionUsuarioInstitucion(uuid, id)) {
                throw new UsuarioYaRelacionadoException("El usuario no esta relacionado con la institucion");
            }
            Instituciones instituciones = institucionesDao.findByIdinstitucionesAndActivo(id, true);
            if (instituciones != null) {
                instituciones.setCorreo(institucionesDto.getCorreo());
                instituciones.setDescripcion(institucionesDto.getDescripcion());
                instituciones.setDireccion(institucionesDto.getDireccion());
                instituciones.setFotoinstitucion(institucionesDto.getFotoInstitucion());
                instituciones.setFotos(institucionesDto.getFotos());
                instituciones.setLogoempresa(institucionesDto.getLogoEmpresa());
                instituciones.setNombre(institucionesDto.getNombre());
                instituciones.setRedessociales(institucionesDto.getRedesSociales());
                instituciones.setSectores(institucionesDto.getSectores());
                instituciones = institucionesDao.save(instituciones);
                return new InstitucionesDto(instituciones);
            } else {
                throw new InstitucionNotFoundException("Institucion no encontrada");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al actualizar la institucion", e);
        }
    }
    @Transactional
    public InstitucionesDto suscribirseEmpresa(Integer idInstitucion, String uuid, SuscribirseInstitucionDto suscribirseInstitucionDto) {
        try {
            //Validar que el usuario no este relacionado con ninguna institucion
            if(!validarQueUsuarioNoEsteRelacionadoConCualquierInstitucion(uuid)) {
                //Validar que el usuario sea EMPRESA
                if(usuariosBL.userIs(uuid, "EMPRESA")){
                    if (institucionesDao.existsByIdinstitucionesAndActivoIsTrue(idInstitucion)) {
                        Usuariosinstituciones usuariosInstituciones = new Usuariosinstituciones();
                        usuariosInstituciones.setCargo(suscribirseInstitucionDto.getCargo());
                        usuariosInstituciones.setInstitucionesIdinstituciones(institucionesDao.findByIdinstitucionesAndActivo(idInstitucion, true));
                        usuariosInstituciones.setUsuariosIdusuarios(usuariosDao.findByKcUuid(uuid));
                        usuariosInstituciones.setActivo(false);
                        usuariosInstitucionesDao.save(usuariosInstituciones);
                        return new InstitucionesDto(institucionesDao.findByIdinstitucionesAndActivo(idInstitucion, true));
                    } else {
                        throw new InstitucionNotFoundException("Institucion no encontrada");
                    }
                }else{
                    throw new UsuarioYaRelacionadoException("El usuario no es una empresa");
                }
            }else{
                throw new UsuarioYaRelacionadoException("El usuario ya esta relacionado con una institucion o en espera de aprobacion para relacionarse con una institucion");
            }
        } catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e) {
            throw e;
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al suscribirse a la institucion", e);
        }
    }
    private Boolean validarRelacionUsuarioInstitucion(String uuid, Integer idInstitucion){
        return usuariosInstitucionesDao.existsByUsuariosUuidAndInstitucionesIdinstituciones(uuid, idInstitucion);
    }
    private Boolean validarQueUsuarioNoEsteRelacionadoConCualquierInstitucion(String uuid){
        return usuariosInstitucionesDao.existsByUsuariosUuid(uuid);
    }

}
