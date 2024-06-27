package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.bl.UsuariosBL;
import ucb.edu.bo.internship.internship_backend.dto.*;

@RequestMapping("/api/v1/usuario")
@RestController
public class UsuarioApi {
    private final InstitucionBl institucionBl;
    private final UsuariosBL usuariosBL;
    public UsuarioApi(InstitucionBl institucionBl, UsuariosBL usuariosBL) {
        this.institucionBl = institucionBl;
        this.usuariosBL = usuariosBL;
    }

    @GetMapping("/{uuid}")
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
    @PostMapping("/{uuid}/institucion")
    public ResponseDto<InstitucionesDto> agregarInstitucion(@RequestBody InstitucionesDto institucionesDto, @PathVariable String uuid){
        ResponseDto<InstitucionesDto> responseDto = new ResponseDto<>();
        try {
            responseDto.setResponse(institucionBl.agregarInstitucion(institucionesDto));
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
        }catch (Exception e){
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al agregar el usuario");
        }
        return responseDto;
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

    @PostMapping()
    public ResponseDto<UsuariosDto> agregarUsuario(
            @RequestBody UsuariosDto usuariosDto
    ){
        ResponseDto<UsuariosDto> response = new ResponseDto<>();
        try{
            response.setResponse(usuariosBL.agregarUsuario(usuariosDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

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
