package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.exception.BadRequestResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

public class BalanceServiceBadDataException extends BadRequestResponseStatusException {
    @Value("${text.exception.balanceIdNull}")
    private String msg;

    public BalanceServiceBadDataException() {
        super();
        this.setMessage(msg);
    }
}
