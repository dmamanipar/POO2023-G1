/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pe.edu.upeu.app.gui;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.com.syscenterlife.jtablecomp.ButtonsEditor;
import pe.com.syscenterlife.jtablecomp.ButtonsPanel;
import pe.com.syscenterlife.jtablecomp.ButtonsRenderer;
import pe.edu.upeu.app.conexion.ConnS;
import pe.edu.upeu.app.dao.AreaPeriodoDao;
import pe.edu.upeu.app.dao.AreaPeriodoDaoI;
import pe.edu.upeu.app.modelo.AreaPeriodoTO;
import pe.edu.upeu.app.util.ErrorLogger;
import pe.edu.upeu.app.util.MsgBox;
import pe.edu.upeu.app.util.UtilsX;

/**
 *
 * @author Data_Science
 */
public class MainAreaPeriodo extends javax.swing.JPanel {

    AreaPeriodoDaoI cDao;
    DefaultTableModel modelo;
    MsgBox msg;
    TableRowSorter<TableModel> trsfiltro;
    List<ModeloDataAutocomplet> items;
    static ErrorLogger log = new ErrorLogger(MainAreaPeriodo.class.getName());
    UtilsX util = new UtilsX();

    public MainAreaPeriodo() {
        initComponents();
        listarDatos();
    }

    public void addTableHeader() {
        Object[] newIdentifiers = new Object[]{"#", "idPA", "Area", "Periodo", "Opc"};
        modelo.setColumnIdentifiers(newIdentifiers);
    }

