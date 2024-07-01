package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones;

public class UsuarioConCorreoYNombreCompletoYFotoDto {
    private Integer idUsuarios;
    private Integer idPersona;
    private String correo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fotoPerfil;

    public UsuarioConCorreoYNombreCompletoYFotoDto(Integer idUsuarios, Integer idPersona, String correo, String nombre, String apellidoPaterno, String apellidoMaterno, String fotoPerfil) {
        this.idUsuarios = idUsuarios;
        this.idPersona = idPersona;
        this.correo = correo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fotoPerfil = fotoPerfil;
    }
    public UsuarioConCorreoYNombreCompletoYFotoDto() {
    }

    public UsuarioConCorreoYNombreCompletoYFotoDto(Usuariosinstituciones usuariosinstituciones) {
        this.idUsuarios = usuariosinstituciones.getUsuariosIdusuarios().getIdusuarios();
        this.idPersona = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getIdpersonas();
        this.correo = usuariosinstituciones.getUsuariosIdusuarios().getCorreo();
        this.nombre = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getNombres();
        this.apellidoPaterno = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getApellidopaterno();
        this.apellidoMaterno = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getApellidomaterno();
        this.fotoPerfil = usuariosinstituciones.getUsuariosIdusuarios().getPersonasIdpersonas().getFotoperfil();
    }

    public Integer getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public String toString() {
        return "UsuarioConCorreoYNombreCompletoYFotoDto{" +
                "idUsuarios=" + idUsuarios +
                ", idPersona=" + idPersona +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                '}';
    }
}
