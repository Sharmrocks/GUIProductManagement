import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUIproductmanagement extends JFrame implements ActionListener 
{
    private JRadioButton addProduct;
    private JRadioButton viewInventory;
    private JRadioButton deleteProduct;
    private JRadioButton sellProduct;
    private JRadioButton totalSales;
    private JButton executeButton;
    private JTextArea outputArea;
    private ArrayList<Product> products;

    public GUIproductmanagement() 
    {
    	// set up the GUI window
        setTitle("Product Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new FlowLayout());
        
     // create radio buttons for different product management options
        addProduct = new JRadioButton("Add Product");
        viewInventory = new JRadioButton("View Inventory");
        deleteProduct = new JRadioButton("Delete Product");
        sellProduct = new JRadioButton("Sell Product");
        totalSales = new JRadioButton("Total Sales");
        
        // add radio buttons to a ButtonGroup so only one can be selected at a time
        ButtonGroup bg = new ButtonGroup();
        bg.add(addProduct);
        bg.add(viewInventory);
        bg.add(deleteProduct);
        bg.add(sellProduct);
        bg.add(totalSales);
       

     // add button to execute selected action
        executeButton = new JButton("Execute");
        executeButton.addActionListener(this);

     // add text area for displaying output
        outputArea = new JTextArea(20, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        // add components to the GUI window
        add(addProduct);
        add(viewInventory);
        add(deleteProduct);
        add(sellProduct);
        add(totalSales);
        add(executeButton);
        add(scrollPane);

     // create list to store products
        products = new ArrayList<Product>();

        // display the GUI window
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) 
    {
    	 // create a new instance of the GUI
        new GUIproductmanagement();
    }

    public void actionPerformed(ActionEvent e) 
	    {
    	// check which radio button is selected and execute the corresponding action
		        if (e.getSource() == executeButton) 
		        {
		            if (addProduct.isSelected()) 
		            {
		                addProduct();
		            } 
		            else if (viewInventory.isSelected()) 
		            {
		                viewInventory();
		            }
		            else if (deleteProduct.isSelected())
		            {
		                deleteProduct();
		            } 
		            else if (sellProduct.isSelected()) 
		            {
		                sellProduct();
		            } 
		            else if (totalSales.isSelected()) 
		            {
		                totalSales();
		            }	
		        }
	    }

    private void addProduct() 
    {
    	 // create input fields for product name, quantity, price, and checkbox to add to inventory
        JTextField nameField = new JTextField(20);
        JTextField quantityField = new JTextField(10);
        JTextField priceField = new JTextField(10);
        JCheckBox addBox = new JCheckBox();

        // add input fields to a JPanel
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("Add to inventory:"));
        myPanel.add(addBox);
        myPanel.add(new JLabel("Product name:"));
        myPanel.add(nameField);
        myPanel.add(new JLabel("Quantity:"));
        myPanel.add(quantityField);
        myPanel.add(new JLabel("Price:"));
        myPanel.add(priceField);

     // display input fields in a JOptionPane and wait for user input
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter product details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            String name = nameField.getText();
            String quantityString = quantityField.getText();
            String priceString = priceField.getText();
            if (name.isEmpty() || quantityString.isEmpty() || priceString.isEmpty()) 
            {
                JOptionPane.showMessageDialog(null, "Please fill out all fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int quantity = Integer.parseInt(quantityString);
            double price = Double.parseDouble(priceString);
            if (addBox.isSelected()) 
            {
                products.add(new Product(name, quantity, price));
                outputArea.append("Product added: " + name + "\n");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please select the checkbox to add the product to the inventory.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
 // This method displays the current inventory of products in a JTable.
    private void viewInventory() {
        outputArea.setText("");
        String[] columnNames = {"Name", "Quantity", "Price"};
        Object[][] data = new Object[products.size()][3];
        for (int i = 0; i < products.size(); i++) {
            data[i][0] = products.get(i).getName();
            data[i][1] = products.get(i).getQuantity();
            data[i][2] = "₱" + String.format("%.2f", products.get(i).getPrice());
        }
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "PRODUCT INVENTORY", JOptionPane.PLAIN_MESSAGE);
    }

 // This method deletes a product from the inventory based on user input.
    private void deleteProduct() 
    {
        JTextField nameField = new JTextField(20);
        JCheckBox deleteBox = new JCheckBox();

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("Delete from inventory:"));
        myPanel.add(deleteBox);
        myPanel.add(new JLabel("Product name:"));
        myPanel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter product name to delete", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            String name = nameField.getText();
            boolean productFound = false;
            if (deleteBox.isSelected()) 
            {
                for (Product product : products) 
                {
                    if (product.getName().equals(name)) 
                    {
                        products.remove(product);
                        outputArea.append("Product deleted: " + name + "\n");
                        productFound = true;
                        break;
                    }
                }
            }
            if (!productFound) 
            {
                outputArea.append("Product not found: " + name + "\n");
            }
        }
    }

 // This method sells a product from the inventory based on user input.
    private void sellProduct() 
    {
        JComboBox<String> productComboBox = new JComboBox<String>();
        for (Product product : products) 
        {
            productComboBox.addItem(product.getName());
        }

        JTextField quantityField = new JTextField(10);
        JCheckBox sellBox = new JCheckBox();

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("Sell from inventory:"));
        myPanel.add(sellBox);
        myPanel.add(new JLabel("Product name:"));
        myPanel.add(productComboBox);
        myPanel.add(new JLabel("Quantity:"));
        myPanel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter product details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            String productName = (String) productComboBox.getSelectedItem();
            int quantity = 0;
            try 
            {
                quantity = Integer.parseInt(quantityField.getText());
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean productFound = false;
            for (Product product : products)
            {
                if (product.getName().equals(productName)) 
                {
                    if (product.getQuantity() >= quantity) 
                    {
                        if (sellBox.isSelected()) 
                        {
                            product.sell(quantity);
                            outputArea.append("Product sold: " + productName + "\n");
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Please select the checkbox to sell the product from the inventory.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(null, "Not enough quantity in inventory.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    productFound = true;
                    break;
                }
            }
            if (!productFound) 
            {
                JOptionPane.showMessageDialog(null, "Product not found: " + productName,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

 // This method to see the total sales 
    private void totalSales() {
        double totalSales = 0;
        for (Product product : products) {
            totalSales +=  product.getSales();
        }
        JOptionPane.showMessageDialog(null, "Total sales: ₱" + String.format("%.2f", totalSales),
                "Total Sales", JOptionPane.INFORMATION_MESSAGE);
    }

    private class Product 
    {
        private String name;     // A private String variable to store the product name.
        private int quantity;    // A private integer variable to store the quantity of the product.
        private double price;    // A private double variable to store the price of the product.
        private double sales;    // A private double variable to store the total sales of the product.

        // Constructor to initialize the variables name, quantity, and price.
        public Product(String name, int quantity, double price)
        {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.sales = 0;
        }

        // Getter method to return the name of the product.
        public String getName() 
        {
            return name;
        }

        // Getter method to return the quantity of the product.
        public int getQuantity() 
        {
            return quantity;
        }

        // Getter method to return the price of the product.
        public double getPrice() 
        {
            return price;
        }
        
        // Setter method to set the quantity of the product.
        public void setQuantity(int quantity)
        {
        	this.quantity = quantity;
        }

        // Method to sell the product and update the quantity and sales.
        public void sell(int quantity) 
        {
            this.quantity -= quantity;              // Reduce the quantity of the product.
            this.sales += quantity * price;         // Increase the total sales by the selling price multiplied by the sold quantity.
        }

        // Getter method to return the total sales of the product.
        public double getSales() 
        {
            return sales;
        }
        
    } 
}
