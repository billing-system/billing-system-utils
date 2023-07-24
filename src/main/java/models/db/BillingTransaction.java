package models.db;

import enums.DbTransactionStatus;
import external.api.TransactionDirection;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "Transaction")
public class BillingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String transactionId;
    private Instant transactionTime;
    private String dstBankAccount;
    private TransactionDirection transactionDirection;
    private DbTransactionStatus transactionStatus;
    private double amount;

    @Version
    private int version;
}
