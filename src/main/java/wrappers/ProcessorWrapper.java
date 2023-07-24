package wrappers;

import exceptions.ProcessorException;
import external.api.Processor;
import external.api.TransactionDirection;
import logger.BillingSystemLogger;

import java.math.BigDecimal;
import java.util.logging.Level;

public class ProcessorWrapper {

    private final Processor processor;
    private final BillingSystemLogger logger;

    public ProcessorWrapper(Processor processor, BillingSystemLogger logger) {
        this.processor = processor;
        this.logger = logger;
    }

    public String downloadReport() throws ProcessorException {
        try {
            return tryDownloadingReport();
        } catch (Exception e) {
            throw new ProcessorException("An error occurred while trying to download report of transaction " +
                    "results: " + e.getMessage(), e);
        }
    }

    public String performTransaction(String srcBankAccount,
                                     String dstBankAccount,
                                     BigDecimal amount,
                                     TransactionDirection direction) throws ProcessorException {
        try {
            return tryPerformingTransaction(srcBankAccount, dstBankAccount, amount, direction);
        } catch (Exception e) {
            throw new ProcessorException("An error occurred while trying to perform " +
                    direction.name().toLowerCase() + " transaction from bank " + srcBankAccount + " to bank "
                    + dstBankAccount + ", of amount" + amount, e);
        }
    }

    private String tryDownloadingReport() throws Exception {
        String report = processor.downloadReport();
        logger.log(Level.FINE, "Finished downloading report of transaction results");
        logger.log(Level.FINEST, "Info about the report downloaded: " + report);

        return report;
    }

    private String tryPerformingTransaction(String srcBankAccount,
                                            String dstBankAccount,
                                            BigDecimal amount,
                                            TransactionDirection direction) throws Exception {
        String transactionId = processor.performTransaction(srcBankAccount, dstBankAccount, amount, direction);

        logger.log(Level.FINE, "Performed " + direction.name().toLowerCase() + " transaction from bank "
                + srcBankAccount + " to bank " + dstBankAccount + ", of amount " + amount + "");

        return transactionId;
    }
}
