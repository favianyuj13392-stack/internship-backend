package ucb.edu.bo.internship.internship_backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucb.edu.bo.internship.internship_backend.entity.Pasantias;

import java.util.Date;

public interface PasantiasDao extends JpaRepository<Pasantias, Integer>{

    Page<Pasantias> findAllByActivoIsTrueAndFechacierreAfter(
            Date fechacierre,
            Pageable pageable
    );

    Page<Pasantias> findAllByActivoIsTrue(Pageable pageable);
}
