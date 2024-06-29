package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.dto.SuscribirseInstitucionDto;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.function.Supplier;

@RequestMapping("/api/v1/usuario/{uuid}")
@RestController
public class UsuarioApi {
    private final InstitucionBl institucionBl;
    public UsuarioApi(InstitucionBl institucionBl) {
        this.institucionBl = institucionBl;
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
}