    public void listarDatos() {
        cDao = new AreaPeriodoDao();
        List<AreaPeriodoTO> listarCleintes = cDao.listarTodo();
        jTable1.setAutoCreateRowSorter(true);
        modelo = (DefaultTableModel) jTable1.getModel();
        addTableHeader();
        ButtonsPanel.metaDataButtons = new String[][]{{"", "img/del-icon.png"},
        {"", "img/data-add-icon.png"}};
        jTable1.setRowHeight(40);
        TableColumn column = jTable1.getColumnModel().getColumn(4);
        column.setCellRenderer(new ButtonsRenderer());
        ButtonsEditor be = new ButtonsEditor(jTable1);
        column.setCellEditor(be);

        modelo.setNumRows(0);
        Object[] ob = new Object[5];
        int cont = -1;
        for (int i = 0; i < listarCleintes.size(); i++) {
            ob[++cont] = i + 1;
            ob[++cont] = listarCleintes.get(i).getIdAreaPeriodo();
            ob[++cont] = listarCleintes.get(i).getNombreArea();
            ob[++cont] = listarCleintes.get(i).getNombre();
            ob[++cont] = "";
            cont = -1;
            modelo.addRow(ob);
        }
        jTable1.setModel(modelo);

        JButton ss = be.getCellEditorValue().buttons.get(0);
        ss.addActionListener((ActionEvent e) -> {
            System.out.println("VERRRRRR:");
            int row = jTable1.convertRowIndexToModel(jTable1.getEditingRow());
            Object o = jTable1.getModel().getValueAt(row, 1);
            cDao = new AreaPeriodoDao();
            try {
                cDao.delete(Integer.parseInt(o.toString()));
                listarDatos();
            } catch (Exception ex) {
                System.err.println("Error:" + ex.getMessage());
            }
            System.out.println("AAAA:" + String.valueOf(o));
            JOptionPane.showMessageDialog(this, "Editing: " + o);
        });

        JButton bP = be.getCellEditorValue().buttons.get(1);
        bP.addActionListener((ActionEvent e) -> {
            int row = jTable1.convertRowIndexToModel(jTable1.getEditingRow());
            Object o = jTable1.getModel().getValueAt(row, 1);
            System.out.println("Llega a este boton: " + o.toString());
        });

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
        btnImpExcel = new javax.swing.JButton();
        btnExpExcel = new javax.swing.JButton();
        btnExpFromDB = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        btnImpHeadDB = new javax.swing.JButton();
        btnInsertDB = new javax.swing.JButton();
        btnExpHead = new javax.swing.JButton();
        btnRepPost = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboBoxSuggestion1 = new pe.com.syscenterlife.comboauto.ComboBoxSuggestion();
        jLabel3 = new javax.swing.JLabel();
        comboBoxSuggestion2 = new pe.com.syscenterlife.comboauto.ComboBoxSuggestion();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setText("Gestion Area Periodo");

        btnImpExcel.setText("Imp.Excel");
        btnImpExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpExcelActionPerformed(evt);
            }
        });

        btnExpExcel.setText("Exp.Excel");
        btnExpExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpExcelActionPerformed(evt);
            }
        });

        btnExpFromDB.setText("Exp.FromDB");
        btnExpFromDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpFromDBActionPerformed(evt);
            }
        });

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("jCheckBox1");

        btnImpHeadDB.setText("ImpHeadDB");
        btnImpHeadDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpHeadDBActionPerformed(evt);
            }
        });

        btnInsertDB.setText("InsertDB");
        btnInsertDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertDBActionPerformed(evt);
            }
        });

        btnExpHead.setText("ExpHead");
        btnExpHead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpHeadActionPerformed(evt);
            }
        });

        btnRepPost.setText("R.Postulantes");
        btnRepPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepPostActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addGap(273, 273, 273))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnRepPost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnImpExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExpExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExpFromDB, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImpHeadDB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInsertDB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExpHead)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnImpHeadDB)
                            .addComponent(btnInsertDB)
                            .addComponent(jCheckBox1)
                            .addComponent(btnExpHead))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImpExcel)
                    .addComponent(btnExpExcel)
                    .addComponent(btnExpFromDB)
                    .addComponent(btnRepPost))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jLabel2.setText("Area:");

        jLabel3.setText("Periodo:");

        jButton1.setText("Nuevo");

        jButton2.setText("Registrar");

        jButton3.setText("Eliminar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(comboBoxSuggestion2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboBoxSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboBoxSuggestion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(162, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "IdPA", "Area", "Periodo", "Opc"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(20);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnImpExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpExcelActionPerformed
        // TODO add your handling code here:
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        String defaultCurrentDirectoryPath = util.getFolderExterno("data").toString();
        String[] exs = new String[]{"xls", "xlsx", "xlsm"};
        FileFilter fnef = new FileNameExtensionFilter("Exel Files", exs);
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setFileFilter(fnef);
        excelFileChooser.setDialogTitle("Select Excel File");

        Object[][] data;
        int excelChooser = excelFileChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = excelFileChooser.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelImportToJTable = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);
                System.out.println("Cantidad:" + excelSheet.getLastRowNum());

                int noOfColumns = excelSheet.getRow(0).getPhysicalNumberOfCells();
                System.out.println("Cantidad Col:" + noOfColumns);
                data = new Object[excelSheet.getLastRowNum() + 1][noOfColumns];
                System.out.println("Data F:" + data.length);
                System.out.println("Data C:" + data[0].length);
                for (int row = 0; row < excelSheet.getLastRowNum() + 1; row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
                    System.out.println("Entra!!!");
                    for (int col = 0; col < noOfColumns; col++) {
                        System.out.println("Entra col!!!");
                        //XSSFCell excelName = excelRow.getCell(col);
                        data[row][col] = excelRow.getCell(col);
                    }
                }

                importarenDB(data);
                JOptionPane.showMessageDialog(null, "Imported Successfully !!.....");
            } catch (IOException iOException) {
                JOptionPane.showMessageDialog(null, iOException.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelImportToJTable != null) {
                        excelImportToJTable.close();
                    }
                } catch (IOException iOException) {
                    JOptionPane.showMessageDialog(null, iOException.getMessage());
                }
            }
        }
        listarDatos();
    }//GEN-LAST:event_btnImpExcelActionPerformed

    private void btnExpExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpExcelActionPerformed
        // TODO add your handling code here:
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser(util.getFolderExterno("data").toString()
        );

        excelFileChooser.setDialogTitle("Save As");

        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls",
                "xlsx", "xlsm");

        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(modelo.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "Exported Successfully !!!........");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnExpExcelActionPerformed

    private void btnExpFromDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpFromDBActionPerformed
        // TODO add your handling code here:
        cDao = new AreaPeriodoDao();
        List<AreaPeriodoTO> listarCleintes
                = cDao.listarTodo();

        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser(util.getFolderExterno("data").toString());

        excelFileChooser.setDialogTitle("Save As");

        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls",
                "xlsx", "xlsm");

        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                int j = -1;
                for (int i = 0; i < listarCleintes.size(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    XSSFCell excelCell = excelRow.createCell(++j);
                    excelCell.setCellValue(listarCleintes.get(i).getIdAreaPeriodo());
                    excelCell = excelRow.createCell(++j);
                    excelCell.setCellValue(listarCleintes.get(i).getIdArea());
                    excelCell = excelRow.createCell(++j);
                    excelCell.setCellValue(listarCleintes.get(i).getIdPeriodo());
                    j = -1;
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile()
                        + ".xlsx");

                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "Exported Successfully !!!........");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnExpFromDBActionPerformed

    private void btnImpHeadDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpHeadDBActionPerformed
        // TODO add your handling code here:
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        String defaultCurrentDirectoryPath
                = util.getFolderExterno("data").toString();
        String[] exs = new String[]{"xls", "xlsx",
            "xlsm"};
        FileFilter fnef = new FileNameExtensionFilter("Exel Files", exs);
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setFileFilter(fnef);
        excelFileChooser.setDialogTitle("Select Excel File");
        int excelChooser = excelFileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = excelFileChooser.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelImportToJTable = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);
                System.out.println("Cantidad:" + excelSheet.getLastRowNum());
                int noOfColumns = excelSheet.getRow(0).getPhysicalNumberOfCells();
                Object[] newIdentifiers = new Object[noOfColumns];
                Object[] datax = new Object[noOfColumns];
                modelo.setNumRows(0);
                for (int row = 0; row < excelSheet.getLastRowNum() + 1; row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
                    if (row == 0 && jCheckBox1.isSelected()) {
                        modelo = (DefaultTableModel) jTable1.getModel();
                        for (int col = 0; col < noOfColumns; col++) {
                            newIdentifiers[col] = excelRow.getCell(col);
                        }
                        modelo.setColumnIdentifiers(newIdentifiers);
                    } else {
                        for (int col = 0; col < noOfColumns; col++) {
                            datax[col] = excelRow.getCell(col);
                        }
                        modelo.addRow(datax);
                    }
                }
                JOptionPane.showMessageDialog(null, "Imported Successfully !!.....");
            } catch (IOException iOException) {
                JOptionPane.showMessageDialog(null, iOException.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelImportToJTable != null) {
                        excelImportToJTable.close();
                    }
                } catch (IOException iOException) {
                    JOptionPane.showMessageDialog(null, iOException.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btnImpHeadDBActionPerformed

    private void btnInsertDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertDBActionPerformed
        // TODO add your handling code here:
        AreaPeriodoTO d;
        int j = -1;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            d = new AreaPeriodoTO();
            d.setIdAreaPeriodo((int) Double.parseDouble(String.valueOf(modelo.getValueAt(i, ++j))));
            d.setIdArea((int) Double.parseDouble(String.valueOf(modelo.getValueAt(i, ++j))));
            d.setIdPeriodo((int) Double.parseDouble(String.valueOf(modelo.getValueAt(i, ++j))));
            cDao.create(d);
            j = -1;
        }
        listarDatos();
    }//GEN-LAST:event_btnInsertDBActionPerformed

    private void btnExpHeadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpHeadActionPerformed
        // TODO add your handling code here:
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
//Choose Location For Saving Excel File
        JFileChooser excelFileChooser = new JFileChooser(util.getFolderExterno("data").toString());
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx",
                "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser
                = excelFileChooser.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                int flag = 0;
                if (jCheckBox1.isSelected()) {
                    int size = jTable1.getColumnCount();
                    XSSFRow excelRowh = excelSheet.createRow(0);
                    for (int i = 0; i < size; i++) {
                        XSSFCell excelCell = excelRowh.createCell(i);
                        excelCell.setCellValue(jTable1.getColumnName(i));
                    }
                    flag++;
                }
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i + flag);
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(modelo.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "Exported Successfully !!!........");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnExpHeadActionPerformed

    private void btnRepPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepPostActionPerformed
        // TODO add your handling code here:
        runReport1();
    }//GEN-LAST:event_btnRepPostActionPerformed

    private void runReport1() {
        try {
            ConnS instance = ConnS.getInstance();            
            HashMap param = new HashMap();
            JasperDesign jdesign = JRXmlLoader.load(getFile("reportpost.jrxml"));
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, param, instance.getConnection());                       
            JasperViewer.viewReport(jprint, false);

        } catch (JRException ex) {
            System.out.println("Error:\n" + ex.getLocalizedMessage());
        }
    } 
    
    
    public File getFile(String filex) {
        File newFolder = new File("jasper");
        String ruta = newFolder.getAbsolutePath();
        //CAMINO = Paths.get(ruta+"/"+"reporte1.jrxml");            
        Path CAMINO = Paths.get(ruta + "/" + filex);
        System.out.println("Llegasss Ruta 2:" + CAMINO.toFile().getAbsolutePath());
        return CAMINO.toFile();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExpExcel;
    private javax.swing.JButton btnExpFromDB;
    private javax.swing.JButton btnExpHead;
    private javax.swing.JButton btnImpExcel;
    private javax.swing.JButton btnImpHeadDB;
    private javax.swing.JButton btnInsertDB;
    private javax.swing.JButton btnRepPost;
    private pe.com.syscenterlife.comboauto.ComboBoxSuggestion comboBoxSuggestion1;
    private pe.com.syscenterlife.comboauto.ComboBoxSuggestion comboBoxSuggestion2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void importarenDB(Object[][] data) {
        int j = -1;
        cDao = new AreaPeriodoDao();
        for (Object[] data1 : data) {
            AreaPeriodoTO d = new AreaPeriodoTO();
            d.setIdAreaPeriodo((int) Double.parseDouble(String.valueOf(data1[++j])));
            d.setIdArea((int) Double.parseDouble(String.valueOf(data1[++j])));
            d.setIdPeriodo((int) Double.parseDouble(String.valueOf(data1[++j])));
            cDao.create(d);
            j = -1;
        }
        listarDatos();
    }

}