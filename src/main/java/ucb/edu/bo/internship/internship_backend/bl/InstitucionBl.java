package ucb.edu.bo.internship.internship_backend.bl;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.config.GlobalSecurityConfiguration;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionConPasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionNombreDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.PasantiaNombreDto;
import ucb.edu.bo.internship.internship_backend.entity.Instituciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitucionBl {
    private final InstitucionesDao institucionesDao;
    private final PasantiasDao pasantiasDao;
    public InstitucionBl(InstitucionesDao institucionesDao, PasantiasDao pasantiasDao) {
        this.institucionesDao = institucionesDao;
        this.pasantiasDao = pasantiasDao;
    }
    //Agregar una institucion
    public InstitucionesDto agregarInstitucion(@NotNull InstitucionesDto institucionesDto){
        try {
            institucionesDto.setActivo(false);
            Instituciones instituciones = new Instituciones(institucionesDto);
            institucionesDao.save(instituciones);
            return new InstitucionesDto(instituciones);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    //Obtener todas las instituciones
    public Page<InstitucionesDto> obtenerInstituciones(Integer page, Integer size, String search,String sort){
        try{
            Pageable pageable = buildPageable(page, size, sort);
            if(search != null && !search.isEmpty()){
                Page<Instituciones> instituciones = institucionesDao.findAllByNombreContainingAndActivo(search,true,pageable);
                return instituciones.map(InstitucionesDto::new);
            }else {
                Page<Instituciones> instituciones = institucionesDao.findAllByActivo(true,pageable);
                return instituciones.map(InstitucionesDto::new);
            }
        }catch (Exception e){
            return null;
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
            if (instituciones != null) {
                return new InstitucionConPasantiasDto(instituciones, pasantiasDao.findPasantiasByInstitucionesIdinstituciones(id));
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<InstitucionNombreDto> obtenerInstitucionesNombre() {
        try {
            return institucionesDao.getAllIdAndNameByActivo(true);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
