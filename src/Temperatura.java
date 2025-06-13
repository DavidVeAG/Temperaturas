public class Temperatura {
    private String ciudad;
    private String fecha;
    private double grado;

    public Temperatura(String ciudad, String fecha, double grado) {
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.grado = grado;
    }

    public String getCiudad() { return ciudad; }
    public String getFecha() { return fecha; }
    public double getGrado() { return grado; }

    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setGrado(double grado) { this.grado = grado; }

    public String toString() {
        return ciudad + "," + fecha + "," + grado;
    }
}