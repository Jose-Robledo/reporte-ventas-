package com.udea.reporteventas.servicio;

import com.udea.reporteventas.modelo.Venta;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LectorVentas {

    public List<Venta> leerArchivo(Path ruta) throws IOException {
        List<Venta> ventas = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(ruta);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : parser) {
                try {
                    LocalDate fecha = LocalDate.parse(registro.get("fecha").trim());
                    String producto = registro.get("producto").trim();
                    int cantidad = Integer.parseInt(registro.get("cantidad").trim());
                    BigDecimal valor = new BigDecimal(registro.get("valor").trim());

                    if (producto.isBlank() || cantidad <= 0 || valor.signum() < 0) {
                        throw new IllegalArgumentException("Producto vacío, cantidad no positiva o valor negativo.");
                    }

                    ventas.add(new Venta(fecha, producto, cantidad, valor));
                } catch (RuntimeException error) {
                    throw new IllegalArgumentException(
                            "Error en la fila " + registro.getRecordNumber()
                                    + " del CSV: " + error.getMessage(), error);
                }
            }
        }

        return ventas;
    }
}
