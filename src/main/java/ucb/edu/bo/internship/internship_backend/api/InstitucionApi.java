package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.InstitucionBl;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionConPasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionNombreDto;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

import java.util.HashMap;
import java.util.List;

@RequestMapping("/api/v1/institucion")
@RestController
public class InstitucionApi {
    private final InstitucionBl institucionBl;
    public InstitucionApi(InstitucionBl institucionBl) {
        this.institucionBl = institucionBl;
    }
    //Obtener todas las instituciones
    @GetMapping
    public ResponseDto<Page<InstitucionesDto>> getInstituciones(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer size,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "idinstituciones", required = false) String sort
    ){
        ResponseDto<Page<InstitucionesDto>> responseDto = new ResponseDto<>();
        try {
            responseDto.setResponse(institucionBl.obtenerInstituciones(page, size, search,sort));
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
        }catch (Exception e){
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al obtener las instituciones");
        }
        return responseDto;
    }
    //Obtener todos los id y nombre de las instituciones activas
    @GetMapping("/nombre")
    public ResponseDto<List<InstitucionNombreDto>> getInstitucionesNombre() {
        ResponseDto<List<InstitucionNombreDto>> responseDto = new ResponseDto<>();
        try {
            responseDto.setResponse(institucionBl.obtenerInstitucionesNombre());
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
        } catch (Exception e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al obtener las instituciones");
        }
        return responseDto;
    }
    //Obtener una institucion por id
    @GetMapping("/{id}")
    public ResponseDto<InstitucionConPasantiasDto> getInstitucionById(@PathVariable Integer id){
        ResponseDto<InstitucionConPasantiasDto> responseDto = new ResponseDto<>();
        try {
            InstitucionConPasantiasDto institucionesDto = institucionBl.obtenerInstitucionById(id);
            responseDto.setResponse(institucionesDto);
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
        }catch (Exception e){
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al obtener la institucion");
        }
        return responseDto;
    }
}
