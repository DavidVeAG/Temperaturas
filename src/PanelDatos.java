import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class PanelDatos extends JPanel {
    private ArrayList<Temperatura> lista;
    private JTable tabla;
    private DefaultTableModel modelo;

    private JComboBox<String> filtroCiudad;
    private JDateChooser filtroFecha;
    private JTextField filtroTemp;

    public PanelDatos(ArrayList<Temperatura> lista) {
        this.lista = lista;
        setLayout(new BorderLayout());

        // Filtros superiores
        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JLabel("Ciudad:"));
        filtroCiudad = new JComboBox<>(new String[]{"Todas", "Bogotá", "Medellín", "Cali", "Barranquilla"});
        panelFiltros.add(filtroCiudad);

        panelFiltros.add(new JLabel("Fecha:"));
        filtroFecha = new JDateChooser();
        filtroFecha.setDateFormatString("dd/MM/yyyy");
        panelFiltros.add(filtroFecha);

        panelFiltros.add(new JLabel("Temperatura:"));
        filtroTemp = new JTextField(5);
        panelFiltros.add(filtroTemp);

        JButton btnFiltrar = new JButton("Filtrar");
        panelFiltros.add(btnFiltrar);

        btnFiltrar.addActionListener(e -> aplicarFiltro());

        add(panelFiltros, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(new String[]{"Ciudad", "Fecha", "Temperatura"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarTabla(lista);
    }

    public void cargarTabla(ArrayList<Temperatura> datos) {
        modelo.setRowCount(0);
        for (Temperatura t : datos) {
            modelo.addRow(new Object[]{t.getCiudad(), t.getFecha(), t.getGrado()});
        }
    }

    public void aplicarFiltro() {
        String ciudadSel = (String) filtroCiudad.getSelectedItem();
        String fechaSel = filtroFecha.getDate() != null ? new SimpleDateFormat("dd/MM/yyyy").format(filtroFecha.getDate()) : "";
        String tempSel = filtroTemp.getText().trim();

        ArrayList<Temperatura> filtrado = new ArrayList<>();

        for (Temperatura t : lista) {
            boolean pasa = true;

            if (!ciudadSel.equals("Todas") && !t.getCiudad().equalsIgnoreCase(ciudadSel)) {
                pasa = false;
            }

            if (!fechaSel.isEmpty() && !t.getFecha().equals(fechaSel)) {
                pasa = false;
            }

            if (!tempSel.isEmpty()) {
                try {
                    double valor = Double.parseDouble(tempSel);
                    if (t.getGrado() != valor) {
                        pasa = false;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Temperatura inválida en filtro.");
                    return;
                }
            }

            if (pasa) {
                filtrado.add(t);
            }
        }

        cargarTabla(filtrado);
    }

    public void agregarFila() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JComboBox<String> combo = new JComboBox<>(new String[]{"Bogotá", "Medellín", "Cali", "Barranquilla"});
        JDateChooser fechaPicker = new JDateChooser();
        fechaPicker.setDateFormatString("dd/MM/yyyy");
        JTextField temp = new JTextField();

        panel.add(new JLabel("Ciudad:"));
        panel.add(combo);
        panel.add(new JLabel("Fecha:"));
        panel.add(fechaPicker);
        panel.add(new JLabel("Temperatura:"));
        panel.add(temp);

        int resp = JOptionPane.showConfirmDialog(this, panel, "Agregar Temperatura", JOptionPane.OK_CANCEL_OPTION);

        if (resp == JOptionPane.OK_OPTION) {
            try {
                String ciudad = combo.getSelectedItem().toString();
                if (fechaPicker.getDate() == null) throw new Exception();
                String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fechaPicker.getDate());
                double valor = Double.parseDouble(temp.getText());
                Temperatura nueva = new Temperatura(ciudad, fecha, valor);
                lista.add(nueva);
                cargarTabla(lista);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Datos inválidos o incompletos.");
            }
        }
    }

    public void eliminarFila() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            lista.remove(fila);
            cargarTabla(lista);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
        }
    }
}