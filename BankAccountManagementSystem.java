import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Transaction {
    private double amount;
    private String type;
    private Date timestamp;
    
    public Transaction(double amount, String type) {
        this.amount = amount;
        this.type = type;
        this.timestamp = new Date();
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getType() {
        return type;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return type + ": $" + amount + " on " + timestamp;
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private List<Transaction> transactionHistory;
    
    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        balance += amount;
        transactionHistory.add(new Transaction(amount, "DEPOSIT"));
    }
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
        
        balance -= amount;
        transactionHistory.add(new Transaction(amount, "WITHDRAWAL"));
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}

class Bank {
    private List<BankAccount> accounts;
    
    public Bank() {
        this.accounts = new ArrayList<>();
    }
    
    public BankAccount createAccount(String accountHolder) {
        String accountNumber = generateAccountNumber();
        BankAccount account = new BankAccount(accountNumber, accountHolder);
        accounts.add(account);
        return account;
    }
    
    private String generateAccountNumber() {
        return "ACC" + (accounts.size() + 1001);
    }
    
    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount account;
    
    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC1001", "John Doe");
    }
    
    @Test
    void testInitialBalance() {
        assertEquals(0.0, account.getBalance());
    }
    
    @Test
    void testDeposit() {
        account.deposit(100.0);
        assertEquals(100.0, account.getBalance());
        assertEquals(1, account.getTransactionHistory().size());
        assertEquals("DEPOSIT", account.getTransactionHistory().get(0).getType());
        assertEquals(100.0, account.getTransactionHistory().get(0).getAmount());
    }
    
    @Test
    void testMultipleDeposits() {
        account.deposit(100.0);
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance());
        assertEquals(2, account.getTransactionHistory().size());
    }
    
    @Test
    void testNegativeDeposit() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
    }
    
    @Test
    void testWithdraw() throws InsufficientFundsException {
        account.deposit(100.0);
        account.withdraw(50.0);
        assertEquals(50.0, account.getBalance());
        assertEquals(2, account.getTransactionHistory().size());
        assertEquals("WITHDRAWAL", account.getTransactionHistory().get(1).getType());
        assertEquals(50.0, account.getTransactionHistory().get(1).getAmount());
    }
    
    @Test
    void testInsufficientFundsWithdrawal() {
        account.deposit(100.0);
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(150.0);
        });
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    void testNegativeWithdrawal() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50.0);
        });
    }
    
    @Test
    void testTransactionHistory() throws InsufficientFundsException {
        account.deposit(100.0);
        account.withdraw(30.0);
        account.deposit(50.0);
        
        List<Transaction> history = account.getTransactionHistory();
        assertEquals(3, history.size());
        assertEquals("DEPOSIT", history.get(0).getType());
        assertEquals(100.0, history.get(0).getAmount());
        assertEquals("WITHDRAWAL", history.get(1).getType());
        assertEquals(30.0, history.get(1).getAmount());
        assertEquals("DEPOSIT", history.get(2).getType());
        assertEquals(50.0, history.get(2).getAmount());
    }
}

class BankTest {
    private Bank bank;
    
    @BeforeEach
    void setUp() {
        bank = new Bank();
    }
    
    @Test
    void testCreateAccount() {
        BankAccount account = bank.createAccount("Jane Doe");
        assertNotNull(account);
        assertEquals("Jane Doe", account.getAccountHolder());
        assertEquals("ACC1001", account.getAccountNumber());
        assertEquals(1, bank.getAllAccounts().size());
    }
    
    @Test
    void testMultipleAccounts() {
        bank.createAccount("Jane Doe");
        bank.createAccount("John Smith");
        assertEquals(2, bank.getAllAccounts().size());
        assertEquals("ACC1002", bank.getAllAccounts().get(1).getAccountNumber());
    }
    
    @Test
    void testFindAccount() {
        BankAccount account1 = bank.createAccount("Jane Doe");
        BankAccount account2 = bank.createAccount("John Smith");
        
        BankAccount found = bank.findAccount("ACC1001");
        assertEquals(account1, found);
        
        found = bank.findAccount("ACC1002");
        assertEquals(account2, found);
        
        found = bank.findAccount("NONEXISTENT");
        assertNull(found);
    }
}

public class BankAccountManagementSystem {
    public static void main(String[] args) {
        Bank bank = new Bank();
        
        BankAccount account1 = bank.createAccount("John Doe");
        account1.deposit(1000.0);
        
        BankAccount account2 = bank.createAccount("Jane Smith");
        account2.deposit(2000.0);
        
        try {
            account1.withdraw(500.0);
            account2.deposit(250.0);
            account1.deposit(100.0);
            account2.withdraw(1000.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("Account: " + account1.getAccountNumber() + " - " + account1.getAccountHolder());
        System.out.println("Balance: $" + account1.getBalance());
        System.out.println("Transaction History:");
        for (Transaction transaction : account1.getTransactionHistory()) {
            System.out.println("- " + transaction);
        }
        
        System.out.println("\nAccount: " + account2.getAccountNumber() + " - " + account2.getAccountHolder());
        System.out.println("Balance: $" + account2.getBalance());
        System.out.println("Transaction History:");
        for (Transaction transaction : account2.getTransactionHistory()) {
            System.out.println("- " + transaction);
        }
    }
}