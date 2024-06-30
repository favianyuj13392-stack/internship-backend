package ucb.edu.bo.internship.internship_backend.bl;

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
import ucb.edu.bo.internship.internship_backend.dto.CarrerasDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasConInstitucionYCarrerasDto;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasDto;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

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

    public Page<PasantiasDto> obtenerPasantiasPorTerminoDeBusqueda(String terminoDeBusqueda, Pageable pageable){
        Date fechaActual = new Date();
        return pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(
                fechaActual,
                terminoDeBusqueda,
                pageable
        ).map(PasantiasDto::fromEntity);
    }

    public Page<PasantiasDto> obtenerTodasPasantias(Pageable pageable){
        Date fechaActual = new Date();
        return pasantiasDao.findAllByActivoIsTrueAndFechacierreAfter(
                fechaActual,
                pageable
        ).map(PasantiasDto::fromEntity);
    }

    public PasantiasDto obtenerPasantiaPorId(Integer id){
        Pasantias pasantias = pasantiasDao.findByIdpasantiasAndActivoIsTrue(id);
        PasantiasDto pasantiasDto = PasantiasDto.fromEntity(pasantias);
        InstitucionesDto institucionesDto = InstitucionesDto.fromEntity(pasantias.getInstitucionesIdinstituciones());
        List<CarrerasDto> carrerasDto = pasantiasCarrerasDao.findAllByPasantiasIdpasantias(pasantias).stream().map(pasantiasCarreras -> CarrerasDto.fromEntity(pasantiasCarreras.getCarrerasIdcarreras())).toList();
        return new PasantiasConInstitucionYCarrerasDto(pasantiasDto, institucionesDto, carrerasDto);
    }

    public List<PasantiasDto> obtenerPasantiasRelacionadas(Integer id){
        return pasantiasDao.findTop3RelatedPasantias(id).stream().map(PasantiasDto::fromEntity).toList();
    }

    public Page<PasantiasDto> obtenerPasantias(Integer page, Integer size, String search, String sort, String active) {
        try {
            Pageable pageable = buildPageable(page, size, sort);
            Date fechaActual = new Date();
            if (active.equals("true")) {
                return pasantiasDao.findAllByActivoIsTrueAndFechacierreAfterAndTituloContainingIgnoreCase(fechaActual, search, pageable).map(PasantiasDto::fromEntity);
            } else if (active.equals("false")) {
                return pasantiasDao.findAllByActivoIsFalseAndTituloContainingIgnoreCase(search, pageable).map(PasantiasDto::fromEntity);
            } else {
                return pasantiasDao.findAllByTituloContainingIgnoreCase(search, pageable).map(PasantiasDto::fromEntity);
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
}
