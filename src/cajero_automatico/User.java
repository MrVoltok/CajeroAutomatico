package cajero_automatico;

import java.util.ArrayList;

public class User {
    private String numCuenta, pin;
    private float saldo;
    private ArrayList<String> ingreso = new ArrayList<String>();
    private ArrayList<String> salida;

    public User(String numCuenta, String pin, float saldo) {
        this.numCuenta = numCuenta;
        this.pin = pin;
        this.saldo = saldo;
    }

    public void depositaSaldo(float deposito) {
        saldo += deposito;
    }

    public void retiraSaldo(float retiro) {
        saldo -= retiro;
    }

    public String consultaSaldo(float saldo) {
        String saldoString = Float.toString(saldo);
        return saldoString;
    }

    public void setIngreso(String fechaIngreso) {
        ingreso.add(fechaIngreso);
    }

    public String getIngreso(int index) {
        return ingreso.get(index);
    }

    public void setSalida(String fechaSalida) {
        salida.add(fechaSalida);
    }
}
