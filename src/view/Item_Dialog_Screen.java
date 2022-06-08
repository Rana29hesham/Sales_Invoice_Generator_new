
package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Item_Dialog_Screen extends JDialog{
    private JTextField item_Name_Field;
    private JTextField item_Count_Field;
    private JTextField item_Price_Field;
    private JLabel item_Name_Label;
    private JLabel item_Count_Label;
    private JLabel item_Price_Label;
    private JButton ok_button;
    private JButton cancel_button;
    
    public Item_Dialog_Screen(Invoice_Frame_Screen frame) {
        item_Name_Field = new JTextField(20);
        item_Name_Label = new JLabel("Item Name");
        
        item_Count_Field = new JTextField(20);
        item_Count_Label = new JLabel("Item Count");
        
        item_Price_Field = new JTextField(20);
        item_Price_Label = new JLabel("Item Price");
        
        ok_button = new JButton("OK");
        cancel_button = new JButton("Cancel");
        
        ok_button.setActionCommand("createItemOK");
        cancel_button.setActionCommand("createItemCancel");
        
        ok_button.addActionListener(frame.getController());
        cancel_button.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(item_Name_Label);
        add(item_Name_Field);
        add(item_Count_Label);
        add(item_Count_Field);
        add(item_Price_Label);
        add(item_Price_Field);
        add(ok_button);
        add(cancel_button);
        
        pack();
    }

    public JTextField getItemNameField() {
        return item_Name_Field;
    }

    public JTextField getItemCountField() {
        return item_Count_Field;
    }

    public JTextField getItemPriceField() {
        return item_Price_Field;
    }
}
