package ucb.edu.bo.internship.internship_backend.api;

import jakarta.ws.rs.core.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucb.edu.bo.internship.internship_backend.bl.PasantiaBl;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasConInstitucionYCarrerasDto;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasConInstitucionYCarrerasDtoYActivo;
import ucb.edu.bo.internship.internship_backend.dto.PasantiasDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pasantia")
public class PasantiaApi {

    private PasantiaBl pasantiaBl;

    public PasantiaApi(PasantiaBl pasantiaBl) {
        this.pasantiaBl = pasantiaBl;
    }

    //Endpoint to get all internships
    @GetMapping()
    public ResponseDto<Page<PasantiasConInstitucionYCarrerasDto>> obtenerTodasPasantias(
        @RequestParam(defaultValue = "0") Integer pagina,
        @RequestParam(defaultValue = "10") Integer tamanio,
        @RequestParam(required = false) String terminoDeBusqueda
    ) {
        ResponseDto<Page<PasantiasConInstitucionYCarrerasDto>> response = new ResponseDto<>();
        Pageable pageable = PageRequest.of(pagina, tamanio);
        Page<PasantiasConInstitucionYCarrerasDto> pasantias;
        try {
            if(terminoDeBusqueda != null){
                pasantias = pasantiaBl.obtenerPasantiasPorTerminoDeBusqueda(terminoDeBusqueda, pageable);
            }else{
                pasantias = pasantiaBl.obtenerTodasPasantias(pageable);
            }
            response.setCode("200");
            response.setResponse(pasantias);
            response.setErrorMessage(null);
            return response;
        }catch (Exception e){
            response.setCode("500");
            response.setResponse(null);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @GetMapping("/institucion/usuario/{uuid}")
    public ResponseDto<List<PasantiasConInstitucionYCarrerasDtoYActivo>> obtenerPasantiasPorUsuario(
            @PathVariable String uuid
    ){
        ResponseDto<List<PasantiasConInstitucionYCarrerasDtoYActivo>> response = new ResponseDto<>();
        List<PasantiasConInstitucionYCarrerasDtoYActivo> pasantias;
        try {
            pasantias = pasantiaBl.obtenerPasantiasPorUsuarioInstitucion(uuid);
            response.setCode("200");
            response.setResponse(pasantias);
            response.setErrorMessage(null);
            return response;
        }catch (Exception e){
            response.setCode("500");
            response.setResponse(null);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }


    @GetMapping("/{id}")
    public ResponseDto<PasantiasDto> obtenerPasantiaPorId(
            @PathVariable Integer id
    ){
        ResponseDto<PasantiasDto> response = new ResponseDto<>();
        PasantiasDto pasantia;
        try {
            pasantia = pasantiaBl.obtenerPasantiaPorId(id);
            response.setCode("200");
            response.setResponse(pasantia);
            response.setErrorMessage(null);
            return response;
        }catch (Exception e){
            response.setCode("500");
            response.setResponse(null);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @GetMapping("/{id}/relacionadas")
    public ResponseDto<List<PasantiasConInstitucionYCarrerasDto>> obtenerPasantiasRelacionadas(
            @PathVariable Integer id
    ){
        ResponseDto<List<PasantiasConInstitucionYCarrerasDto>> response = new ResponseDto<>();
        List<PasantiasConInstitucionYCarrerasDto> pasantias;
        try {
            pasantias = pasantiaBl.obtenerPasantiasRelacionadas(id);
            response.setCode("200");
            response.setResponse(pasantias);
            response.setErrorMessage(null);
            return response;
        }catch (Exception e){
            response.setCode("500");
            response.setResponse(null);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }


}
