package unsl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import unsl.entities.Cuenta;
import unsl.entities.User;
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

    @Retryable( maxAttempts = 4, backoff = @Backoff(1000))
    public User getCuentaC(Cuenta cuenta) throws Exception {
        User exito;
        try {
            RestTemplate restTemplate = new RestTemplate();

            exito = restTemplate.getForObject("http://localhost:8080/users/" + cuenta.getId(), User.class);
        }catch(Exception e){
            throw new Exception(buildMessageError(e));
        }
        return exito;
    }

    private String buildMessageError(Exception e) {
        String msg = e.getMessage();
        if (e instanceof HttpClientErrorException) {
            msg = ((HttpClientErrorException) e).getResponseBodyAsString();
        } else if (e instanceof HttpServerErrorException) {
            msg =  ((HttpServerErrorException) e).getResponseBodyAsString();
        }
        return msg;
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

