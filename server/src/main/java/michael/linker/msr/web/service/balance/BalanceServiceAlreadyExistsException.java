package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.exception.BadRequestResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

public class BalanceServiceAlreadyExistsException extends BadRequestResponseStatusException {
    @Value("${text.exception.balanceExists}")
    private String msg;

    public BalanceServiceAlreadyExistsException(Long balanceId) {
        super();
        this.setMessage(String.format(msg, balanceId));
    }
}
