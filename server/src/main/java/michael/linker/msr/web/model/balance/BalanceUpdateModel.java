package michael.linker.msr.web.model.balance;

import michael.linker.msr.web.model.api.request.UpdateBalanceRequest;

public record BalanceUpdateModel(Long amount) {
    public BalanceUpdateModel(UpdateBalanceRequest request) {
        this(request.amount());
    }
}
