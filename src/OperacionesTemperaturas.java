import java.util.*;

public class OperacionesTemperaturas {
    public static HashMap<String, Double> calcularPromedios(ArrayList<Temperatura> datos, String desde, String hasta) {
        HashMap<String, ArrayList<Double>> agrupado = new HashMap<>();

        for (Temperatura t : datos) {
            if (t.getFecha().compareTo(desde) >= 0 && t.getFecha().compareTo(hasta) <= 0) {
                agrupado.computeIfAbsent(t.getCiudad(), k -> new ArrayList<>()).add(t.getGrado());
            }
        }

        HashMap<String, Double> resultado = new HashMap<>();
        for (String ciudad : agrupado.keySet()) {
            ArrayList<Double> lista = agrupado.get(ciudad);
            double suma = 0;
            for (double val : lista) suma += val;
            resultado.put(ciudad, suma / lista.size());
        }

        return resultado;
    }

    public static Temperatura buscarMayor(ArrayList<Temperatura> datos, String fecha) {
        Temperatura mayor = null;
        for (Temperatura t : datos) {
            if (t.getFecha().equals(fecha)) {
                if (mayor == null || t.getGrado() > mayor.getGrado()) {
                    mayor = t;
                }
            }
        }
        return mayor;
    }

    public static Temperatura buscarMenor(ArrayList<Temperatura> datos, String fecha) {
        Temperatura menor = null;
        for (Temperatura t : datos) {
            if (t.getFecha().equals(fecha)) {
                if (menor == null || t.getGrado() < menor.getGrado()) {
                    menor = t;
                }
            }
        }
        return menor;
    }
}