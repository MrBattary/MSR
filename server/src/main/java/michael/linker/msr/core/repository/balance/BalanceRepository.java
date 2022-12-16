package michael.linker.msr.core.repository.balance;

import michael.linker.msr.core.entity.balance.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
    long countById(Long id);
}
