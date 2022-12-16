package michael.linker.msr.core.service.balance;

import michael.linker.msr.core.entity.balance.BalanceEntity;
import michael.linker.msr.core.model.balance.BalanceModel;
import michael.linker.msr.core.repository.balance.BalanceRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceCoreService implements IBalanceCoreService {
    private static final String CACHE_KEY = "balances";
    private final BalanceRepository repository;

    public BalanceCoreService(BalanceRepository repository) {
        this.repository = repository;
    }

    @Override
    @CachePut(value = CACHE_KEY, key = "#model.id.longValue()")
    public void createBalance(BalanceModel model) throws BalanceCoreServiceFailedException {
        if (repository.countById(model.id()) == 0L) {
            repository.saveAndFlush(new BalanceEntity(model));
        } else {
            throw new BalanceCoreServiceFailedException();
        }
    }

    @Override
    @Cacheable(value = CACHE_KEY, key = "#id.longValue()")
    public Optional<Long> getBalance(Long id) {
        BalanceEntity balance = repository.findById(id).orElse(null);
        if (balance != null) {
            return Optional.of(balance.getAmount());
        }
        return Optional.empty();
    }

    @Override
    @CacheEvict(value = CACHE_KEY, key = "#id.longValue()")
    public void changeBalance(Long id, Long amount) throws BalanceCoreServiceFailedException {
        BalanceEntity balance = repository.findById(id)
                .orElseThrow(BalanceCoreServiceFailedException::new);
        balance.setAmount(balance.getAmount() + amount);
        repository.saveAndFlush(balance);
    }

    @Override
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public void removeAllBalances() {
        repository.deleteAllInBatch();
    }
}
