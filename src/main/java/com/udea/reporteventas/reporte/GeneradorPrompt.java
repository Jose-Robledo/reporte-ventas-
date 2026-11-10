package com.udea.reporteventas.reporte;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class GeneradorPrompt {

    public String construir(BigDecimal total, int cantidad,
                            String productoMayor, BigDecimal promedio) {
        NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        moneda.setMaximumFractionDigits(0);

        return """
                Actúa como analista de ventas de una pequeña empresa.
                Redacta en español un reporte ejecutivo de máximo tres párrafos.
                Usa únicamente los indicadores suministrados.
                Explica los resultados y ofrece recomendaciones prudentes.
                No inventes datos, cifras ni tendencias que no puedan deducirse.
                No vuelvas a calcular ni modifiques los indicadores.

                INDICADORES CALCULADOS POR EL PROGRAMA JAVA:
                - Valor total de ventas: %s
                - Cantidad total vendida: %d unidades
                - Producto con mayor valor vendido: %s
                - Valor promedio de venta por registro: %s
                """.formatted(
                moneda.format(total),
                cantidad,
                productoMayor,
                moneda.format(promedio)
        );
    }
}
