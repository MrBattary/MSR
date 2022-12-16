package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.exception.BadRequestResponseStatusException;

public class BalanceServiceAlreadyExistsException extends BadRequestResponseStatusException {
    private static final String MSG = "The balance with ID %1$s already exists.";

    public BalanceServiceAlreadyExistsException(Long balanceId) {
        super();
        this.setMessage(String.format(MSG, balanceId));
    }
}
