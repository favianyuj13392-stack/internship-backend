package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.PasantiasDao;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasDto;

import java.util.Date;

@Service
public class PasantiaBl {
    private final PasantiasDao pasantiasDao;

    public PasantiaBl(PasantiasDao pasantiasDao) {
        this.pasantiasDao = pasantiasDao;
    }

    public Page<PasantiasDto> obtenerPasantiasPorTerminoDeBusqueda(String terminoDeBusqueda, Pageable pageable){
        Date fechaActual = new Date();
        return pasantiasDao.findAllByActivoIsTrueAndFechacierreAfter(
                fechaActual,
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
}
