package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;

import ucb.edu.bo.internship.internship_backend.bl.UsuariosBL;
import ucb.edu.bo.internship.internship_backend.dto.*;

import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.dto.SuscribirseInstitucionDto;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.function.Supplier;


@RequestMapping("/api/v1/usuario")
@RestController
public class UsuarioApi {
    private final InstitucionBl institucionBl;
    private final UsuariosBL usuariosBL;
    public UsuarioApi(InstitucionBl institucionBl, UsuariosBL usuariosBL) {
        this.institucionBl = institucionBl;
        this.usuariosBL = usuariosBL;
    }

    @GetMapping("/{uuid}/existencia")
    public ResponseDto<Boolean> usuarioExiste(@PathVariable String uuid){
        ResponseDto<Boolean> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.obtenerUsuario(uuid) != null);
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    //Agregar una institucion
    @PostMapping("/institucion")
    public ResponseEntity<ResponseDto<InstitucionesDto>> agregarInstitucion(@RequestBody InstitucionesDto institucionesDto, @PathVariable String uuid) {
        return handleRequest(() -> institucionBl.agregarInstitucion(institucionesDto));
    }
    //Suscribirse a una institucion
    @PostMapping("/institucion/{id}")
    public ResponseEntity<ResponseDto<InstitucionesDto>> suscribirseEmpresa(@PathVariable Integer id, @PathVariable String uuid, @RequestBody SuscribirseInstitucionDto suscribirseInstitucionDto) {
        return handleRequest(() -> institucionBl.suscribirseEmpresa(id, uuid, suscribirseInstitucionDto));
    }
    //Actualizar una institucion
    @PutMapping("/institucion/{id}")
    public ResponseEntity<ResponseDto<InstitucionesDto>> actualizarInstitucion(@RequestBody InstitucionesDto institucionesDto, @PathVariable Integer id, @PathVariable String uuid) {
        return handleRequest(() -> institucionBl.actualizarInstitucion(uuid, institucionesDto, id));
    }




    //Metodo para manejar las respuestas
    private <T> ResponseEntity<ResponseDto<T>> handleRequest(Supplier<T> supplier) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        try {
            T result = supplier.get();
            responseDto.setResponse(result);
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (UsuarioYaRelacionadoException | InstitucionServiceExcepcion e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InstitucionNotFoundException e) {
            responseDto.setCode("404");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error interno del servidor");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/persona")
    public ResponseDto<PersonasDto> agregarPersona(
            @RequestBody PersonasDto personasDto
            ){
        ResponseDto<PersonasDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarPersona(personasDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

//    @PostMapping()
//    public ResponseDto<UsuariosDto> agregarUsuario(
//            @RequestBody UsuariosDto usuariosDto
//    ){
//        ResponseDto<UsuariosDto> response = new ResponseDto<>();
//        try{
//            response.setResponse(usuariosBL.agregarUsuario(usuariosDto));
//            response.setCode("200");
//            response.setErrorMessage("");
//        }catch (Exception e){
//            response.setCode("500");
//            response.setErrorMessage(e.getMessage());
//        }
//        return response;
//    }

    @PostMapping("/{uuid}/institucion/{institucionId}")
    public ResponseDto<UsuariosInstitucionesDto> agregarUsuarioInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer institucionId,
            @RequestParam String cargo
    ){
        ResponseDto<UsuariosInstitucionesDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarUsuarioInstitucion(uuid, institucionId, cargo));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping()
    public ResponseDto<UsuarioRegistroCompletoDto> agregarUsuarioCompleto(
            @RequestBody UsuarioRegistroCompletoDto usuarioRegistroCompletoDto
    ){
        ResponseDto<UsuarioRegistroCompletoDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarUsuarioCompleto(usuarioRegistroCompletoDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

}
