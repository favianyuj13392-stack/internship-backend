package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;
import ucb.edu.bo.internship.internship_backend.entity.Usuarios;

import java.util.List;

public interface AplicacionPasantiasDao extends JpaRepository<Aplicacionespasantias, Integer>{

    Page<Aplicacionespasantias> findAllByPasantiasIdpasantias(Pasantias pasantias, Pageable pageable);

    Aplicacionespasantias findByPasantiasIdpasantiasAndUsuariosIdusuarios(Pasantias idpasantias, Usuarios idusuarios);

    List<Aplicacionespasantias> findByUsuariosIdusuariosAndPasantiasIdpasantias(Usuarios usuario, Pasantias pasantia);
}
