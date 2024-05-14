
package javaapplication20;

/**
 *
 * @author pc
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
class InicioSesion extends JFrame implements ActionListener {
    JLabel labelUsuario, labelContrasena;
    JTextField txtUsuario;
    JPasswordField txtContrasena;
    JButton btnIniciarSesion;

    InicioSesion() {
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(new Color(70, 130, 180)); 
        add(panel, BorderLayout.CENTER);

        labelUsuario = new JLabel("Usuario:");
        labelUsuario.setForeground(Color.WHITE);
        panel.add(labelUsuario);

        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        labelContrasena = new JLabel("Contraseña:");
        labelContrasena.setForeground(Color.WHITE);
        panel.add(labelContrasena);

        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(this);
        add(btnIniciarSesion, BorderLayout.SOUTH);

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnIniciarSesion) {
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (validarCredenciales(usuario, contrasena)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
                new InventarioFormulario();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarCredenciales(String usuario, String contrasena) {
        return usuario.equals("Grupo5") && contrasena.equals("1234");
    }
}
     

public class InventarioFormulario extends JFrame implements ActionListener {
    JLabel labelTitulo, labelNombre, labelCantidad;
    JTextField txtNombre, txtCantidad;
    JButton btnAgregar, btnMostrar, btnStock, btnPrecio;

    JTable tablaInventario;
    DefaultTableModel modeloTabla;

    HashMap<String, Integer> inventario;
    HashMap<String, Double> precios;

    InventarioFormulario() {
        setTitle("Sistema de Inventario");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.GRAY);

        labelTitulo = new JLabel("Inventario");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setBounds(250, 20, 100, 20);
        add(labelTitulo);

        labelNombre = new JLabel("Nombre:");
        labelNombre.setBounds(30, 70, 80, 20);
        add(labelNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 70, 150, 20);
        add(txtNombre);

        labelCantidad = new JLabel("Cantidad:");
        labelCantidad.setBounds(30, 100, 80, 20);
        add(labelCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(120, 100, 150, 20);
        add(txtCantidad);

        btnAgregar = new JButton("Agregar al Inventario");
        btnAgregar.setBounds(30, 150, 200, 30);
        btnAgregar.addActionListener(this);
        add(btnAgregar);

        btnMostrar = new JButton("Mostrar Inventario");
        btnMostrar.setBounds(250, 150, 200, 30);
        btnMostrar.addActionListener(this);
        add(btnMostrar);

        btnStock = new JButton("Mostrar Stock");
        btnStock.setBounds(30, 200, 200, 30);
        btnStock.addActionListener(this);
        add(btnStock);

        btnPrecio = new JButton("Mostrar Precio");
        btnPrecio.setBounds(250, 200, 200, 30);
        btnPrecio.addActionListener(this);
        add(btnPrecio);

        modeloTabla = new DefaultTableModel(new String[]{"Producto", "Stock Disponible", "Precio Unit."}, 0);
        tablaInventario = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaInventario.getColumnCount(); i++) {
            tablaInventario.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaInventario);
        scrollPane.setBounds(30, 250, 540, 100);
        scrollPane.getViewport().setBackground(new Color(102, 255, 255)); 
        add(scrollPane);

        setLocationRelativeTo(null); 
        setVisible(true);

        inventario = new HashMap<>();
        precios = new HashMap<>();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            String nombre = txtNombre.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());

            String precioString = JOptionPane.showInputDialog(this, "Ingrese el precio unitario para " + nombre + ":");
            double precio = Double.parseDouble(precioString);

            if (inventario.containsKey(nombre)) {
                inventario.put(nombre, inventario.get(nombre) + cantidad);
            } else {
                inventario.put(nombre, cantidad);
                precios.put(nombre, precio);
            }
            JOptionPane.showMessageDialog(this, "Elemento agregado al inventario");
        } else if (e.getSource() == btnMostrar) {
            actualizarTabla();
        } else if (e.getSource() == btnStock) {
            String nombre = txtNombre.getText();
            if (inventario.containsKey(nombre)) {
                JOptionPane.showMessageDialog(this, "Se tiene " + inventario.get(nombre) + " unidades de " + nombre);
            } else {
                JOptionPane.showMessageDialog(this, "El producto no está en el inventario");
            }
        } else if (e.getSource() == btnPrecio) {
            String nombre = txtNombre.getText();
            if (precios.containsKey(nombre)) {
                JOptionPane.showMessageDialog(this, "El precio por unidad de " + nombre + " es de " + precios.get(nombre) + " soles");
            } else {
                JOptionPane.showMessageDialog(this, "El producto no está en el inventario");
            }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (String nombre : inventario.keySet()) {
            int cantidad = inventario.get(nombre);
            double precio = precios.get(nombre);
            modeloTabla.addRow(new Object[]{nombre, cantidad + " unidades", precio + " soles"});
        }
    }
}
