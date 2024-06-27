package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucb.edu.bo.internship.internship_backend.bl.PaginaInicioBl;
import ucb.edu.bo.internship.internship_backend.dto.RecuentoPaginaInicioDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;

@RequestMapping("/api/v1/inicio")
@RestController
public class PaginaInicioApi {
    private final PaginaInicioBl paginaInicioBl;
    public PaginaInicioApi(PaginaInicioBl paginaInicioBl) {
        this.paginaInicioBl = paginaInicioBl;
    }
    @GetMapping("/recuento")
    public ResponseDto<RecuentoPaginaInicioDto> getRecuento(){
        ResponseDto<RecuentoPaginaInicioDto> responseDto = new ResponseDto<>();
        try {
            RecuentoPaginaInicioDto recuentoPaginaInicioDto = paginaInicioBl.obtenerRecuentoInstituciones();
            responseDto.setResponse(recuentoPaginaInicioDto);
            responseDto.setCode("200");
            responseDto.setErrorMessage("");
        }catch (Exception e){
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al obtener el recuento");
        }
        return responseDto;
    }
}
