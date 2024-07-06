package ucb.edu.bo.internship.internship_backend.bl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasCarrerasDao;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PasantiaBl {
    private final PasantiasDao pasantiasDao;
    private final PasantiasCarrerasDao pasantiasCarrerasDao;

    public PasantiaBl(PasantiasDao pasantiasDao, PasantiasCarrerasDao pasantiasCarrerasDao) {
        this.pasantiasDao = pasantiasDao;
        this.pasantiasCarrerasDao = pasantiasCarrerasDao;
    }

    public Page<PasantiasConInstitucionYCarrerasDto> obtenerPasantiasPorTerminoDeBusqueda(String terminoDeBusqueda, Pageable pageable){
        Date fechaActual = new Date();
        return pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
                fechaActual,
                terminoDeBusqueda,
                pageable
        ).map(this::toPasantiasConInstitucionYCarrerasDto);
    }


    public Page<PasantiasConInstitucionYCarrerasDto> obtenerTodasPasantias(Pageable pageable){
        Date fechaActual = new Date();
        return pasantiasDao.findAllByActivoIsTrueAndFechacierreAfter(
                fechaActual,
                pageable
        ).map(this::toPasantiasConInstitucionYCarrerasDto);
    }

    public PasantiasDto obtenerPasantiaPorId(Integer id){
        Pasantias pasantias = pasantiasDao.findByIdpasantiasAndActivoIsTrue(id);
        PasantiasDto pasantiasDto = PasantiasDto.fromEntity(pasantias);
        InstitucionesDto institucionesDto = InstitucionesDto.fromEntity(pasantias.getInstitucionesIdinstituciones());
        List<CarrerasDto> carrerasDto = pasantiasCarrerasDao.findAllByPasantiasIdpasantias(pasantias).stream().map(pasantiasCarreras -> CarrerasDto.fromEntity(pasantiasCarreras.getCarrerasIdcarreras())).toList();

        return toPasantiasConInstitucionYCarrerasDto(pasantias);
    }

    public List<PasantiasConInstitucionYCarrerasDto> obtenerPasantiasRelacionadas(Integer id){
        return pasantiasDao.findTop3RelatedPasantias(id).stream().map(this::toPasantiasConInstitucionYCarrerasDto).toList();
    }

    private PasantiasConInstitucionYCarrerasDto toPasantiasConInstitucionYCarrerasDto(Pasantias pasantias1) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode areas, beneficios, funciones, requisitos;
        try{
            Object areasJson, beneficiosJson, funcionesJson, requisitosJson;
            areasJson = pasantias1.getAreas();
            beneficiosJson = pasantias1.getBeneficios();
            funcionesJson = pasantias1.getFunciones();
            requisitosJson = pasantias1.getRequisitos();
            areas = objectMapper.readTree(areasJson.toString());
            beneficios = objectMapper.readTree(beneficiosJson.toString());
            funciones = objectMapper.readTree(funcionesJson.toString());
            requisitos = objectMapper.readTree(requisitosJson.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir areas a objeto:" + e);
        }
        PasantiasDto pasantiasDto = PasantiasDto.fromEntity(pasantias1);
        pasantiasDto.setAreas(areas);
        pasantiasDto.setBeneficios(beneficios);
        pasantiasDto.setFunciones(funciones);
        pasantiasDto.setRequisitos(requisitos);
        InstitucionesDto institucionesDto = InstitucionesDto.fromEntity(pasantias1.getInstitucionesIdinstituciones());
        List<CarrerasDto> carrerasDto = pasantiasCarrerasDao.findAllByPasantiasIdpasantias(pasantias1).stream().map(pasantiasCarreras -> CarrerasDto.fromEntity(pasantiasCarreras.getCarrerasIdcarreras())).toList();
        return new PasantiasConInstitucionYCarrerasDto(pasantiasDto, institucionesDto, carrerasDto);
    }

    public Page<PasantiaConNombreYLogoEmpresaDto> obtenerPasantias(Integer page, Integer size, String search, String sort, String active) {
        try {
            Pageable pageable = buildPageable(page, size, sort);
            Date fechaActual = new Date();
            if (active.equals("true")) {
                return pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(fechaActual, search, pageable).map(PasantiaConNombreYLogoEmpresaDto::fromEntity);
            } else if (active.equals("false")) {
                return pasantiasDao.findAllByActivoIsFalseAndTituloContainingIgnoreCase(search, pageable).map(PasantiaConNombreYLogoEmpresaDto::fromEntity);
            } else {
                return pasantiasDao.findAllByTituloContainingIgnoreCase(search, pageable).map(PasantiaConNombreYLogoEmpresaDto::fromEntity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las pasantias");
        }
    }
    @NotNull
    private Pageable buildPageable(Integer page, Integer size, String sort){
        Sort.Order order = Sort.Order.desc("idpasantias");
        if(Objects.equals(sort, "titulo")){
            order = Sort.Order.asc("titulo");
        }
        return PageRequest.of(page, size, Sort.by(order));
    }

    public Long obtenerCantidadPasantiasPorInstitucion(Integer idInstituciones) {
        return pasantiasDao.countAllByInstitucionesIdinstituciones(idInstituciones);
    }
}
