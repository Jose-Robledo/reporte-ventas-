# Sistema de reporte de ventas

Proyecto académico de Técnicas de Programación y Laboratorio. Lee ventas desde un CSV, calcula indicadores, genera una gráfica y solicita a Google Gemini un reporte ejecutivo.

## Requisitos

- JDK 21
- Maven 3.9 o posterior
- Conexión a Internet para descargar dependencias y consultar Gemini
- Una API Key válida de Gemini configurada como variable de entorno

## Estructura

- `datos/ventas.csv`: archivo de entrada.
- `src/main/java`: código fuente.
- `reportes/`: resultados generados al ejecutar.
- `target/reporte-ventas.jar`: JAR ejecutable generado por Maven.

## Configurar la API Key en Windows PowerShell

En la misma terminal desde la que se ejecutará el programa:

```powershell
$env:GOOGLE_API_KEY="PEGUE_AQUI_SU_CLAVE"
```

No escriba la clave en los archivos Java, en `pom.xml`, ni la suba a repositorios.

Verifique que quedó configurada sin imprimir su contenido:

```powershell
if ($env:GOOGLE_API_KEY) { "API Key configurada" } else { "Falta API Key" }
```

## Compilar

Desde la carpeta raíz del proyecto:

```powershell
mvn clean package
```

Debe terminar con `BUILD SUCCESS`.

## Ejecutar desde Maven

```powershell
mvn exec:java -Dexec.mainClass="com.udea.reporteventas.Main"
```

## Ejecutar el JAR

Primero configure `GOOGLE_API_KEY` en esa terminal y luego ejecute:

```powershell
java -jar target/reporte-ventas.jar
```

El CSV predeterminado es `datos/ventas.csv`. También se puede indicar otra ruta como argumento:

```powershell
java -jar target/reporte-ventas.jar datos/ventas.csv
```

## Resultados

El programa genera en `reportes/`:

- `indicadores.txt`
- `ventas_por_producto.png`
- `prompt_ia.txt`
- `reporte_ejecutivo.txt` (si Gemini responde correctamente)

## Indicadores de comprobación con el CSV entregado

- Total de ventas: $12.930.000
- Cantidad total vendida: 29 unidades
- Producto con mayor valor vendido: Laptop
- Promedio por registro: $2.586.000

`valor` se interpreta como el valor total del registro, tal como se utiliza en los cálculos del caso.

## Dependencias y licencias

| Librería | Uso | Licencia | Consideraciones |
|---|---|---|---|
| Apache Commons CSV 1.14.1 | Lectura del archivo CSV | Apache License 2.0 | Permite uso académico y redistribución; conservar avisos y texto de licencia. |
| JFreeChart 1.5.6 | Generación de gráfica PNG | LGPL 2.1 o posterior | Al redistribuir, cumplir las condiciones de LGPL, conservar avisos y respetar los derechos sobre la biblioteca. |
| Google Gen AI SDK 1.80.0 | Solicitudes a Gemini | Apache License 2.0 | Permite uso académico; conservar avisos y texto de licencia. |

Las licencias de las dependencias deben revisarse junto con los avisos de cada versión utilizada.

## Separación de responsabilidades

Java lee el CSV y calcula todos los indicadores. Gemini recibe esos resultados y redacta el texto ejecutivo; no sustituye los cálculos del programa.
