package unsl.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import unsl.entities.Cuenta;
import unsl.entities.ResponseError;
import unsl.entities.User;
import unsl.services.CuentaService;

@RestController
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    //Buscar cuentas por titular
    @GetMapping(value = "/cuenta/search")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object searchUser(@RequestParam("titular") long titular){
        List <Cuenta> cuenta = cuentaService.findByTitular(titular);
        if(cuenta == null){
            return new ResponseEntity(new ResponseError(404, String.format("Cuenta with titular_id %d not found")), HttpStatus.NOT_FOUND);
        }
        return cuenta;
    }


    //Get por id
    @GetMapping(value = "/cuenta/{cuentaId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object getCuenta(@PathVariable("cuentaId") long cuentaId){
        Cuenta cuenta = cuentaService.getCuenta(cuentaId);
        if(cuenta == null){
            return new ResponseEntity(new ResponseError(404, String.format("Cuenta with id  %d not found", cuentaId)), HttpStatus.NOT_FOUND);
        }
        return cuenta;
    }

    //Get all
    @GetMapping(value = "/cuenta")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List <Cuenta> getAll(){return cuentaService.getAll();}

    //Crear cuenta
    @PostMapping(value = "/cuenta")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Object createCuenta(@RequestBody Cuenta cuenta) throws Exception {
        long titular = cuenta.getTitular();
        Cuenta.Moneda moneda = cuenta.getMoneda();

        List<Cuenta> cuentasTitular = cuentaService.findByTitular(titular); //Recupero las cuentas, en caso de que las hubieran, con el titular de la cuenta que se quiere crear.

        if(cuentaService.getCuentaC(cuenta) == null){
            return new ResponseEntity(new ResponseError(400, String.format("Cliente de la cuenta inexistente")), HttpStatus.BAD_REQUEST);
        }

        if(cuenta.getMoneda() == null){
            return new ResponseEntity(new ResponseError(400, String.format("moneda not found")), HttpStatus.BAD_REQUEST);
        }
        else if(cuentasTitular.size() > 2) {
            return new ResponseEntity(new ResponseError(400, String.format("El cliente ya dispone de mas de 2 cuentas")), HttpStatus.BAD_REQUEST);
        }
        for (Cuenta c: cuentasTitular) {
            if (c.getMoneda() == moneda){
                return new ResponseEntity(new ResponseError(400, String.format("El cliente ya dispone de una cuenta con esta moneda")), HttpStatus.BAD_REQUEST);
            }
        }

        //Valores iniciales de la cuenta
        cuenta.setEstado(Cuenta.Estado.ACTIVO);
        cuenta.setSaldo(0);

        //retorno la cuenta creada
        return cuentaService.saveCuenta(cuenta);
    }

    //Baja logica
    @PutMapping("/cuenta/{cuentaId}")
    @ResponseBody
    public Object updateEstado(@PathVariable("cuentaId") long cuentaId){
        Cuenta cuenta = cuentaService.getCuenta(cuentaId);
        Cuenta c = cuentaService.updateEstado(cuenta);

        if ( c == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Cuenta with Id %d not found", cuenta.getId())), HttpStatus.NOT_FOUND);
        }
        return c;
    }

    //Actualizar saldo de la cuenta
    @PutMapping("/cuenta")
    @ResponseBody
    public Object updateSaldo(@RequestBody Cuenta cuenta){
        Cuenta c = cuentaService.updateSaldo(cuenta);

        if ( c == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Cuenta with Id %d not found", cuenta.getId())), HttpStatus.NOT_FOUND);
        }
        return c;
    }

}