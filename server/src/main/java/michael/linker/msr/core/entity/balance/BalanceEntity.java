package michael.linker.msr.core.entity.balance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import michael.linker.msr.core.model.balance.BalanceModel;

import java.io.Serializable;

@Entity
@Table(name = "balance")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BalanceEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Long amount;

    public BalanceEntity(Long id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public BalanceEntity(BalanceModel model) {
        id = model.id();
        amount = model.amount();
    }
}
