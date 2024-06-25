package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.sql.Date;

public class PasantiasDto {
    private Integer idPasantias;
    private Integer idInstituciones;
    private Object areas;
    private String titulo;
    private String descripcion;
    private Object requisitos;
    private Object funciones;
    private Object beneficios;
    private Date fechaCierre;
    private Date fechaIngreso;

    public PasantiasDto() {
    }

    public PasantiasDto(Integer idPasantias, Integer idInstituciones, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechaCierre, Date fechaIngreso) {
        this.idPasantias = idPasantias;
        this.idInstituciones = idInstituciones;
        this.areas = areas;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.funciones = funciones;
        this.beneficios = beneficios;
        this.fechaCierre = fechaCierre;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdPasantias() {
        return this.idPasantias;
    }

    public void setIdPasantias(Integer idPasantias) {
        this.idPasantias = idPasantias;
    }

    public Integer getIdInstituciones() {
        return this.idInstituciones;
    }

    public void setIdInstituciones(Integer idInstituciones) {
        this.idInstituciones = idInstituciones;
    }

    public Object getAreas() {
        return this.areas;
    }

    public void setAreas(Object areas) {
        this.areas = areas;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getRequisitos() {
        return this.requisitos;
    }

    public void setRequisitos(Object requisitos) {
        this.requisitos = requisitos;
    }

    public Object getFunciones() {
        return this.funciones;
    }

    public void setFunciones(Object funciones) {
        this.funciones = funciones;
    }

    public Object getBeneficios() {
        return this.beneficios;
    }

    public void setBeneficios(Object beneficios) {
        this.beneficios = beneficios;
    }

    public Date getFechaCierre() {
        return this.fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return "{" +
            " idPasantias='" + getIdPasantias() + "'" +
            ", idInstituciones='" + getIdInstituciones() + "'" +
            ", areas='" + getAreas() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", requisitos='" + getRequisitos() + "'" +
            ", funciones='" + getFunciones() + "'" +
            ", beneficios='" + getBeneficios() + "'" +
            ", fechaCierre='" + getFechaCierre() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            "}";
    }

    public static PasantiasDto fromEntity(Pasantias pasantias) {
        return new PasantiasDto(
            pasantias.getIdpasantias(),
            pasantias.getInstitucionesIdinstituciones().getIdinstituciones(),
            pasantias.getAreas(),
            pasantias.getTitulo(),
            pasantias.getDescripcion(),
            pasantias.getRequisitos(),
            pasantias.getFunciones(),
            pasantias.getBeneficios(),
            new Date (pasantias.getFechacierre().getTime()),
            new Date (pasantias.getFechaingreso().getTime())
        );
    }
}
