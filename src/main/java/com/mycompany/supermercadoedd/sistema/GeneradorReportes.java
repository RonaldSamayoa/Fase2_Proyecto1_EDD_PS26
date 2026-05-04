package com.mycompany.supermercadoedd.sistema;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author ronald
 */
public class GeneradorReportes {
    // Guarda archivo .dot
    public static void guardarDot(String contenido, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo + ".dot")) {
            writer.write(contenido);
        } catch (IOException e) {
            System.out.println("Error al generar DOT: " + e.getMessage());
        }
    }

    // Genera imagen usando Graphviz
    public static void generarImagen(String nombreArchivo) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "dot", "-Tpng",
                nombreArchivo + ".dot",
                "-o",
                nombreArchivo + ".png"
            );
            pb.start();
        } catch (IOException e) {
            System.out.println("Error al generar imagen: " + e.getMessage());
        }
    }
}
