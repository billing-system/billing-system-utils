package logger;

import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BillingSystemLogger {

    private final Logger logger;

    public BillingSystemLogger() {
        logger = Logger.getLogger(BillingSystemLogger.class.getName());
    }

    public void log(Level logLevel, String message) {
        logger.log(logLevel, message);
    }
}
