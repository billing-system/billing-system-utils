package external.api;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class Processor {

    private static final String REPORT = "[{\"transaction_id\":\"1\", \"transactionResult\":\"Success\"}, " +
            "{\"transaction_id\":\"2\", " + "\"transaction_result\":\"Failed\"}]";

    public String performTransaction(String srcBankAccount,
                                     String dstBankAccount,
                                     BigDecimal amount,
                                     TransactionDirection direction) throws Exception {
        return UUID.randomUUID().toString();
    }

    public String downloadReport() throws Exception {
        return REPORT;
    }
}
