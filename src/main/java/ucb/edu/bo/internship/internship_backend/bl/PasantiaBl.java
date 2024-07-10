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
import ucb.edu.bo.internship.internship_backend.dao.*;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PasantiaBl {
    private final PasantiasDao pasantiasDao;
    private final PasantiasCarrerasDao pasantiasCarrerasDao;
    private final UsuariosDao usuariosDao;
    private final AplicacionPasantiasDao aplicacionPasantiasDao;
    private final SeleccionAplicanteDao seleccionAplicanteDao;

    public PasantiaBl(PasantiasDao pasantiasDao, PasantiasCarrerasDao pasantiasCarrerasDao,UsuariosDao usuariosDao, AplicacionPasantiasDao aplicacionPasantiasDao, SeleccionAplicanteDao seleccionAplicanteDao) {
        this.pasantiasDao = pasantiasDao;
        this.pasantiasCarrerasDao = pasantiasCarrerasDao;
        this.usuariosDao = usuariosDao;
        this.aplicacionPasantiasDao = aplicacionPasantiasDao;
        this.seleccionAplicanteDao = seleccionAplicanteDao;
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
        List<String> areas, beneficios, funciones, requisitos;
        try{
            List<String> areasJson, beneficiosJson, funcionesJson, requisitosJson;
            areasJson = pasantias1.getAreas();
            beneficiosJson = pasantias1.getBeneficios();
            funcionesJson = pasantias1.getFunciones();
            requisitosJson = pasantias1.getRequisitos();
            areas = areasJson;
            beneficios = beneficiosJson;
            funciones = funcionesJson;
            requisitos = requisitosJson;
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

    private PasantiasConInstitucionYCarrerasDtoYActivo toPasantiasConInstitucionYCarrerasDtoYActivo(Pasantias pasantias1) {
        List<String> areas, beneficios, funciones, requisitos;
        try{
            List<String> areasJson, beneficiosJson, funcionesJson, requisitosJson;
            areasJson = pasantias1.getAreas();
            beneficiosJson = pasantias1.getBeneficios();
            funcionesJson = pasantias1.getFunciones();
            requisitosJson = pasantias1.getRequisitos();
            areas = areasJson;
            beneficios = beneficiosJson;
            funciones = funcionesJson;
            requisitos = requisitosJson;
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
        return new PasantiasConInstitucionYCarrerasDtoYActivo(pasantiasDto, institucionesDto, carrerasDto, pasantias1.getActivo());
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

    public List<PasantiasConInstitucionYCarrerasDtoYActivo> obtenerPasantiasPorUsuarioInstitucion(String uuid) {
        List<Pasantias> pasantias = pasantiasDao.findPasantiasByInstitucionesIdinstitucionesUsuarioUUID(uuid);
        
        return pasantias.stream().map(this::toPasantiasConInstitucionYCarrerasDtoYActivo).toList();
    }

    public PasantiaConEmpresaYPostulantes obtenerPasantia(Integer idPasantia) {
        try{
            Pasantias pasantias = pasantiasDao.findById(idPasantia).orElse(null);
            if(pasantias == null){
                throw new RuntimeException("Pasantia no encontrada");
            }
            PasantiasDto pasantiasDto = PasantiasDto.fromEntity(pasantias);
            pasantiasDto.setAreas(pasantias.getAreas());
            pasantiasDto.setBeneficios(pasantias.getBeneficios());
            pasantiasDto.setFunciones(pasantias.getFunciones());
            pasantiasDto.setRequisitos(pasantias.getRequisitos());
            Boolean estadoPasantia = pasantias.getActivo();
            List<Aplicacionespasantias> aplicacionespasantias = pasantias.getAplicacionespasantiasList();
            //Obtener las personas postulantes
            List<PersonasDto> personasDto = new ArrayList<>();
            List<AplicacionPasantiasDto> aplicacionPasantiasDto = new ArrayList<>();
            for (Aplicacionespasantias aplicacionespasantias1 : aplicacionespasantias) {
                aplicacionPasantiasDto.add(AplicacionPasantiasDto.fromEntity(aplicacionespasantias1));
                personasDto.add(PersonasDto.fromEntity(aplicacionespasantias1.getUsuariosIdusuarios().getPersonasIdpersonas()));
            }
            InstitucionesDto institucionesDto = InstitucionesDto.fromEntity(pasantias.getInstitucionesIdinstituciones());
            return new PasantiaConEmpresaYPostulantes(pasantiasDto,estadoPasantia,personasDto,institucionesDto,aplicacionPasantiasDto);
        }catch (Exception e){
            throw new RuntimeException("Error al obtener la pasantia",e);
        }
    }

    public PasantiaConPostulantesDto obtenerPasantiaDetalle(String uuid, Integer idPasantia) {
        try{
            if(!validarRelacionInstitucionUsuario(uuid, idPasantia)) throw new RuntimeException("No tiene permisos para ver esta pasantía");
            //Obtener Pasantia
            Pasantias pasantia = pasantiasDao.findById(idPasantia).orElse(null);
            if(pasantia == null) throw new RuntimeException("No se encontró la pasantía");
            //Obtener todos los datos de la pasantia
            PasantiasDto pasantiasDto = PasantiasDto.fromEntity(pasantia);
            pasantiasDto.setAreas(pasantia.getAreas());
            pasantiasDto.setBeneficios(pasantia.getBeneficios());
            pasantiasDto.setFunciones(pasantia.getFunciones());
            pasantiasDto.setRequisitos(pasantia.getRequisitos());

            //Obtener los postulantes
            List<Aplicacionespasantias> aplicacionespasantias = pasantia.getAplicacionespasantiasList();
            List<UsuarioCompletoDto> usuarioCompletoDtos = new ArrayList<>();
            for (Aplicacionespasantias aplicacionespasantias1 : aplicacionespasantias) {
                UsuarioCompletoDto usuarioCompletoDto = UsuarioCompletoDto.fromEntity(aplicacionespasantias1);
                usuarioCompletoDto.getUsuario().setKc_UUID(null);
                usuarioCompletoDtos.add(usuarioCompletoDto);
            }
            InstitucionesDto institucionesDto = InstitucionesDto.fromEntity(pasantia.getInstitucionesIdinstituciones());
            return new PasantiaConPostulantesDto(pasantiasDto, usuarioCompletoDtos, institucionesDto, pasantia.getActivo());
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al obtener la pasantía", e);
        }
    }

    public SeleccionAplicanteDto aceptarAplicacionPasantia(String uuid, Integer idPasantia, Integer idAplicacionPasantia, String comentarios) {
        try {
            if (!validarRelacionInstitucionUsuario(uuid, idPasantia)) throw new RuntimeException("No tiene permisos para ver esta pasantía");

            //Obtener la aplicación
            Aplicacionespasantias aplicacionespasantias = aplicacionPasantiasDao.findById(idAplicacionPasantia).orElse(null);
            if (aplicacionespasantias == null) throw new RuntimeException("No se encontró la aplicación");

            //Cambiar el estado de la aplicación
            aplicacionespasantias.setActivo(true);
            aplicacionespasantias = aplicacionPasantiasDao.save(aplicacionespasantias);

            //Crear una nueva Seleccion aplicante
            Seleccionaplicante seleccionaplicante = new Seleccionaplicante();
            seleccionaplicante.setActivo(true);
            seleccionaplicante.setComentarios(comentarios);
            seleccionaplicante.setFechaseleccion(new Date());
            seleccionaplicante.setHoraseleccion(new Date());
            seleccionaplicante.setAplicacionespasantiasIdaplicacionpasantias(aplicacionespasantias);
            seleccionaplicante.setUsuariosinstitucionesIdusuariosinstituciones(usuariosDao.findByKcUuid(uuid).getUsuariosinstitucionesList().get(0));
            seleccionaplicante = seleccionAplicanteDao.save(seleccionaplicante);
            return SeleccionAplicanteDto.fromEntity(seleccionaplicante);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al aceptar la aplicación", e);
        }
    }

    public AplicacionPasantiasDto rechazarAplicacionPasantia(String uuid, Integer idPasantia, Integer idAplicacionPasantia) {
        try {
            if (!validarRelacionInstitucionUsuario(uuid, idPasantia)) throw new RuntimeException("No tiene permisos para ver esta pasantía");

            //Obtener la aplicación
            Aplicacionespasantias aplicacionespasantias = aplicacionPasantiasDao.findById(idAplicacionPasantia).orElse(null);
            if (aplicacionespasantias == null) throw new RuntimeException("No se encontró la aplicación");

            //Eliminar la aplicación
            aplicacionPasantiasDao.delete(aplicacionespasantias);
            return new AplicacionPasantiasDto();
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al rechazar la aplicación", e);
        }
    }

    public SeleccionAplicanteDto finalizarPasantiaSinSeleccion(String uuid, Integer idPasantia) {
        try {
            if (!validarRelacionInstitucionUsuario(uuid, idPasantia)) throw new RuntimeException("No tiene permisos para ver esta pasantía");

            //Obtener Pasantia
            Pasantias pasantia = pasantiasDao.findById(idPasantia).orElse(null);
            if (pasantia == null) throw new RuntimeException("No se encontró la pasantía");

            //Obtener las aplicaciones y validar que no haya aplicantes
            List<Aplicacionespasantias> aplicacionespasantias = pasantia.getAplicacionespasantiasList();
            if (!aplicacionespasantias.isEmpty()) throw new RuntimeException("No se puede finalizar la pasantía porque hay aplicantes, por favor seleccione a un aplicante o rechace todas las aplicaciones");

            //Crear una nueva Seleccion aplicante
            Seleccionaplicante seleccionaplicante = new Seleccionaplicante();
            seleccionaplicante.setActivo(false);
            seleccionaplicante.setComentarios("No se seleccionó a ningún aplicante");
            seleccionaplicante.setFechaseleccion(new Date());
            seleccionaplicante.setHoraseleccion(new Date());
            seleccionaplicante.setAplicacionespasantiasIdaplicacionpasantias(null);
            seleccionaplicante.setUsuariosinstitucionesIdusuariosinstituciones(usuariosDao.findByKcUuid(uuid).getUsuariosinstitucionesList().get(0));
            seleccionaplicante = seleccionAplicanteDao.save(seleccionaplicante);
            return SeleccionAplicanteDto.fromEntity(seleccionaplicante);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al finalizar la pasantía", e);
        }
    }
    private Boolean validarRelacionInstitucionUsuario(String uuid, Integer idPasantia) {
        //Obtener Pasantia
        Pasantias pasantia = pasantiasDao.findById(idPasantia).orElse(null);
        if(pasantia == null) throw new RuntimeException("No se encontró la pasantía");

        //Obtener Usuario
        Usuarios usuario = usuariosDao.findByKcUuid(uuid);
        if(usuario == null) throw new RuntimeException("No se encontró el usuario");

        //Validar que la pasantía y el usuario pertenezcan a la misma empresa
        Instituciones institucionPasantia = pasantia.getInstitucionesIdinstituciones();
        Instituciones institucionUsuario = usuario.getUsuariosinstitucionesList().get(0).getInstitucionesIdinstituciones();

        return Objects.equals(institucionUsuario.getIdinstituciones(), institucionPasantia.getIdinstituciones());
    }
}
