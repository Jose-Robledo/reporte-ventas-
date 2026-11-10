package com.udea.reporteventas.servicio;

import com.udea.reporteventas.modelo.Venta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CalculadoraIndicadores {

    public BigDecimal calcularTotalVentas(List<Venta> ventas) {
        BigDecimal total = BigDecimal.ZERO;
        for (Venta venta : ventas) {
            total = total.add(venta.getValor());
        }
        return total;
    }

    public int calcularCantidadTotal(List<Venta> ventas) {
        int total = 0;
        for (Venta venta : ventas) {
            total += venta.getCantidad();
        }
        return total;
    }

    public Map<String, BigDecimal> obtenerVentasPorProducto(List<Venta> ventas) {
        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        for (Venta venta : ventas) {
            resultado.merge(venta.getProducto(), venta.getValor(), BigDecimal::add);
        }
        return resultado;
    }

    public String obtenerProductoMayorVenta(List<Venta> ventas) {
        return obtenerVentasPorProducto(ventas).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No disponible");
    }

    public BigDecimal calcularPromedioPorRegistro(List<Venta> ventas) {
        if (ventas.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return calcularTotalVentas(ventas)
                .divide(BigDecimal.valueOf(ventas.size()), 2, RoundingMode.HALF_UP);
    }
}
