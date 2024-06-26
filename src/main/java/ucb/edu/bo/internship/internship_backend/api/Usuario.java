package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.bl.UsuariosBL;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

@RequestMapping("/api/v1/usuario")
@RestController
public class Usuario {
    private final InstitucionBl institucionBl;
    public Usuario(InstitucionBl institucionBl) {
        this.institucionBl = institucionBl;
    }
    @PostMapping()
    public ResponseDto<InstitucionesDto> agregarUsuario(@RequestBody InstitucionesDto institucionesDto){
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
}
