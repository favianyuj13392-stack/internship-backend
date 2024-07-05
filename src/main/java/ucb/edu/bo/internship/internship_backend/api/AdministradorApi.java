package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.AdministradorBl;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.bl.PasantiaBl;
import ucb.edu.bo.internship.internship_backend.dto.*;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionNotFoundException;
import ucb.edu.bo.internship.internship_backend.exception.institucion.InstitucionServiceExcepcion;
import ucb.edu.bo.internship.internship_backend.exception.institucion.UsuarioYaRelacionadoException;

import java.util.List;
import java.util.function.Supplier;

@RequestMapping("/api/v1/admin/{uuid}")
@RestController
public class AdministradorApi {
    private final AdministradorBl administradorBl;
    private final InstitucionBl institucionBl;
    private final PasantiaBl pasantiaBl;
    public AdministradorApi(AdministradorBl administradorBl, InstitucionBl institucionBl, PasantiaBl pasantiaBl) {
        this.administradorBl = administradorBl;
        this.pasantiaBl = pasantiaBl;
        this.institucionBl = institucionBl;
    }
    //Obtener todas las instituciones
    @GetMapping("/instituciones")
    public ResponseEntity<ResponseDto<Page<InstitucionesDto>>> getInstituciones(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "idinstituciones", required = false) String sort,
            @RequestParam(defaultValue = "", required = false) String active
            ) {
        return handleRequest(() -> institucionBl.obtenerInstituciones(page, size, search, sort,active));
    }
    // Obtener una institución por id
    @GetMapping("/instituciones/{id}")
    public ResponseEntity<ResponseDto<InstitucionConPasantiasDto>> getInstitucionById(@PathVariable String uuid,@PathVariable Integer id) {
        return handleRequest(() -> institucionBl.obtenerInstitucionById(id));
    }
    @GetMapping("/pasantia")
    public ResponseEntity<ResponseDto<Page<PasantiaConNombreYLogoEmpresaDto>>> getPasantias(
            @PathVariable String uuid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "idpasantias", required = false) String sort,
            @RequestParam(defaultValue = "", required = false) String active
    ) {
        return handleRequest(() -> pasantiaBl.obtenerPasantias(page, size, search, sort,active));
    }
    @PutMapping("/instituciones/{idInstituciones}/estado")
    public ResponseEntity<ResponseDto<InstitucionesDto>> cambiarEstadoInstitucion(@PathVariable Integer idInstituciones, @RequestBody ChangeEstadoDto estado, @PathVariable String uuid){
        return handleRequest(() -> administradorBl.cambiarEstadoInstitucion(idInstituciones, estado.getEstado()));
    }
    @GetMapping("/instituciones/{idInstituciones}/usuarios")
    public ResponseEntity<ResponseDto<List<UsuarioConCorreoYNombreCompletoYFotoDto>>> getUsuariosInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer idInstituciones
    ) {
        return handleRequest(() -> administradorBl.obtenerUsuariosInstitucion(idInstituciones));
    }
    @PutMapping("/instituciones/{idInstituciones}/usuarios/{idUsuarios}/aceptar")
    public ResponseEntity<ResponseDto<UsuarioConCorreoYNombreCompletoYFotoDto>> relacionarUsuarioInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer idInstituciones,
            @PathVariable Integer idUsuarios
    ) {
        return handleRequest(() -> administradorBl.relacionarUsuarioInstitucion(idInstituciones, idUsuarios, true));
    }
    @DeleteMapping("/instituciones/{idInstituciones}/usuarios/{idUsuarios}/aceptar")
    public ResponseEntity<ResponseDto<UsuarioConCorreoYNombreCompletoYFotoDto>> desrelacionarUsuarioInstitucion(
            @PathVariable String uuid,
            @PathVariable Integer idInstituciones,
            @PathVariable Integer idUsuarios
    ) {
        return handleRequest(() -> administradorBl.relacionarUsuarioInstitucion(idInstituciones, idUsuarios, false));
    }
    @PutMapping("/pasantia/{idPasantias}/aceptar")
    public ResponseEntity<ResponseDto<PasantiasDto>> aceptarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantias
    ) {
        return handleRequest(() -> administradorBl.aceptarPasantia(idPasantias));
    }
    @DeleteMapping("/pasantia/{idPasantias}/aceptar")
    public ResponseEntity<ResponseDto<PasantiasDto>> rechazarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantias
    ) {
        return handleRequest(() -> administradorBl.rechazarPasantia(idPasantias));
    }

    private <T> ResponseEntity<ResponseDto<T>> handleRequest(Supplier<T> supplier) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        try {
            T result = supplier.get();
            responseDto.setResponse(result);
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InstitucionNotFoundException e) {
            responseDto.setCode("404");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        }catch (UsuarioYaRelacionadoException | InstitucionServiceExcepcion e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            responseDto.setResponse(null);
            responseDto.setCode("500");
            responseDto.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
