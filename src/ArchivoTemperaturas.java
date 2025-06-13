import java.io.*;
import java.util.*;

public class ArchivoTemperaturas {
    public static ArrayList<Temperatura> leerArchivo(String ruta) {
        ArrayList<Temperatura> lista = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;
            boolean primera = true;
            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false;
                    continue; // saltar cabecera
                }
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String ciudad = partes[0];
                    String fecha = partes[1];
                    double grado = Double.parseDouble(partes[2]);
                    lista.add(new Temperatura(ciudad, fecha, grado));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void guardarArchivo(String ruta, ArrayList<Temperatura> lista) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(ruta));
            pw.println("Ciudad,Fecha,Temperatura");
            for (Temperatura t : lista) {
                pw.println(t);
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}