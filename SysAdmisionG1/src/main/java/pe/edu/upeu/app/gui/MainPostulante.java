/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pe.edu.upeu.app.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import pe.com.syscenterlife.autocomp.AutoCompleteTextField;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.com.syscenterlife.formvalid.Validator;
import pe.com.syscenterlife.formvalid.ValidatorItem;
import pe.edu.upeu.app.dao.PostulanteDao;
import pe.edu.upeu.app.dao.PostulanteDaoI;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.PostulanteTO;
import pe.edu.upeu.app.util.ErrorLogger;
import pe.edu.upeu.app.util.MsgBox;

/**
 *
 * @author Data_Science
 */
enum ESTADO {
    Activo, Desactivo
};

public class MainPostulante extends javax.swing.JPanel {

    PostulanteDaoI cDao;
    DefaultTableModel modelo;
    MsgBox msg;
    TableRowSorter<TableModel> trsfiltro;
    List<ModeloDataAutocomplet> items;
    static ErrorLogger log = new ErrorLogger(MainPostulante.class.getName());

    /**
     * Creates new form MainPostulante
     */
    public MainPostulante() {
        initComponents();
        listarDatos();
        cDao = new PostulanteDao();
        for (ComboBoxOption cb : cDao.listaModalidadExamen()) {
            cbxModalidad.addItem(cb);
        }
        cDao = new PostulanteDao();
        for (ComboBoxOption cb : cDao.listarPeriodo()) {
            cbxPeriodo.addItem(cb);
        }
        for (ESTADO myVar : ESTADO.values()) {
            cbxEstado.addItem(myVar.toString());
        }
        buscarCarrera();
        txtCarrera.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode()
                        == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
                        && AutoCompleteTextField.dataGetReturnet != null) {
//txtAutoCompDNI.setText(AutoCompleteTextField.dataGetReturnet.getIdx());
                    txtIdCarrera.setText(AutoCompleteTextField.dataGetReturnet
                            .getIdx());
                }
            }
        });

    }

    public void buscarCarrera() {
        cDao = new PostulanteDao();
        items = cDao.listAutoCompletCarrera("");
        AutoCompleteTextField.setupAutoComplete(txtCarrera, items,
                "NAME");//ID,NAME, OTHER
    }

    public void listarDatos() {
        cDao = new PostulanteDao();
        List<PostulanteTO> listarCleintes = cDao.listarTodo();
        jTable1.setAutoCreateRowSorter(true);
        modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        Object[] ob = new Object[9];
        int cont = -1;
        for (int i = 0; i < listarCleintes.size(); i++) {
            ob[++cont] = i + 1;
            ob[++cont] = listarCleintes.get(i).getDni();
            ob[++cont] = listarCleintes.get(i).getNombre();
            ob[++cont] = listarCleintes.get(i).getApellidoPat();
            ob[++cont] = listarCleintes.get(i).getApellidoMat();
            ob[++cont] = listarCleintes.get(i).getNombreModalidad();
            ob[++cont] = listarCleintes.get(i).getEstado();
            ob[++cont] = listarCleintes.get(i).getNombrePeriodo();
            ob[++cont] = listarCleintes.get(i).getNombreCarrera();
            cont = -1;
            modelo.addRow(ob);
        }
        jTable1.setModel(modelo);
    }

    private void paintForm() {
        if (jTable1.getSelectedRow() != -1) {
            modelo = (DefaultTableModel) jTable1.getModel();
            int rowx = jTable1.getSelectedRow();
            Object valor = jTable1.getValueAt(rowx, 1);
            cDao = new PostulanteDao();
            PostulanteTO d = cDao.buscarEntidad(valor.toString());
            txtDni.setText(d.getDni());
            txtNombre.setText(d.getNombre());
            cbxModalidad.setSelectedItem(d.getNombreModalidad());
            cbxEstado.setSelectedItem(d.getEstado());
            cbxPeriodo.setSelectedItem(d.getNombrePeriodo());
            txtAPaterno.setText(d.getApellidoPat());
            txtAMaterno.setText(d.getApellidoMat());
            txtIdCarrera.setText(String.valueOf(d.getIdCarrera()));
            txtCarrera.setText(d.getNombreCarrera());
            txtDni.setEditable(false);
            btnRegistrar.setText("MODIFICAR");
        } else {
            txtDni.setEditable(true);
        }
    }

    public void resetForm() {
        txtDni.setText("");
        txtNombre.setText("");
        txtAPaterno.setText("");
        txtAMaterno.setText("");
        cbxModalidad.setSelectedIndex(-1);
        cbxEstado.setSelectedIndex(-1);
        cbxPeriodo.setSelectedIndex(-1);
        txtCarrera.setText("");
        txtIdCarrera.setText("");
        txtDni.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAPaterno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAMaterno = new javax.swing.JTextField();
        cbxModalidad = new pe.com.syscenterlife.comboauto.ComboBoxSuggestion();
        jLabel6 = new javax.swing.JLabel();
        cbxEstado = new pe.com.syscenterlife.comboauto.ComboBoxSuggestion();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbxPeriodo = new pe.com.syscenterlife.comboauto.ComboBoxSuggestion();
        jLabel9 = new javax.swing.JLabel();
        txtCarrera = new javax.swing.JTextField();
        txtIdCarrera = new javax.swing.JTextField();
        toastMsg = new pe.edu.upeu.app.componentes.ToastMsg();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Gestión Postulante");

        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnRegistrar)
                    .addComponent(btnEliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("DNI:");

        txtDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniActionPerformed(evt);
            }
        });

        jLabel3.setText("Nombres:");

        jLabel4.setText("A. Paterno:");

        jLabel5.setText("A. Materno:");

        jLabel6.setText("Modalidad:");

        jLabel7.setText("Estado:");

        jLabel8.setText("Periodo:");

        jLabel9.setText("Carrera:");

        txtIdCarrera.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(52, 52, 52)
                                .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(33, 33, 33)
                                .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(23, 23, 23)
                                    .addComponent(txtNombre))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtAPaterno))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbxModalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(toastMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cbxModalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbxPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toastMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "DNI", "Nombres", "A. Paterno", "A. Materno", "Modalidad", "Estado", "Periodo", "Carrera"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        paintForm();
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        List<ValidatorItem> vals = new ArrayList<>();
        vals.add(new ValidatorItem("required|number|min:8|max:8", txtDni,
                "DNI"));
        vals.add(new ValidatorItem("required", txtNombre, "Nombre"));
        vals.add(new ValidatorItem("required", txtAPaterno, "A. Paterno"));
        vals.add(new ValidatorItem("required", txtAMaterno, "A. Materno"));
        //vals.add(new ValidatorItem("required", cbxModalidad, "Modalidad"));
        //vals.add(new ValidatorItem("required", cbxEstado, "Estado"));
        //vals.add(new ValidatorItem("required", cbxPeriodo, "Periodo"));
        vals.add(new ValidatorItem("required", txtCarrera, "Nombre Carrera"));
        vals.add(new ValidatorItem("required", txtIdCarrera, "Id Carrera"));
        //vals.add(new ValidatorItem("required|date", txtDato1, "Fecha"));
        //vals.add(new ValidatorItem("required|email", txtDato2, "Correo"));
        //vals.add(new ValidatorItem("required|date", jDateChooser1,"Fecha"));
        //vals.add(new ValidatorItem("required", jHintTextField2, "TextoHint"));  

        cDao = new PostulanteDao();
        PostulanteTO to = new PostulanteTO();
        to.setDni(txtDni.getText());
        to.setNombre(txtNombre.getText());
        to.setApellidoPat(txtAPaterno.getText());
        to.setApellidoMat(txtAMaterno.getText());
        to.setModalidad(cbxModalidad.getSelectedItem() == null ? "0"
                : ((ComboBoxOption) cbxModalidad.getSelectedItem()).getKey());
        to.setEstado(cbxEstado.getSelectedItem() == null ? ""
                : cbxEstado.getSelectedItem().toString());
        to.setIdPeriodo(Integer.parseInt(cbxPeriodo.getSelectedItem() == null
                ? "0" : ((ComboBoxOption) cbxPeriodo.getSelectedItem()).getKey()));
        to.setIdCarrera(Integer.parseInt(txtIdCarrera.getText().equals("")
                ? "0" : txtIdCarrera.getText()));
        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            try {
                int resultado = cDao.update(to);
                if (resultado != 0) {
                    listarDatos();
                    resetForm();
                    toastMsg.success("Se actualizo correctamente!");
                    //JOptionPane.showMessageDialog(this, "Re registro");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            try {
                Validator validator = new Validator(vals);
                if (validator.isPasses()) {
                    msg = new MsgBox();
                    if (msg.showConfirmCustom("Esta seguro de crear un nuevo cliente ? ", "  Mensaje de Confirmación", "") == 0) {
                        if (cDao.create(to) != 0) {
                            listarDatos();
                            resetForm();
                            toastMsg.success("Se registro correctamente!");
                            //JOptionPane.showMessageDialog(this, "Reregistro");
                        }
                    }
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "Crear Cliente", e);
            }
        }


    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        cDao = new PostulanteDao();
        if (jTable1.getSelectedRowCount() > 0) {
            try {
                modelo = (DefaultTableModel) jTable1.getModel();
                int rowx = jTable1.getSelectedRow();
                Object valor = jTable1.getValueAt(rowx, 1);
                msg = new MsgBox();
                if (msg.showConfirmCustom("Esta seguro de eliminar este registrtro DNI: " + valor + "?", "Mensaje de Confirmación", "") == 0) {
                    modelo.removeRow(rowx);
                    cDao.delete(valor.toString());
                    resetForm();
                    toastMsg.success("Se elimino correctamente!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un item");
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        resetForm();
        btnRegistrar.setText("REGISTRAR");
        txtDni.setEditable(true);
        jTable1.getSelectionModel().clearSelection();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        // TODO add your handling code here:
        txtFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String cadena = (txtFiltro.getText());
                System.out.println("v:" + cadena);
                txtFiltro.setText(cadena);
                repaint();
                trsfiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText())
                );
            }
        });
        System.out.println("llego");
        trsfiltro = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);

    }//GEN-LAST:event_txtFiltroKeyTyped

    private void txtDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegistrar;
    private pe.com.syscenterlife.comboauto.ComboBoxSuggestion cbxEstado;
    private pe.com.syscenterlife.comboauto.ComboBoxSuggestion cbxModalidad;
    private pe.com.syscenterlife.comboauto.ComboBoxSuggestion cbxPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private pe.edu.upeu.app.componentes.ToastMsg toastMsg;
    private javax.swing.JTextField txtAMaterno;
    private javax.swing.JTextField txtAPaterno;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtIdCarrera;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
