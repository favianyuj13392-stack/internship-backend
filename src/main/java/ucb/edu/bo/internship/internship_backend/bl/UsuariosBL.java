package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosDto;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

@Service
public class UsuariosBL {

    private final Logger logger = LoggerFactory.getLogger(UsuariosBL.class);

    private final UsuariosDao usuariosDao;

    public UsuariosBL(UsuariosDao usuariosDao) {
        this.usuariosDao = usuariosDao;
    }

    public UsuariosDto obtenerUsuario(String kcUuid){
        Usuarios usuario = usuariosDao.findByKcUuid(kcUuid);
        return UsuariosDto.fromEntity(usuario);
    }

}
