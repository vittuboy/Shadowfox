import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManagementSystem extends JFrame {
    private static class InventoryItem {
        private String id;
        private String name;
        private int quantity;
        private double price;
        
        public InventoryItem(String id, String name, int quantity, double price) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
        
        public void setName(String name) { this.name = name; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public void setPrice(double price) { this.price = price; }
        
        public Object[] toRowData() {
            return new Object[] {id, name, quantity, price};
        }
    }
    
    private List<InventoryItem> inventory;
    
    private JTable itemTable;
    private DefaultTableModel tableModel;
    
    private JTextField idField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;
    
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    
    public InventoryManagementSystem() {
        inventory = new ArrayList<>();
        
        setTitle("Inventory Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        initializeComponents();
        setupLayout();
        setupListeners();
        
        setVisible(true);
    }
    
    private void initializeComponents() {
        String[] columns = {"ID", "Name", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        itemTable = new JTable(tableModel);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        idField = new JTextField(10);
        nameField = new JTextField(20);
        quantityField = new JTextField(5);
        priceField = new JTextField(10);
        
        addButton = new JButton("Add Item");
        updateButton = new JButton("Update Item");
        deleteButton = new JButton("Delete Item");
        clearButton = new JButton("Clear Form");
    }
    
    private void setupLayout() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
        itemTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && itemTable.getSelectedRow() != -1) {
                int selectedRow = itemTable.getSelectedRow();
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                quantityField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                
                idField.setEditable(false);
            }
        });
    }
    
    private void addItem() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();
        
        if (id.isEmpty() || name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
            
            if (quantity < 0 || price < 0) {
                JOptionPane.showMessageDialog(this, "Quantity and price must be positive!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            for (InventoryItem item : inventory) {
                if (item.getId().equals(id)) {
                    JOptionPane.showMessageDialog(this, "Item with this ID already exists!", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            InventoryItem newItem = new InventoryItem(id, name, quantity, price);
            inventory.add(newItem);
            tableModel.addRow(newItem.toRowData());
            
            clearForm();
            JOptionPane.showMessageDialog(this, "Item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and price must be numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateItem() {
        int selectedRow = itemTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to update!", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();
        
        if (id.isEmpty() || name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
            
            if (quantity < 0 || price < 0) {
                JOptionPane.showMessageDialog(this, "Quantity and price must be positive!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            for (InventoryItem item : inventory) {
                if (item.getId().equals(id)) {
                    item.setName(name);
                    item.setQuantity(quantity);
                    item.setPrice(price);
                    
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(quantity, selectedRow, 2);
                    tableModel.setValueAt(price, selectedRow, 3);
                    
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and price must be numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteItem() {
        int selectedRow = itemTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete!", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            inventory.removeIf(item -> item.getId().equals(id));
            tableModel.removeRow(selectedRow);
            clearForm();
            JOptionPane.showMessageDialog(this, "Item deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        itemTable.clearSelection();
        idField.setEditable(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InventoryManagementSystem();
            }
        });
    }
}