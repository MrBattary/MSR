package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.exception.NotFoundResponseStatusException;

public class BalanceServiceNotFoundException extends NotFoundResponseStatusException {
    private static final String MSG = "The balance with ID %1$s was not found.";

    public BalanceServiceNotFoundException(Long balanceId) {
        super(String.format(MSG, balanceId));
    }
}
