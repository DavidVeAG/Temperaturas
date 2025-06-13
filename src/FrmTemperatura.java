import javax.swing.*;
import java.util.*;

public class FrmTemperatura extends JFrame {
    private ArrayList<Temperatura> lista;
    private String ruta = "src/datos/Temperaturas.csv";

    private JButton btnAgregar, btnEliminar, btnGuardar;
    private PanelDatos panelDatos;
    private PanelGrafica panelGrafica;
    private PanelConsulta panelConsulta;

    public FrmTemperatura() {
        setTitle("Temperaturas");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        lista = ArchivoTemperaturas.leerArchivo(ruta);

        btnAgregar = new JButton();
        btnAgregar.setIcon(new ImageIcon("src/imagenes/agregar.png"));
        btnAgregar.setBounds(10, 10, 60, 60);
        add(btnAgregar);

        btnEliminar = new JButton();
        btnEliminar.setIcon(new ImageIcon("src/imagenes/eliminar.png"));
        btnEliminar.setBounds(80, 10, 60, 60);
        add(btnEliminar);

        btnGuardar = new JButton();
        btnGuardar.setIcon(new ImageIcon("src/imagenes/guardar.png"));
        btnGuardar.setBounds(150, 10, 60, 60);
        add(btnGuardar);

        panelDatos = new PanelDatos(lista);
        panelGrafica = new PanelGrafica(lista);
        panelConsulta = new PanelConsulta(lista);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setBounds(10, 90, 760, 460);
        pestañas.addTab("Datos", panelDatos);
        pestañas.addTab("Gráfica", panelGrafica);
        pestañas.addTab("Consulta", panelConsulta);

        add(pestañas);

        btnAgregar.addActionListener(e -> panelDatos.agregarFila());
        btnEliminar.addActionListener(e -> panelDatos.eliminarFila());
        btnGuardar.addActionListener(e -> {
            ArchivoTemperaturas.guardarArchivo(ruta, lista);
            JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.");
        });
    }
}