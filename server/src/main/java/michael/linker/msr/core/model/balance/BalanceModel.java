package michael.linker.msr.core.model.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import michael.linker.msr.web.model.api.request.CreateBalanceRequest;

@AllArgsConstructor
@Getter
public record BalanceModel(Long id, Long amount) {
    public BalanceModel(CreateBalanceRequest request) {
        this(request.getId(), request.getAmount());
    }
}
