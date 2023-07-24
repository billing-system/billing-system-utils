package dal;

import enums.DbTransactionStatus;
import external.api.TransactionDirection;
import models.db.BillingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<BillingTransaction, String> {


    @Query("SELECT t FROM Transaction t WHERE t.transactionStatus = 'SUCCESS' AND t.dstBankAccount IN " +
            "(SELECT t2.dstBankAccount FROM Transaction t2 WHERE t2.transactionDirection = 'CREDIT') " +
            "AND t.dstBankAccount NOT IN " +
            "(SELECT t3.dstBankAccount FROM Transaction t3 WHERE t3.transactionDirection = 'DEBIT')")
    List<BillingTransaction> findTransactionsForAdvancesWithoutRepaymentPlan();


    @Query("SELECT t FROM Transaction t WHERE t.transactionStatus = :transactionStatus")
    List<BillingTransaction> findByTransactionStatus(@Param("transactionStatus")
                                                             DbTransactionStatus transactionStatus);


    @Query("SELECT t FROM Transaction t WHERE t.dstBankAccount = :dstBankAccount AND " +
            "t.transactionDirection = 'DEBIT' ORDER BY t.transactionTime")
    List<BillingTransaction> findDebitTransactionsByDstBankAccountOrderByTransactionTime(@Param("dstBankAccount")
                                                                                                 String dstBankAccount);


    @Query("SELECT t.dstBankAccount FROM Transaction t WHERE t.transactionDirection = 'DEBIT' AND " +
            "t.transactionStatus = 'SUCCESS' GROUP BY t.dstBankAccount HAVING COUNT(t.id) = :numberOfDebits")
    List<String> findBankAccountsThatFinishedPayingDebits(@Param("numberOfDebits") int numberOfDebits);


    @Query("SELECT t FROM Transaction t WHERE t.dstBankAccount IN :dstBankAccounts")
    List<BillingTransaction> findByDstBankAccountIn(@Param("dstBankAccounts") List<String> dstBankAccounts);


    @Query("SELECT t FROM Transaction t WHERE t.transactionStatus = :transactionStatus AND " +
            "t.transactionDirection = :transactionDirection")
    List<BillingTransaction> findByTransactionStatusAndTransactionDirection(@Param("transactionStatus")
                                                                                    DbTransactionStatus transactionStatus,
                                                                            @Param("transactionDirection")
                                                                                    TransactionDirection transactionDirection);


    @Query("SELECT t FROM Transaction t WHERE t.dstBankAccount = :dstBankAccount AND " +
            "t.transactionDirection = :transactionDirection")
    List<BillingTransaction> findByBankAccountAndTransactionDirection(@Param("dstBankAccount")
                                                                              String dstBankAccount,
                                                                      @Param("transactionDirection")
                                                                              TransactionDirection transactionDirection);


    @Query("SELECT t FROM Transaction t WHERE t.transactionStatus = :transactionStatus AND t.transactionId IN :ids")
    List<BillingTransaction> findByTransactionStatusAndIdIn(@Param("transactionStatus")
                                                                    DbTransactionStatus transactionStatus,
                                                            @Param("ids")
                                                                    List<String> ids);
}
