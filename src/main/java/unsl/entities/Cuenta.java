package unsl.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "cuenta")
public class Cuenta {
    public static enum Moneda {
        PESO_AR,
        DOLAR,
        EURO
    }
    public static enum Estado {
        ACTIVO,
        BAJA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Id del titular de la cuenta
    @JsonProperty("titular")
    private long titular;

    @JsonProperty("saldo")
    private float saldo;

    @Enumerated(EnumType.STRING)
    private Moneda moneda;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    //Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTitular() {
        return titular;
    }

    public void setTitular(long titular) {
        this.titular = titular;
    }

    public float getSaldo(){
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Moneda getMoneda(){
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}