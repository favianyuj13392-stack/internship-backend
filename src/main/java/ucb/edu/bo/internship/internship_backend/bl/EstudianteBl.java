package ucb.edu.bo.internship.internship_backend.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;
import ucb.edu.bo.internship.internship_backend.service.MinioService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class EstudianteBl {
    private MinioBl minioBl;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final RolesDao rolesDao;

    private final CurriculumsDao curriculumsDao;

    private final AplicacionPasantiasDao aplicacionPasantiasDao;

    private Logger logger = LoggerFactory.getLogger(EstudianteBl.class);
    private final PasantiasDao pasantiasDao;

    public EstudianteBl(UsuariosDao usuariosDao, PersonasDao personasDao, RolesDao rolesDao, CurriculumsDao curriculumsDao, MinioBl minioBl, AplicacionPasantiasDao aplicacionPasantiasDao,
                        PasantiasDao pasantiasDao) {
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.rolesDao = rolesDao;
        this.curriculumsDao = curriculumsDao;
        this.minioBl = minioBl;
        this.aplicacionPasantiasDao = aplicacionPasantiasDao;
        this.pasantiasDao = pasantiasDao;
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
        persona.setBannerperfil(personasDto.getBannerPerfil());
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
        try {
            System.out.println("subiendo pdf");
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
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error al subir el curriculum",e);
        }
    }

    public String obtenerCurriculum(String uuid, String curriculumPdf) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
        Curriculums curriculum = curriculumsDao.findByTitulo(curriculumPdf);
        if(curriculum == null) throw new RuntimeException("Curriculum no encontrado");
        return curriculum.getPdfcurriculum();
    }

    //TODO: Seleccionar curriculum
    public Boolean postularPasantia(String uuid, Integer curriculumId, Integer idPasantia) {
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        Pasantias pasantia = pasantiasDao.findById(idPasantia).orElseThrow(() -> new RuntimeException("Pasantia no encontrada"));
        if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
        Aplicacionespasantias aplicacion = new Aplicacionespasantias();
        aplicacion.setUsuariosIdusuarios(usuario);
        aplicacion.setPasantiasIdpasantias(pasantia);
        aplicacion.setFechaaplicacion(new Date());
        aplicacion.setActivo(false);
        aplicacionPasantiasDao.save(aplicacion);
        return true;
    }

    public List<CurriculumsDto> obtenerTodosLosCurriculums(String uuid) {
        try{
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
            return curriculumsDao.findByUsuariosIdusuarios(usuario).stream().map(CurriculumsDto::fromEntity).toList();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener los curriculums",e);
        }
    }
    public CurriculumsDto eliminarCurriculum(String uuid, Integer curriculumId) {
        try{
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
            Curriculums curriculum = curriculumsDao.findById(curriculumId).orElseThrow(() -> new RuntimeException("Curriculum no encontrado"));
            if(!curriculum.getUsuariosIdusuarios().equals(usuario)) throw new RuntimeException("El curriculum no pertenece al usuario");
            curriculumsDao.delete(curriculum);
            return CurriculumsDto.fromEntity(curriculum);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el curriculum",e);
        }
    }

    public AplicacionPasantiasDto aplicarPasantia(String uuid, Integer pasantiaId, Integer idCurriculum) {
        try{
            Usuarios usuario = usuariosDao.findByKcUuid(uuid);
            if(!Objects.equals(usuario.getRolesIdroles().getRol(), "ESTUDIANTE")) throw new RuntimeException("El usuario no es un estudiante");
            Pasantias pasantia = pasantiasDao.findById(pasantiaId).orElseThrow(() -> new RuntimeException("Pasantia no encontrada"));
            Curriculums curriculum = curriculumsDao.findById(idCurriculum).orElseThrow(() -> new RuntimeException("Curriculum no encontrado"));
            Aplicacionespasantias aplicacion = new Aplicacionespasantias();
            aplicacion.setUsuariosIdusuarios(usuario);
            aplicacion.setPasantiasIdpasantias(pasantia);
            aplicacion.setFechaaplicacion(new Date());
            aplicacion.setActivo(false);
            aplicacion.setCurriculumsIdcurriculums(curriculum);
            aplicacion = aplicacionPasantiasDao.save(aplicacion);
            return AplicacionPasantiasDto.fromEntity(aplicacion);
        }catch (Exception e){
            throw new RuntimeException("Error al aplicar a la pasantia",e);
        }
    }
}
