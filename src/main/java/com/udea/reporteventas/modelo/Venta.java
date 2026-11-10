package com.udea.reporteventas.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Venta {
    private final LocalDate fecha;
    private final String producto;
    private final int cantidad;
    private final BigDecimal valor;

    public Venta(LocalDate fecha, String producto, int cantidad, BigDecimal valor) {
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
