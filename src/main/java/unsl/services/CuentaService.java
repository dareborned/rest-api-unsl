package unsl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsl.entities.Cuenta;
import unsl.repository.CuentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    public Cuenta saveCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta getCuenta(long cuentaId) { return cuentaRepository.findById(cuentaId).orElse(null); }

    public List <Cuenta> findByTitular(long titular){ return cuentaRepository.findByTitular(titular); }

    public Cuenta updateEstado(Cuenta updateCuenta){
        Cuenta cuenta = cuentaRepository.findById(updateCuenta.getId()).orElse(null);
        if(cuenta == null){
            return null;
        }

        cuenta.setEstado(Cuenta.Estado.BAJA);
        return cuentaRepository.save(cuenta);
    }

    public Cuenta updateSaldo(Cuenta updateCuenta){
        Cuenta cuenta = cuentaRepository.findById(updateCuenta.getId()).orElse(null);
        if(cuenta == null){
            return null;
        }

        cuenta.setSaldo(updateCuenta.getSaldo());
        return cuentaRepository.save(cuenta);
    }

}

