import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class PanelConsulta extends JPanel {
    private ArrayList<Temperatura> lista;
    private JDateChooser dateConsulta;
    private JButton btnBuscar;
    private JTextArea resultado;

    public PanelConsulta(ArrayList<Temperatura> lista) {
        this.lista = lista;
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Fecha:"));
        dateConsulta = new JDateChooser();
        dateConsulta.setDateFormatString("dd/MM/yyyy");
        panelSuperior.add(dateConsulta);

        btnBuscar = new JButton("Consultar");
        panelSuperior.add(btnBuscar);

        add(panelSuperior, BorderLayout.NORTH);

        resultado = new JTextArea();
        resultado.setEditable(false);
        resultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(resultado), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> consultarFecha());
    }

    private void consultarFecha() {
        if (dateConsulta.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una fecha");
            return;
        }

        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(dateConsulta.getDate());
        Temperatura mayor = OperacionesTemperaturas.buscarMayor(lista, fecha);
        Temperatura menor = OperacionesTemperaturas.buscarMenor(lista, fecha);

        if (mayor == null || menor == null) {
            JOptionPane.showMessageDialog(this, "No se encontraron datos para la fecha seleccionada.");
            resultado.setText("");
            return;
        }

        String texto = "Consulta para la fecha: " + fecha + "\n\n";
        texto += "Ciudad más calurosa: " + mayor.getCiudad() + " con " + mayor.getGrado() + "°C\n";
        texto += "Ciudad más fría:     " + menor.getCiudad() + " con " + menor.getGrado() + "°C\n";

        resultado.setText(texto);
    }
}