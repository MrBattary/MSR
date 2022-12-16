package michael.linker.msr.core.model.balance;

import michael.linker.msr.web.model.api.request.CreateBalanceRequest;

public record BalanceModel(Long id, Long amount) {
    public BalanceModel(CreateBalanceRequest request) {
        this(request.id(), request.amount());
    }
}
