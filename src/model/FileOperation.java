
package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Invoice;
import model.Item;


public class FileOperation {
    
    private ArrayList<Invoice> invoiceHeader;
      
    public ArrayList<Invoice> read(){
        
        
        JFileChooser chooser = new JFileChooser();

        try {
            JOptionPane.showMessageDialog(null, "Select Invoice Header File",
                    "Invoice Header", JOptionPane.INFORMATION_MESSAGE);
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = chooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> invoiceItems = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
                
                ArrayList<Invoice> invoices = new ArrayList<>();
                for (String invoiceItem : invoiceItems) {
                    try {
                        String[] invoiceSplit = invoiceItem.split(",");
                        int invoice_Num = Integer.parseInt(invoiceSplit[0]);
                        String invoice_Date = invoiceSplit[1];
                        String customer_Name = invoiceSplit[2];

                        Invoice invoice = new Invoice(invoice_Num, invoice_Date, customer_Name);
                        invoices.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                   
                    }
                }
                System.out.println("Check point");
                JOptionPane.showMessageDialog(null, "Select Invoice Line File",
                        "Invoice Line", JOptionPane.INFORMATION_MESSAGE);
                result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = chooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                     List<String> items = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String item : items) {
                        try {
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
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                                //Reminder to load only CSV file and error popup appear when try to choose fault file type
                           }
                        }
                    
                    System.out.println("Check point");
                    
                  }
                
                
                this.invoiceHeader = invoices;  // store invoices array in the class variable
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        return invoiceHeader;
    }
    
    
    
    
    
    public void write(ArrayList<Invoice> invoices)
    {
        for(Invoice inv : invoices)
            
      {
          int invoice_Num = inv.getNum();
          String invoice_Date = inv.getDate();
          String customer_Name = inv.getCustomer();
          System.out.println("\n Invoice " +invoice_Num+ "\n {\n " + invoice_Date+ "," + customer_Name);
          ArrayList<Item> Items = inv.getItems();
          for(Item line : Items)
          {
              System.out.println( line.getItem() + "," + line.getPrice() + "," + line.getCount());
          }
          
          System.out.println(" } \n");
      }
        
    }
    
    
    
    
    
     public static void main(String[] args)
   {
       FileOperation fo = new FileOperation();
       ArrayList<Invoice> invoices = fo.read();
       fo.write(invoices);
              
   }
    
     
}
