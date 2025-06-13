import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class PanelGrafica extends JPanel {
    private ArrayList<Temperatura> lista;
    private JDateChooser dateDesde, dateHasta;
    private JButton btnGenerar;
    private HashMap<String, Double> datosGraficados;
    private JPanel panelGrafico;

    public PanelGrafica(ArrayList<Temperatura> lista) {
        this.lista = lista;
        this.datosGraficados = new HashMap<>();

        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Desde:"));
        dateDesde = new JDateChooser();
        dateDesde.setDateFormatString("dd/MM/yyyy");
        panelSuperior.add(dateDesde);

        panelSuperior.add(new JLabel("Hasta:"));
        dateHasta = new JDateChooser();
        dateHasta.setDateFormatString("dd/MM/yyyy");
        panelSuperior.add(dateHasta);

        btnGenerar = new JButton("Generar gráfica");
        panelSuperior.add(btnGenerar);

        add(panelSuperior, BorderLayout.NORTH);

        panelGrafico = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (datosGraficados.isEmpty()) return;

                int x = 50;
                int base = getHeight() - 50;
                int altoMax = getHeight() - 100;
                double max = datosGraficados.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);

                for (Map.Entry<String, Double> entry : datosGraficados.entrySet()) {
                    int alto = (int)((entry.getValue() / max) * altoMax);
                    g.setColor(Color.BLUE);
                    g.fillRect(x, base - alto, 40, alto);
                    g.setColor(Color.BLACK);
                    g.drawString(entry.getKey(), x, base + 15);
                    g.drawString(String.format("%.1f", entry.getValue()), x, base - alto - 5);
                    x += 60;
                }
            }
        };

        add(panelGrafico, BorderLayout.CENTER);

        btnGenerar.addActionListener(e -> dibujarGrafica());
    }

    private void dibujarGrafica() {
        if (dateDesde.getDate() == null || dateHasta.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Selecciona ambas fechas.");
            return;
        }

        String desde = new SimpleDateFormat("dd/MM/yyyy").format(dateDesde.getDate());
        String hasta = new SimpleDateFormat("dd/MM/yyyy").format(dateHasta.getDate());

        datosGraficados = OperacionesTemperaturas.calcularPromedios(lista, desde, hasta);

        if (datosGraficados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos en el rango de fechas seleccionado.");
        }

        panelGrafico.repaint();
    }
}
