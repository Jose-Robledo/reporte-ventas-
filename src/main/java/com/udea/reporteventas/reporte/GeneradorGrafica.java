package com.udea.reporteventas.reporte;

import com.udea.reporteventas.modelo.Venta;
import com.udea.reporteventas.servicio.CalculadoraIndicadores;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GeneradorGrafica {

    public void generar(List<Venta> ventas, Path destino) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        CalculadoraIndicadores calculadora = new CalculadoraIndicadores();
        Map<String, java.math.BigDecimal> datos =
                calculadora.obtenerVentasPorProducto(ventas);

        for (Map.Entry<String, java.math.BigDecimal> entrada : datos.entrySet()) {
            dataset.addValue(entrada.getValue().doubleValue(), "Ventas (COP)", entrada.getKey());
        }

        JFreeChart grafica = ChartFactory.createBarChart(
                "Ventas por producto",
                "Producto",
                "Valor vendido (COP)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        NumberFormat formato = NumberFormat.getNumberInstance(new Locale("es", "CO"));
        formato.setMaximumFractionDigits(0);
        grafica.getCategoryPlot().getRenderer()
                .setDefaultItemLabelGenerator(
                        new StandardCategoryItemLabelGenerator("{2}", formato));
        grafica.getCategoryPlot().getRenderer().setDefaultItemLabelsVisible(true);

        ChartUtils.saveChartAsPNG(destino.toFile(), grafica, 1100, 650);
    }
}
