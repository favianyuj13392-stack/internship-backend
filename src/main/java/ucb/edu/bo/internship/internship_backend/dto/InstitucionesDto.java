package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Instituciones;

public class InstitucionesDto {
    private Integer idInstituciones;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String fotoInstitucion;
    private String correo;
    private Object sectores;
    private String logoEmpresa;
    private Object fotos;
    private Object redesSociales;
    private Boolean activo;

    public InstitucionesDto() {
    }

    public InstitucionesDto(Integer idInstituciones, String nombre, String descripcion, String direccion, String fotoInstitucion, String correo, Object sectores, String logoEmpresa, Object fotos, Object redesSociales,Boolean activo) {
        this.idInstituciones = idInstituciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.fotoInstitucion = fotoInstitucion;
        this.correo = correo;
        this.sectores = sectores;
        this.logoEmpresa = logoEmpresa;
        this.fotos = fotos;
        this.redesSociales = redesSociales;
        this.activo = activo;
    }
    public InstitucionesDto(Instituciones instituciones) {
        this.idInstituciones = instituciones.getIdinstituciones();
        this.nombre = instituciones.getNombre();
        this.descripcion = instituciones.getDescripcion();
        this.direccion = instituciones.getDireccion();
        this.fotoInstitucion = instituciones.getFotoinstitucion();
        this.correo = instituciones.getCorreo();
        this.sectores = instituciones.getSectores();
        this.logoEmpresa = instituciones.getLogoempresa();
        this.fotos = instituciones.getFotos();
        this.redesSociales = instituciones.getRedessociales();
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoInstitucion() {
        return this.fotoInstitucion;
    }

    public void setFotoInstitucion(String fotoInstitucion) {
        this.fotoInstitucion = fotoInstitucion;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Object getSectores() {
        return this.sectores;
    }

    public void setSectores(Object sectores) {
        this.sectores = sectores;
    }

    public String getLogoEmpresa() {
        return this.logoEmpresa;
    }

    public void setLogoEmpresa(String logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    public Object getFotos() {
        return this.fotos;
    }

    public void setFotos(Object fotos) {
        this.fotos = fotos;
    }

    public Object getRedesSociales() {
        return this.redesSociales;
    }

    public void setRedesSociales(Object redesSociales) {
        this.redesSociales = redesSociales;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "{" +
            " idInstituciones='" + getIdInstituciones() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", fotoInstitucion='" + getFotoInstitucion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", sectores='" + getSectores() + "'" +
            ", logoEmpresa='" + getLogoEmpresa() + "'" +
            ", fotos='" + getFotos() + "'" +
            ", redesSociales='" + getRedesSociales() + "'" +
            "}";
    }
    
    
}
