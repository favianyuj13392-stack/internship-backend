package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Instituciones;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class InstitucionConPasantiasDto extends InstitucionesDto {
    private List<PasantiaNombreDto> pasantias;

    public InstitucionConPasantiasDto(List<PasantiaNombreDto> pasantias) {
        this.pasantias = pasantias;
    }

    public InstitucionConPasantiasDto(Integer idInstituciones, String nombre, String descripcion, String direccion, String fotoInstitucion, String correo, List<String> sectores, String logoEmpresa, List<String> fotos, LinkedHashMap<String, String> redesSociales, Boolean activo, List<PasantiaNombreDto> pasantias) {
        super(idInstituciones, nombre, descripcion, direccion, fotoInstitucion, correo, sectores, logoEmpresa, fotos, redesSociales, activo);
        this.pasantias = pasantias;
    }

    public InstitucionConPasantiasDto(Instituciones instituciones, List<PasantiaNombreDto> pasantias) {
        super(instituciones);
        this.pasantias = pasantias;
    }

    public List<PasantiaNombreDto> getPasantias() {
        return pasantias;
    }

    public void setPasantias(List<PasantiaNombreDto> pasantias) {
        this.pasantias = pasantias;
    }

    @Override
    public String toString() {
        return "InstitucionConPasantiasDto{" +
                "pasantias=" + pasantias +
                '}';
    }
}
