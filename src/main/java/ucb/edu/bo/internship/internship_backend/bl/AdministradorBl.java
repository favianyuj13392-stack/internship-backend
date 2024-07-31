package ucb.edu.bo.internship.internship_backend.bl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;
import ucb.edu.bo.internship.internship_backend.service.impl.KeycloakServiceImpl;

import ucb.edu.bo.internship.internship_backend.service.EmailService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AdministradorBl {
    private final InstitucionesDao institucionesDao;
    private final UsuariosInstitucionesDao usuariosInstitucionesDao;
    private final PasantiasDao pasantiasDao;
    private final UsuariosDao usuariosDao;
    private final PersonasDao personasDao;
    private final AplicacionPasantiasDao aplicacionPasantiasDao;
    private final CurriculumsDao curriculumsDao;
    private final KeycloakServiceImpl keycloakService;

    private final EmailService emailService;
    private final RolesDao rolesDao;

    public AdministradorBl(InstitucionesDao institucionesDao, UsuariosInstitucionesDao usuariosInstitucionesDao, PasantiasDao pasantiasDao, UsuariosDao usuariosDao, PersonasDao personasDao, AplicacionPasantiasDao aplicacionPasantiasDao, EmailService emailService, RolesDao rolesDao, CurriculumsDao curriculumsDao, KeycloakServiceImpl keycloakService) {
        this.institucionesDao = institucionesDao;
        this.usuariosInstitucionesDao = usuariosInstitucionesDao;
        this.pasantiasDao = pasantiasDao;
        this.usuariosDao = usuariosDao;
        this.personasDao = personasDao;
        this.aplicacionPasantiasDao = aplicacionPasantiasDao;
        this.emailService = emailService;
        this.rolesDao = rolesDao;
        this.curriculumsDao = curriculumsDao;
        this.keycloakService = keycloakService;
    }

    public InstitucionesDto cambiarEstadoInstitucion(Integer idInstituciones, Boolean estado) {
        try {
            Instituciones instituciones = institucionesDao.findById(idInstituciones).orElse(null);
            if (instituciones != null) {
                instituciones.setActivo(estado);
                instituciones = institucionesDao.save(instituciones);
                return new InstitucionesDto(instituciones);
            }else{
                throw new InstitucionNotFoundException("Institucion no encontrada");
            }
        }catch (InstitucionNotFoundException e){
            throw new InstitucionNotFoundException("Institucion no encontrada");
        }catch (Exception e){
            throw new RuntimeException("Error al cambiar el estado de la institucion",e);
        }
    }

    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerUsuariosInstitucion(Integer idInstituciones) {
        try {
            return institucionesDao.findById(idInstituciones)
                    .orElseThrow(() -> new InstitucionNotFoundException("Institucion no encontrada"))
                    .getUsuariosinstitucionesList()
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (InstitucionNotFoundException e) {
            throw new InstitucionNotFoundException("Institucion no encontrada");
        } catch (Exception e) {
            throw new InstitucionServiceExcepcion("Error al obtener los usuarios de la institucion", e);
        }
    }
    @Transactional
    public UsuarioConCorreoYNombreCompletoYFotoDto relacionarUsuarioInstitucion(Integer idInstituciones, Integer idUsuariosInstituciones, Integer idUsuarios, Boolean estado) {
        try {
            Usuariosinstituciones usuariosInstituciones = usuariosInstitucionesDao.findById(idUsuariosInstituciones).orElse(null);
            if(usuariosInstituciones == null){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            if(!estado){
                //Borrar la relacion y los datos asociados
                Usuarios usuarios = usuariosInstituciones.getUsuariosIdusuarios();
                Personas personas = usuarios.getPersonasIdpersonas();
                Instituciones instituciones = usuariosInstituciones.getInstitucionesIdinstituciones();
                usuariosInstitucionesDao.delete(usuariosInstituciones);
                if(!instituciones.getActivo()){
                    institucionesDao.delete(instituciones);
                }

                EmailRequest emailRequest = new EmailRequest();

                emailRequest.setTo(
                        usuariosInstituciones.getUsuariosIdusuarios().getCorreo()
                );

                emailRequest.setSubject(
                        "Rechazo de su solicitud de cuenta de empresa en nuestro sistema de pasantías"
                );

                emailRequest.setBody(
                        "<p>Estimado/a "+usuarios.getPersonasIdpersonas().getNombres()+",</p>" +
                                "<p>Lamentamos informarle que su solicitud para registrar la empresa <strong>"+instituciones.getNombre()+"</strong> en nuestro sistema de pasantías no ha sido aceptada.</p>" +
                                "<p>Le agradecemos por su interés en colaborar con nosotros. Le invitamos a volver a postularse en el futuro o a ponerse en contacto con nosotros para discutir cualquier inquietud.</p>" +
                                "<p>Si tiene preguntas o desea más detalles sobre esta decisión, no dude en comunicarse con nuestro equipo de soporte.</p>"
                );

                emailService.enviarCorreo(emailRequest);

                usuariosDao.delete(usuarios);
                personasDao.delete(personas);

                keycloakService.deleteUser(usuarios.getKcUuid());    


                return new UsuarioConCorreoYNombreCompletoYFotoDto();
            }
            //Aceptar la relacion
            usuariosInstituciones.getUsuariosIdusuarios().setActivo(true);
            usuariosInstituciones.getInstitucionesIdinstituciones().setActivo(true);
            usuariosInstituciones.setActivo(true);
            usuariosInstituciones = usuariosInstitucionesDao.save(usuariosInstituciones);

            EmailRequest emailRequest = new EmailRequest();

            emailRequest.setTo(
                    usuariosInstituciones.getUsuariosIdusuarios().getCorreo()
            );

            emailRequest.setSubject(
                    "Aceptación de su cuenta de empresa en nuestro sistema de pasantías"
            );

            emailRequest.setBody(
                    "<p>Estimado/a {{nombre_contacto}},</p>" +
                            "<p>Nos complace informarle que su solicitud para registrar la empresa <strong>"+usuariosInstituciones.getInstitucionesIdinstituciones().getNombre()+"</strong> en nuestro sistema de pasantías ha sido aceptada.</p>" +
                            "<p>A partir de ahora, puede acceder a nuestra plataforma y comenzar a publicar oportunidades de pasantía. Estamos entusiasmados de colaborar con usted y de ofrecer a nuestros estudiantes valiosas experiencias laborales en su empresa.</p>" +
                            "<p>Si tiene alguna pregunta o necesita asistencia adicional, no dude en ponerse en contacto con nuestro equipo de soporte.</p>"
            );

            emailService.enviarCorreo(emailRequest);

            return new UsuarioConCorreoYNombreCompletoYFotoDto(usuariosInstituciones);
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al relacionar el usuario con la institucion",e);
        }
    }

    public PasantiasDto aceptarPasantia(Integer idPasantias) {
        try {
            Pasantias pasantias = pasantiasDao.findById(idPasantias).orElseThrow(() -> new InstitucionNotFoundException("Pasantia no encontrada"));
            if(pasantias.getActivo()){
                throw new UsuarioYaRelacionadoException("No se puede aceptar una pasantia activa");
            }
            pasantias.setActivo(true);
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(
                    pasantias.getUsuariosIdusuarios().getCorreo()
            );
            emailRequest.setSubject(
                    "Su solicitud de publicación de una pasantía ha sido aceptada"
            );
            emailRequest.setBody(
                    "<p>Estimado/a"+pasantias.getUsuariosIdusuarios().getPersonasIdpersonas().getNombres()+",</p>" +
                            "<p>Nos complace informarle que su solicitud de publicación para la pasantía titulada <strong>" + pasantias.getTitulo() + "</strong> ha sido aceptada.</p>" +
                            "<p>Agradecemos tu interés en contribuir a la formación profesional de nuestros estudiantes. Si tienes alguna pregunta o necesitas más detalles, no dudes en ponerte en contacto con nosotros.</p>"+
                            "<p>Para más información, visita nuestro sitio web o ponte en contacto con nuestro equipo.</p>"+
                            "<p>¡Felicitaciones y mucho éxito!</p>"
            );
            emailService.enviarCorreo(emailRequest);

            EmailRequestMassive emailRequestMassive = new EmailRequestMassive();
            Roles rol = rolesDao.findByRol("ESTUDIANTE");
            List<Usuarios> estudiantes = usuariosDao.findAllByActivoIsTrueAndRolesIdrolesAndCarrerasIdcarrerasIn(
                    rol, pasantias.getPasantiascarrerasList().stream().map(
                            Pasantiascarreras::getCarrerasIdcarreras
                    ).toList()
            );
            List<String> correosEstudiantes = estudiantes.stream().map(
                    Usuarios::getCorreo
            ).toList();

            emailRequestMassive.setTo(correosEstudiantes);
            emailRequestMassive.setSubject(
                    "Nueva oportunidad de pasantía relacionada con tu carrera"
            );
            emailRequestMassive.setBody(
                    "<p>Estimado/a estudiante,</p>" +
                            "<p>Nos complace anunciar que hemos publicado una nueva oportunidad de pasantía relacionada con tu carrera. Esta es una excelente oportunidad para aplicar tus conocimientos y adquirir experiencia práctica en tu área de estudio.</p>" +
                            "<p>Detalles de la pasantía:</p>" +
                            "<ul>" +
                            "    <li><strong>Título:</strong>"+ pasantias.getTitulo() +"</li>" +
                            "    <li><strong>Descripción:</strong>"+ pasantias.getDescripcion() +"</li>" +
                            "    <li><strong>Empresa:</strong>"+pasantias.getInstitucionesIdinstituciones().getNombre()+"</li>" +
                            "    <li><strong>Fecha de inicio:</strong>"+pasantias.getFechaingreso()+"</li>" +
                            "    <li><strong>Fecha límite para postular:</strong>"+pasantias.getFechacierre()+"</li>" +
                            "</ul>" +
                            "<p>Si estás interesado/a en esta oportunidad, te invitamos a postularte a través de nuestro sistema de pasantías. No pierdas la oportunidad de enriquecer tu formación profesional y abrir puertas a futuras oportunidades laborales.</p>" +
                            "<p>Para más información y para postularte, visita nuestro sitio web o contacta a nuestro equipo de pasantías.</p>"
            );

            emailService.enviarCorreoMasivo(emailRequestMassive);

            pasantias = pasantiasDao.save(pasantias);
            return new PasantiasDto(pasantias);
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e ){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al aceptar la pasantia",e);
        }
    }

    public PasantiasDto rechazarPasantia(Integer idPasantias) {
        try {
            Pasantias pasantias = pasantiasDao.findById(idPasantias).orElseThrow(() -> new InstitucionNotFoundException("Pasantia no encontrada"));
            if(pasantias.getActivo()){
                throw new UsuarioYaRelacionadoException("No se puede eliminar una pasantia activa");
            }
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(
                    pasantias.getUsuariosIdusuarios().getCorreo()
            );
            emailRequest.setSubject(
                    "Su solicitud de publicación de una pasantía ha sido rechazada"
            );
            emailRequest.setBody(
                    "<p>Estimado/a " + pasantias.getUsuariosIdusuarios().getPersonasIdpersonas().getNombres() + ",</p>" +
                            "<p>Lamentablemente, debemos informarle que su solicitud de publicación para la pasantía titulada <strong>" + pasantias.getTitulo() + "</strong> ha sido rechazada.</p>" +
                            "<p>Agradecemos su interés en contribuir a la formación profesional de nuestros estudiantes. Aunque esta vez no hemos podido aceptar su solicitud, le animamos a seguir participando en futuras oportunidades.</p>" +
                            "<p>Si tiene alguna pregunta o necesita más detalles, no dude en ponerse en contacto con nosotros.</p>"
            );
            emailService.enviarCorreo(emailRequest);
            pasantiasDao.delete(pasantias);
            return new PasantiasDto();
        }catch (InstitucionNotFoundException | UsuarioYaRelacionadoException e ){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error al rechazar la pasantia",e);
        }
    }

    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerSuscripcionAInstituciones(String nombreInstitucion){
        try {
            if(nombreInstitucion.isBlank()){
                return usuariosInstitucionesDao.findByActivoFalse()
                        .stream()
                        .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                        .toList();
            }
            return usuariosInstitucionesDao.findByInstitucionesIdinstitucionesNombreContainsAndActivoFalse(nombreInstitucion)
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener las suscripciones a las instituciones",e);
        }
    }

    public SolicitudIndormacionDto obtenerTodaLaInformacionDeSolicitud(Integer idInstitucion, Integer idSolicitud, Integer idUsuario) {
        try {
            Usuariosinstituciones usuariosinstituciones = usuariosInstitucionesDao.findById(idSolicitud).orElse(null);
            if(usuariosinstituciones == null){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            if(usuariosinstituciones.getActivo()){
                throw new InstitucionNotFoundException("La relacion entre el usuario y la institucion esta activa, no se puede obtener la informacion");
            }
            Usuarios usuarios = usuariosDao.findById(idUsuario).orElse(null);
            if (usuarios == null) throw new InstitucionNotFoundException("Usuario no encontrado");
            Instituciones instituciones = institucionesDao.findById(idInstitucion).orElse(null);
            if (instituciones == null) throw new InstitucionNotFoundException("Institucion no encontrada");
            if(usuariosinstituciones.getUsuariosIdusuarios().equals(usuarios) && usuariosinstituciones.getInstitucionesIdinstituciones().equals(instituciones)){
                throw new InstitucionNotFoundException("No se encontro la relacion entre el usuario y la institucion");
            }
            return new SolicitudIndormacionDto(UsuariosDto.fromEntityInstitucion(usuarios),PersonasDto.fromEntity(usuarios.getPersonasIdpersonas()),InstitucionesDto.fromEntity(instituciones),UsuariosInstitucionesDto.fromEntity(usuariosinstituciones));
        }catch (InstitucionNotFoundException e){
            throw e;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener la informacion de la solicitud", e);
        }
    }
    public List<UsuarioConCorreoYNombreCompletoYFotoDto> obtenerSuscripcionAInstitucion(Integer idInstitucion) {
        try {
            return usuariosInstitucionesDao.findByInstitucionesIdinstitucionesIdinstitucionesAndActivoTrue(idInstitucion)
                    .stream()
                    .map(UsuarioConCorreoYNombreCompletoYFotoDto::new)
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener las suscripciones a la institucion",e);
        }
    }

    public AplicacionPasantiasDto obtenerSolicitudDeAplicacion(Integer idSolicitud) {
        try {
            Aplicacionespasantias aplicacionespasantias = aplicacionPasantiasDao.findById(idSolicitud).orElseThrow(() -> new RuntimeException("Solicitud de aplicacion no encontrada"));
            return AplicacionPasantiasDto.fromEntity(aplicacionespasantias);
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener la informacion de la solicitud", e);
        }
    }
    public KPISDto getAllKPIS(
            //Estudiantes
            Integer idCarreraEstudiante,
            String fechaInicioEstudiante,
            String fechaFinEstudiante,
            //Pasantias
            Integer idCarreraPasantia,
            Integer idEmpresaPasantia,
            String sectorPasantia,
            String areaPasantia,
            //Aplicaciones
            Integer idCarreraAplicacion,
            Integer idInstitucionAplicacion,
            String sectorAplicacion,
            String areaAplicacion,
            //Instituciones
            String sectorInstitucion,
            //Usuarios
            Integer idEmpresaUsuarios
    ){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedFechaInicio = null;
        Date parsedFechaFin = null;
        try {

            parsedFechaInicio = format.parse(fechaInicioEstudiante);
            parsedFechaFin = format.parse(fechaFinEstudiante);
            return new KPISDto(
                    usuariosDao.countEstudiantes().orElse(0L),//LISTO
                    usuariosDao.countEstudiantesPorCarrera(idCarreraEstudiante).orElse(0L),//LISTO
                    usuariosDao.countEstudiantesPorFecha(parsedFechaInicio,parsedFechaFin).orElse(0L),//LISTO
                    pasantiasDao.countPasantiasActivo(true).orElse(0L),//LISTO
                    pasantiasDao.countPasantiasActivo(false).orElse(0L),//LISTO
                    pasantiasDao.countPasantiasPorCarrera(idCarreraPasantia).orElse(0L),// PENDIENTE
                    pasantiasDao.countPasantiasPorEmpresa(idEmpresaPasantia).orElse(0L),//LISTO
                    pasantiasDao.countPasantiasPorSector(sectorPasantia).orElse(0L),//LISTO
                    pasantiasDao.countPasantiasPorArea(areaPasantia).orElse(0L),//LISTO
                    aplicacionPasantiasDao.countByPasantiasIdpasantias().orElse(0L),//LISTO
                    aplicacionPasantiasDao.countByPasantiasIdpasantiasByCarrera(idCarreraAplicacion).orElse(0L),//PENDIENTE
                    aplicacionPasantiasDao.countByPasantiasIdpasantiasByEmpresa(idInstitucionAplicacion).orElse(0L),//LISTO
                    aplicacionPasantiasDao.countByPasantiasIdpasantiasBySector(sectorAplicacion).orElse(0L),//LISTO
                    aplicacionPasantiasDao.countByPasantiasIdpasantiasByArea(areaAplicacion).orElse(0L),//LISTO
                    aplicacionPasantiasDao.countByActivo(true).orElse(0L),//LISTO
                    pasantiasDao.countPasantiasQueNoAceptaronEstudiantes().orElse(0L),//LISTO
                    curriculumsDao.count(),
                    curriculumsDao.promedioCurriculumsPorEstudiante(1).orElse(0.0),//LISTO
                    institucionesDao.countAllByActivo(),//Listo
                    institucionesDao.countAllBySector(sectorInstitucion).orElse(0L),//Listo
                    usuariosDao.countUsuariosEmpresa().orElse(0L),
                    usuariosInstitucionesDao.countUsuariosPorEmpresa(idEmpresaUsuarios).orElse(0L)
            );
        }catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error al obtener los KPIs", e);
        }
    }
}
