package com.udea.reporteventas;

import com.udea.reporteventas.modelo.Venta;
import com.udea.reporteventas.reporte.GeneradorGrafica;
import com.udea.reporteventas.reporte.GeneradorPrompt;
import com.udea.reporteventas.servicio.CalculadoraIndicadores;
import com.udea.reporteventas.servicio.ConexionGemini;
import com.udea.reporteventas.servicio.LectorVentas;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Path archivoCsv = args.length > 0 ? Path.of(args[0]) : Path.of("datos", "ventas.csv");
        Path carpetaReportes = Path.of("reportes");

        try {
            System.out.println("======================================");
            System.out.println("        SISTEMA DE REPORTE DE VENTAS");
            System.out.println("======================================");

            List<Venta> ventas = new LectorVentas().leerArchivo(archivoCsv);
            if (ventas.isEmpty()) {
                throw new IllegalStateException("El archivo CSV no contiene registros.");
            }

            CalculadoraIndicadores calculadora = new CalculadoraIndicadores();
            BigDecimal total = calculadora.calcularTotalVentas(ventas);
            int cantidad = calculadora.calcularCantidadTotal(ventas);
            String productoMayor = calculadora.obtenerProductoMayorVenta(ventas);
            BigDecimal promedio = calculadora.calcularPromedioPorRegistro(ventas);

            NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
            moneda.setMaximumFractionDigits(0);

            System.out.println("\nINDICADORES CALCULADOS POR JAVA");
            System.out.println("Total de ventas: " + moneda.format(total));
            System.out.println("Cantidad total vendida: " + cantidad + " unidades");
            System.out.println("Producto con mayor valor vendido: " + productoMayor);
            System.out.println("Promedio por registro: " + moneda.format(promedio));

            Files.createDirectories(carpetaReportes);

            String indicadores = """
                    REPORTE DE INDICADORES
                    Total de ventas: %s
                    Cantidad total vendida: %d unidades
                    Producto con mayor valor vendido: %s
                    Promedio por registro: %s
                    """.formatted(moneda.format(total), cantidad, productoMayor, moneda.format(promedio));

            Files.writeString(carpetaReportes.resolve("indicadores.txt"), indicadores);

            new GeneradorGrafica().generar(ventas,
                    carpetaReportes.resolve("ventas_por_producto.png"));
            System.out.println("\nGráfica guardada en reportes/ventas_por_producto.png");

            String prompt = new GeneradorPrompt().construir(total, cantidad, productoMayor, promedio);
            Files.writeString(carpetaReportes.resolve("prompt_ia.txt"), prompt);
            System.out.println("Prompt guardado en reportes/prompt_ia.txt");

            System.out.println("\nCONSULTANDO GOOGLE GEMINI...");
            try {
                String respuesta = new ConexionGemini().generarReporte(prompt);
                Files.writeString(carpetaReportes.resolve("reporte_ejecutivo.txt"), respuesta);
                System.out.println("\nREPORTE EJECUTIVO");
                System.out.println(respuesta);
                System.out.println("\nRespuesta guardada en reportes/reporte_ejecutivo.txt");
            } catch (Exception errorIA) {
                System.out.println("No se pudo obtener el reporte de Gemini: " + errorIA.getMessage());
                System.out.println("Los indicadores, la gráfica y el prompt sí fueron generados.");
            }

            System.out.println("\nProceso finalizado.");
        } catch (Exception error) {
            System.err.println("No se pudo ejecutar el reporte: " + error.getMessage());
            error.printStackTrace();
            System.exit(1);
        }
    }
}
