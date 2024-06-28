package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.bl.EstudianteBl;
import ucb.edu.bo.internship.internship_backend.dto.PersonasDto;
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

    @PutMapping("/{uuid}")
    public ResponseDto<Boolean> updateEstudianteByUuid(
            @PathVariable String uuid,
            @RequestBody PersonasDto personasDto
            ) {
        ResponseDto<Boolean> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.actualizarEstudianteByUuid(uuid, personasDto));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/{uuid}/curriculum")
    public ResponseDto<Boolean> addCurriculum(
            @PathVariable String uuid,
            MultipartFile curriculum
    ) {
        ResponseDto<Boolean> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.agregarCurriculum(uuid, curriculum));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{uuid}/curriculum/{archivo}")
    public ResponseDto<String> getCurriculum(
            @PathVariable String uuid,
            @PathVariable String archivo
    ) {
        ResponseDto<String> response = new ResponseDto<>();
        try {
            response.setResponse(estudianteBl.obtenerCurriculum(uuid, archivo));
            response.setCode("200");
            response.setErrorMessage("");
        }catch (Exception e) {
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }




}
