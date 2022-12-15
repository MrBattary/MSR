package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.exception.NotFoundResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

public class BalanceServiceNotFoundException extends NotFoundResponseStatusException {
    @Value("${text.exception.balanceNotFound}")
    private String msg;

    public BalanceServiceNotFoundException(Long balanceId) {
        super();
        this.setMessage(String.format(msg, balanceId));
    }
}
