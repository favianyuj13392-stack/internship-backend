package ucb.edu.bo.internship.internship_backend.bl;

import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.InstitucionesDao;

@Service
public class AdministradorBl {
    private final InstitucionesDao institucionesDao;
    public AdministradorBl(InstitucionesDao institucionesDao) {
        this.institucionesDao = institucionesDao;
    }
    /*//Cambiar el estado de una institucion
    public void cambiarEstadoInstitucion(Integer idInstitucion, Boolean estado) {
        institucionesDao.cambiarEstadoInstitucion(idInstitucion, estado);
    }*/


}
