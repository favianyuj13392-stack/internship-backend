package ucb.edu.bo.internship.internship_backend.dto;

public class InstitucionNombreDto {
private Integer idInstituciones;
    private String nombre;

    public InstitucionNombreDto() {
    }

    public InstitucionNombreDto(Integer idInstituciones, String nombre) {
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
    }

    public Integer getIdInstituciones() {
        return this.idInstituciones;
    }

    public void setIdInstituciones(Integer idInstituciones) {
        this.idInstituciones = idInstituciones;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "InstitucionNombreDto{" +
                "idInstituciones=" + idInstituciones +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
