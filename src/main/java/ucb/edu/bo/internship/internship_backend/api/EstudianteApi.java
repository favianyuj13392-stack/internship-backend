package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucb.edu.bo.internship.internship_backend.bl.EstudianteBl;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosDto;

@RestController
@RequestMapping("/api/v1/estudiante")
public class EstudianteApi {

    private final EstudianteBl estudianteBl;

    public EstudianteApi(EstudianteBl estudianteBl) {
        this.estudianteBl = estudianteBl;
    }

    @GetMapping("/{uuid}")
    public ResponseDto<UsuariosDto> getEstudianteByUuid(
            @PathVariable String uuid
    ) {
        ResponseDto<UsuariosDto> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.obtenerEstudianteByUuid(uuid));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }


}
