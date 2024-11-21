package Vista;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Vista extends JFrame {

    public JPanel contentPane;
    public JTable table;
    public JMenuItem conectarBD;
    public JMenuItem conectarExcel;
    public JMenuItem desconectarBD;
    public JMenuItem desconectarExcel;
    public DefaultTableModel modeloTbl;
    public JButton anadirBD;
    public JButton Listar;
    public JButton Limpiar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Vista frame = new Vista();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Vista() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 690, 364);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnConectar = new JMenu("Conectar");
        menuBar.add(mnConectar);

        conectarBD = new JMenuItem("Base de Datos");
        mnConectar.add(conectarBD);

        conectarExcel = new JMenuItem("Excel");
        mnConectar.add(conectarExcel);

        JMenu mnNewMenu_1 = new JMenu("Desconectar");
        menuBar.add(mnNewMenu_1);

        desconectarBD = new JMenuItem("Base de Datos");
        mnNewMenu_1.add(desconectarBD);

        desconectarExcel = new JMenuItem("Excel");
        mnNewMenu_1.add(desconectarExcel);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        anadirBD = new JButton("Añadir a BBDD");
        anadirBD.setBounds(32, 55, 141, 23);
        contentPane.add(anadirBD);

        Listar = new JButton("Listar");
        Listar.setBounds(241, 55, 141, 23);
        contentPane.add(Listar);

        Limpiar = new JButton("Limpiar");
        Limpiar.setBounds(436, 55, 141, 23);
        contentPane.add(Limpiar);

        // Configurar el modelo de la tabla con columnas iniciales
        modeloTbl = new DefaultTableModel(new Object[]{"Nombre Provincia"}, 0);

        // Crear la tabla con el modelo y añadirla dentro de un JScrollPane
        table = new JTable(modeloTbl);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 580, 200);
        contentPane.add(scrollPane);

        setVisible(true);
    }
}
