package unsl.repository;

import unsl.entities.Cuenta;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List <Cuenta> findByTitular(@Param("titular") Long titular);
}
