package ucb.edu.bo.internship.internship_backend.dto;

import java.sql.Date;
import java.sql.Time;

public class SeleccionAplicanteDto {
    private Integer idSeleccionAplicante;
    private Integer idAplicacionPasantias;
    private Integer idUsuarios;
    private Date fechaSeleccion;
    private Time horaSeleccion;
    private String comentarios;

    public SeleccionAplicanteDto() {
    }

    public SeleccionAplicanteDto(Integer idSeleccionAplicante, Integer idAplicacionPasantias, Integer idUsuarios, Date fechaSeleccion, Time horaSeleccion, String comentarios) {
        this.idSeleccionAplicante = idSeleccionAplicante;
        this.idAplicacionPasantias = idAplicacionPasantias;
        this.idUsuarios = idUsuarios;
        this.fechaSeleccion = fechaSeleccion;
        this.horaSeleccion = horaSeleccion;
        this.comentarios = comentarios;
    }

    public Integer getIdSeleccionAplicante() {
        return this.idSeleccionAplicante;
    }

    public void setIdSeleccionAplicante(Integer idSeleccionAplicante) {
        this.idSeleccionAplicante = idSeleccionAplicante;
    }

    public Integer getIdAplicacionPasantias() {
        return this.idAplicacionPasantias;
    }

    public void setIdAplicacionPasantias(Integer idAplicacionPasantias) {
        this.idAplicacionPasantias = idAplicacionPasantias;
    }

    public Integer getIdUsuarios() {
        return this.idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Date getFechaSeleccion() {
        return this.fechaSeleccion;
    }

    public void setFechaSeleccion(Date fechaSeleccion) {
        this.fechaSeleccion = fechaSeleccion;
    }

    public Time getHoraSeleccion() {
        return this.horaSeleccion;
    }

    public void setHoraSeleccion(Time horaSeleccion) {
        this.horaSeleccion = horaSeleccion;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "{" +
            " idSeleccionAplicante='" + getIdSeleccionAplicante() + "'" +
            ", idAplicacionPasantias='" + getIdAplicacionPasantias() + "'" +
            ", idUsuarios='" + getIdUsuarios() + "'" +
            ", fechaSeleccion='" + getFechaSeleccion() + "'" +
            ", horaSeleccion='" + getHoraSeleccion() + "'" +
            ", comentarios='" + getComentarios() + "'" +
            "}";
    }
    
    
}
