package michael.linker.msr.core.model.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public record BalanceModel(Long id, Long amount) {
}
