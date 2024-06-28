package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dao.CurriculumsDao;
import ucb.edu.bo.internship.internship_backend.dao.PersonasDao;
import ucb.edu.bo.internship.internship_backend.dao.RolesDao;
import ucb.edu.bo.internship.internship_backend.dao.UsuariosDao;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.Curriculums;
import ucb.edu.bo.internship.internship_backend.entity.Personas;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;
import ucb.edu.bo.internship.internship_backend.service.MinioService;

import java.util.Date;
import java.util.Objects;

@Service
public class EstudianteBl {
    private MinioBl minioBl;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final RolesDao rolesDao;

    private final CurriculumsDao curriculumsDao;

    private Logger logger = LoggerFactory.getLogger(EstudianteBl.class);

    public EstudianteBl(UsuariosDao usuariosDao, PersonasDao personasDao, RolesDao rolesDao, CurriculumsDao curriculumsDao, MinioBl minioBl) {
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.rolesDao = rolesDao;
        this.curriculumsDao = curriculumsDao;
        this.minioBl = minioBl;
    }

    public UsuariosDto obtenerEstudianteByUuid(String uuid) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        logger.info("Usuario encontrado: " + uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");

        return new UsuariosConPersonaYCarreraDto(UsuariosDto.fromEntity(usuario),
                 PersonasDto.fromEntity(usuario.getPersonasIdpersonas()), CarrerasDto.fromEntity(usuario.getCarrerasIdcarreras()));
    }

    public Boolean actualizarEstudianteByUuid(String uuid, PersonasDto personasDto) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");

        Personas persona = usuario.getPersonasIdpersonas();
        persona.setNombres(personasDto.getNombre());
        persona.setApellidomaterno(personasDto.getApellidoMaterno());
        persona.setApellidopaterno(personasDto.getApellidoPaterno());
        persona.setCi(personasDto.getCi());
        persona.setTelefono(personasDto.getTelefono());
        persona.setFotoperfil(personasDto.getFotoPerfil());
        persona.setFechadenacimiento(personasDto.getFechaDeNacimiento());
        persona.setAnioingresouniversidad(personasDto.getAnioIngresoUniversidad());
        persona.setDescripcion(personasDto.getDescripcion());
        persona.setHabilidades(personasDto.getHabilidades());
        persona.setHabilidadesseleccionadas(personasDto.getHabilidadesSeleccionada());
        persona.setExperiencia(personasDto.getExperiencia());
        persona.setRedessociales(personasDto.getRedesSociales());
        personasDao.save(persona);
        return true;
    }

    public Boolean agregarCurriculum(String uuid, MultipartFile curriculum) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
        NewFileDto newFileDto = minioBl.uploadFile(curriculum, "internship-cv");
        if(newFileDto == null) throw new RuntimeException("Error al subir el curriculum");
        String filepath = minioBl.getFile("internship-cv", newFileDto.getFileName());
        Curriculums curriculumEntity = new Curriculums();
        curriculumEntity.setFechacargado(new Date());
        curriculumEntity.setTitulo(curriculum.getOriginalFilename());
        curriculumEntity.setPdfcurriculum(filepath);
        curriculumEntity.setUsuariosIdusuarios(usuario);

        curriculumsDao.save(curriculumEntity);

        return true;
    }

    public String obtenerCurriculum(String uuid, String curriculumPdf) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
        Curriculums curriculum = curriculumsDao.findByTitulo(curriculumPdf);
        if(curriculum == null) throw new RuntimeException("Curriculum no encontrado");
        return curriculum.getPdfcurriculum();
    }

}
