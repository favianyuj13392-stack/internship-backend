package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

@RequestMapping("/api/v1/usuario/{uuid}")
@RestController
public class UsuarioApi {
    private final InstitucionBl institucionBl;
    public UsuarioApi(InstitucionBl institucionBl) {
        this.institucionBl = institucionBl;
    }
    @PostMapping("/institucion")
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
}
