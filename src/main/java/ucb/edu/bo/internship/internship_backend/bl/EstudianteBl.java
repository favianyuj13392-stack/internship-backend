package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.PersonasDao;
import ucb.edu.bo.internship.internship_backend.dao.RolesDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dto.CarrerasDto;
import ucb.edu.bo.internship.internship_backend.dto.PersonasDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosConPersonaYCarreraDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosDto;
import ucb.edu.bo.internship.internship_backend.entity.Personas;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.util.Objects;

@Service
public class EstudianteBl {

    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final RolesDao rolesDao;

    private Logger logger = LoggerFactory.getLogger(EstudianteBl.class);

    public EstudianteBl(UsuariosDao usuariosDao, PersonasDao personasDao, RolesDao rolesDao) {
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.rolesDao = rolesDao;
    }

    public UsuariosDto obtenerEstudianteByUuid(String uuid) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        logger.info("Usuario encontrado: " + uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");

        return new UsuariosConPersonaYCarreraDto(UsuariosDto.fromEntity(usuario),
                 PersonasDto.fromEntity(usuario.getPersonasIdpersonas()), CarrerasDto.fromEntity(usuario.getCarrerasIdcarreras()));
    }

}
