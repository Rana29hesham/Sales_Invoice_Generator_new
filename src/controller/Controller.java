package controller;

import model.Invoice;
import model.Invoices_Table_Model;
import model.Item;
import model.Items_Table_Model;
import view.Invoice_Dialog_Screen;
import view.Invoice_Frame_Screen;
import view.Item_Dialog_Screen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private Invoice_Frame_Screen invoice_frame;
    private Invoice_Dialog_Screen invoice_dialog_screen;
    private Item_Dialog_Screen item_dialog_screen;

    public Controller(Invoice_Frame_Screen invoice_frame) {
        this.invoice_frame = invoice_frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionName = e.getActionCommand();
        switch (actionName) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoiceButton();
                break;
            case "Delete Invoice":
                deleteInvoiceButton();
                break;
            case "Create New Item":
                createNewItemButton();
                break;
            case "Delete Item":
                deleteItemButton();
                break;

            case "createInvoiceOK":
                createInvoiceOKButton();
                break;
            case "createInvoiceCancel":
                createInvoiceCancelButton();
                break;

            case "createItemOK":
                createItemOKButton();
                break;
                
            case "createItemCancel":
                createItemCancelButton();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selected_row_Index = invoice_frame.getInvoiceTable().getSelectedRow();
        if (selected_row_Index != -1) {
            Invoice currentInvoice = invoice_frame.getInvoices().get(selected_row_Index);
            invoice_frame.getInvoice_Num_Label().setText("" + currentInvoice.getNum());
            invoice_frame.getCustomer_Name_Label().setText(currentInvoice.getCustomer());
            invoice_frame.getInvoice_Date_Label().setText(currentInvoice.getDate());
            invoice_frame.getInvoice_Total_Label().setText("" + currentInvoice.getInvoiceTotal());
            Items_Table_Model itemsTableModel = new Items_Table_Model(currentInvoice.getItems());
            invoice_frame.getItemTable().setModel(itemsTableModel);
            itemsTableModel.fireTableDataChanged();
        }
    }

    private void loadFile() {
        JFileChooser chooser = new JFileChooser();
        try {
            int action = chooser.showOpenDialog(invoice_frame);
            if (action == JFileChooser.APPROVE_OPTION) {
                File headerFile = chooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> invoiceItems = Files.readAllLines(headerPath);
                  System.out.println("Invoices have been read");
                ArrayList<Invoice> invoices = new ArrayList<>();
                for (String invoiceItem : invoiceItems) {
                    String[] invoiceSplit = invoiceItem.split(",");
                    int invoice_Num = Integer.parseInt(invoiceSplit[0]);
                    String invoice_Date = invoiceSplit[1];
                    String customer_Name = invoiceSplit[2];
                    Invoice invoice = new Invoice(invoice_Num, invoice_Date, customer_Name);
                    invoices.add(invoice);
                }
                
                System.out.println("Check point");
                action = chooser.showOpenDialog(invoice_frame);
                if (action == JFileChooser.APPROVE_OPTION) {
                    File lineFile = chooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> items = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String item : items) {
                        String[] itemSplit = item.split(",");
                        int invoice_Num = Integer.parseInt(itemSplit[0]);
                        String item_Name = itemSplit[1];
                        double item_Price = Double.parseDouble(itemSplit[2]);
                        int count = Integer.parseInt(itemSplit[3]);
                        Invoice inv = null;
                        for (Invoice invoice : invoices) {
                            if (invoice.getNum() == invoice_Num) {
                                inv = invoice;
                                break;
                            }
                        }

                        Item it = new Item(item_Name, item_Price, count, inv);
                        inv.getItems().add(it);
                    }
             
                }
                invoice_frame.setInvoices(invoices);
                Invoices_Table_Model invoicesTableModel = new Invoices_Table_Model(invoices);
                invoice_frame.setInvoicesTableModel(invoicesTableModel);
                invoice_frame.getInvoiceTable().setModel(invoicesTableModel);
                invoice_frame.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveFile() {
        ArrayList<Invoice> invoices = invoice_frame.getInvoices();
        String headers = "";
        String items = "";
        for (Invoice invoice : invoices) {
            String invoice_CSV = invoice.getAsCSV();
            headers += invoice_CSV;
            headers += "\n";

            for (Item item : invoice.getItems()) {
                String item_CSV = item.getAsCSV();
                items += item_CSV;
                items += "\n";
            }
        }
        try {
            JFileChooser chooser = new JFileChooser();
            int action = chooser.showSaveDialog(invoice_frame);
            if (action == JFileChooser.APPROVE_OPTION) {
                File invoiceFile = chooser.getSelectedFile();
                FileWriter filewriter = new FileWriter(invoiceFile);
                filewriter.write(headers);
                filewriter.flush();
                filewriter.close();
                action = chooser.showSaveDialog(invoice_frame);
                if (action == JFileChooser.APPROVE_OPTION) {
                    File itemFile = chooser.getSelectedFile();
                    FileWriter fw = new FileWriter(itemFile);
                    fw.write(items);
                    fw.flush();
                    fw.close();
                }
            }
        } catch (Exception e) {
            e.getStackTrace();

        }
    }

    private void createNewInvoiceButton() {
        invoice_dialog_screen = new Invoice_Dialog_Screen(invoice_frame);
        invoice_dialog_screen.setVisible(true);
    }

    private void deleteInvoiceButton() {
        int selected_Invoice_Row = invoice_frame.getInvoiceTable().getSelectedRow();
        if (selected_Invoice_Row != -1) {
            invoice_frame.getInvoices().remove(selected_Invoice_Row);
            invoice_frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItemButton() {
        item_dialog_screen = new Item_Dialog_Screen(invoice_frame);
        item_dialog_screen.setVisible(true);
    }

    private void deleteItemButton() {
        int selected_Item_Row = invoice_frame.getItemTable().getSelectedRow();

        if (selected_Item_Row != -1) {
            Items_Table_Model itemsTableModel = (Items_Table_Model) invoice_frame.getItemTable().getModel();
            itemsTableModel.getLines().remove(selected_Item_Row);
            itemsTableModel.fireTableDataChanged();
            invoice_frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceOKButton() {
        String date = invoice_dialog_screen.getInvoiceDateField().getText();
        String customerName = invoice_dialog_screen.getCustomerNameField().getText();
        int num = invoice_frame.getNext_Invoice_Num();

        Invoice invoice = new Invoice(num, date, customerName);
        invoice_frame.getInvoices().add(invoice);
        invoice_frame.getInvoicesTableModel().fireTableDataChanged();
        invoice_dialog_screen.setVisible(false);
        invoice_dialog_screen.dispose();
        invoice_dialog_screen = null;
    }

    private void createInvoiceCancelButton() {
        invoice_dialog_screen.setVisible(false);
        invoice_dialog_screen.dispose();
        invoice_dialog_screen = null;
    }

    private void createItemOKButton() {
        String item = item_dialog_screen.getItemNameField().getText();
        String Count = item_dialog_screen.getItemCountField().getText();
        String Price = item_dialog_screen.getItemPriceField().getText();
        int count = Integer.parseInt(Count);
        double price = Double.parseDouble(Price);
        int select_Invoice = invoice_frame.getInvoiceTable().getSelectedRow();
        if (select_Invoice != -1) {
            Invoice invoice = invoice_frame.getInvoices().get(select_Invoice);
            Item it = new Item(item, price, count, invoice);
            invoice.getItems().add(it);
            Items_Table_Model itemsTableModel = (Items_Table_Model) invoice_frame.getItemTable().getModel();
            itemsTableModel.fireTableDataChanged();
            invoice_frame.getInvoicesTableModel().fireTableDataChanged();
        }
        item_dialog_screen.setVisible(false);
        item_dialog_screen.dispose();
        item_dialog_screen = null;
    }

    private void createItemCancelButton() {
        item_dialog_screen.setVisible(false);
        item_dialog_screen.dispose();
        item_dialog_screen = null;
    }
    
    
    

}
