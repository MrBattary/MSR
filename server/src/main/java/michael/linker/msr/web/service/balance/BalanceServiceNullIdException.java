package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.exception.BadRequestResponseStatusException;

public class BalanceServiceNullIdException extends BadRequestResponseStatusException {
    private static final String MSG = "The provided ID is null.";

    public BalanceServiceNullIdException() {
        super();
        this.setMessage(MSG);
    }
}
