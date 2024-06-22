package ucb.edu.bo.internship.internship_backend.dto;

import java.sql.Date;
import java.sql.Time;

public class UsuariosDto {
    private Integer idUsuarios;
    private String kc_UUID;
    private String correo;
    private Date fechaRegistro;
    private Time horaRegistro;
    private Integer idRoles;
    private Integer idPersonas;
    private Integer idCarreras;

    public UsuariosDto() {
    }

    public UsuariosDto(Integer idUsuarios, String kc_UUID, String correo, Date fechaRegistro, Time horaRegistro, Integer idRoles, Integer idPersonas, Integer idCarreras) {
        this.idUsuarios = idUsuarios;
        this.kc_UUID = kc_UUID;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.horaRegistro = horaRegistro;
        this.idRoles = idRoles;
        this.idPersonas = idPersonas;
        this.idCarreras = idCarreras;
    }

    public Integer getIdUsuarios() {
        return this.idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getKc_UUID() {
        return this.kc_UUID;
    }

    public void setKc_UUID(String kc_UUID) {
        this.kc_UUID = kc_UUID;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Time getHoraRegistro() {
        return this.horaRegistro;
    }

    public void setHoraRegistro(Time horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public Integer getIdRoles() {
        return this.idRoles;
    }

    public void setIdRoles(Integer idRoles) {
        this.idRoles = idRoles;
    }

    public Integer getIdPersonas() {
        return this.idPersonas;
    }

    public void setIdPersonas(Integer idPersonas) {
        this.idPersonas = idPersonas;
    }


    public Integer getIdCarreras() {
        return this.idCarreras;
    }

    public void setIdCarreras(Integer idCarreras) {
        this.idCarreras = idCarreras;
    }

    @Override
    public String toString() {
        return "{" +
            " idUsuarios='" + getIdUsuarios() + "'" +
            ", kc_UUID='" + getKc_UUID() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", horaRegistro='" + getHoraRegistro() + "'" +
            ", idRoles='" + getIdRoles() + "'" +
            ", idPersonas='" + getIdPersonas() + "'" +
            ", idCarreras='" + getIdCarreras() + "'" +
            "}";
    }

    


    
}
