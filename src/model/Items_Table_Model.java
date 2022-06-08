
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class Items_Table_Model extends AbstractTableModel {

    private ArrayList<Item> lines;
    private String[] columns = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public Items_Table_Model(ArrayList<Item> lines) {
        this.lines = lines;
    }

    public ArrayList<Item> getLines() {
        return lines;
    }
    
    
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int x) {
        return columns[x];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item line = lines.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return line.getInvoice().getNum();
            case 1: return line.getItem();
            case 2: return line.getPrice();
            case 3: return line.getCount();
            case 4: return line.getItemTotal();
            default : return "";
        }
    }
    
}
