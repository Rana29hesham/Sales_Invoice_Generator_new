
package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Invoice_Dialog_Screen extends JDialog {
    private JTextField customer_Name_Field;
    private JTextField invoice_Date_Field;
    private JLabel customer_Name_label;
    private JLabel invoice_Date_label;
    private JButton ok_button;
    private JButton cancel_button;

    public Invoice_Dialog_Screen(Invoice_Frame_Screen frame) {
        customer_Name_label = new JLabel("Customer Name:");
        customer_Name_Field = new JTextField(20);
        invoice_Date_label = new JLabel("Invoice Date:");
        invoice_Date_Field = new JTextField(20);
        ok_button = new JButton("OK");
        cancel_button = new JButton("Cancel");
        
        ok_button.setActionCommand("createInvoiceOK");
        cancel_button.setActionCommand("createInvoiceCancel");
        
        ok_button.addActionListener(frame.getController());
        cancel_button.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoice_Date_label);
        add(invoice_Date_Field);
        add(customer_Name_label);
        add(customer_Name_Field);
        add(ok_button);
        add(cancel_button);
        pack();
        
    }

    public JTextField getCustomerNameField() {
        return customer_Name_Field;
    }

    public JTextField getInvoiceDateField() {
        return invoice_Date_Field;
    }
    
}
