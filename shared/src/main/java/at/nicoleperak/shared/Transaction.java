package at.nicoleperak.shared;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Category category;
    private String transactionPartner;
    private String note;
    private boolean addedAutomatically;

    public Transaction() {
    }

    public Transaction(Long id, String description, BigDecimal amount, LocalDate date, Category category, String transactionPartner, String note, boolean addedAutomatically) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.transactionPartner = transactionPartner;
        this.note = note;
        this.addedAutomatically = addedAutomatically;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public String getTransactionPartner() {
        return transactionPartner;
    }

    public String getNote() {
        return note;
    }

    public boolean isAddedAutomatically() {
        return addedAutomatically;
    }
}
