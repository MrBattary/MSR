package michael.linker.msr.core.service.balance;

import michael.linker.msr.core.model.balance.BalanceModel;

import java.util.Optional;

/**
 * General interface for the core balance service implementation.
 * Is an extended interface from the task, method contracts from the task are saved.
 * Used by IBalanceWebService impl.
 *
 * @see michael.linker.msr.web.service.balance.IBalanceWebService
 */
public interface IBalanceCoreService {

    /**
     * Checks if the balance exists, and if it does not exist, adds the balance to the repository.
     *
     * @param model balance model.
     * @return true if successfully added, false otherwise.
     */
    boolean createBalance(BalanceModel model);

    /**
     * Getting a balance.
     *
     * @param id bank account ID.
     * @return the amount of money in the bank account.
     */
    Optional<Long> getBalance(Long id);

    /**
     * Changing the balance by a certain value.
     *
     * @param id     bank account ID.
     * @param amount the amount of money to be added to the bank account.
     */
    void changeBalance(Long id, Long amount);

    /**
     * Remove all balances.
     */
    void removeAllBalances();
}
