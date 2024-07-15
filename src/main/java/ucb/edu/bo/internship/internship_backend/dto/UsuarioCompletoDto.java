package ucb.edu.bo.internship.internship_backend.dto;

import ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias;

public class UsuarioCompletoDto {
    private PersonasDto persona;
    private UsuariosDto usuario;
    private AplicacionPasantiasDto aplicacionPasantia;

    public UsuarioCompletoDto(PersonasDto persona, UsuariosDto usuario, AplicacionPasantiasDto aplicacionPasantia) {
        this.persona = persona;
        this.usuario = usuario;
        this.aplicacionPasantia = aplicacionPasantia;
    }
    public UsuarioCompletoDto(PersonasDto persona, UsuariosDto usuario){
        this.persona = persona;
        this.usuario = usuario;
    }
    public UsuarioCompletoDto(){}

    public PersonasDto getPersona() {
        return persona;
    }

    public void setPersona(PersonasDto persona) {
        this.persona = persona;
    }

    public UsuariosDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDto usuario) {
        this.usuario = usuario;
    }

    public AplicacionPasantiasDto getAplicacionPasantia() {
        return aplicacionPasantia;
    }

    public void setAplicacionPasantia(AplicacionPasantiasDto aplicacionPasantia) {
        this.aplicacionPasantia = aplicacionPasantia;
    }
    public static UsuarioCompletoDto fromEntity(Aplicacionespasantias aplicacionespasantias){
        return new UsuarioCompletoDto(
                PersonasDto.fromEntity(aplicacionespasantias.getUsuariosIdusuarios().getPersonasIdpersonas()),
                UsuariosDto.fromEntity(aplicacionespasantias.getUsuariosIdusuarios()),
                AplicacionPasantiasDto.fromEntity(aplicacionespasantias)

        );
    }

    @Override
    public String toString() {
        return "UsuarioCompletoDto{" +
                "persona=" + persona +
                ", usuario=" + usuario +
                ", aplicacionPasantia=" + aplicacionPasantia +
                '}';
    }
}
