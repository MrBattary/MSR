package michael.linker.msr.web.service.balance;

import michael.linker.msr.core.service.balance.IBalanceCoreService;
import michael.linker.msr.web.model.api.request.CreateBalanceRequest;
import michael.linker.msr.web.model.api.request.UpdateBalanceRequest;
import michael.linker.msr.web.model.api.response.GetBalanceResponse;

/**
 * General interface for the web balance service implementation.
 * Used by BalanceController, uses IBalanceCoreService impl.
 *
 * @see michael.linker.msr.web.controller.BalanceController
 * @see IBalanceCoreService
 */
public interface IBalanceWebService {
    /**
     * Create a balance according to the specified request model.
     *
     * @param request contains ID and amount balance data.
     * @throws BalanceServiceAlreadyExistsException if balance with provided ID already exists.
     */
    void createBalance(CreateBalanceRequest request) throws BalanceServiceAlreadyExistsException;

    /**
     * Get the balance model by the specified balance ID.
     *
     * @param balanceId ID of the balance.
     * @return a model containing data about the amount on the balance.
     * @throws BalanceServiceNotFoundException if balance with provided ID was not found.
     */
    GetBalanceResponse getBalance(Long balanceId) throws BalanceServiceNotFoundException;

    /**
     * Update specified balance according to the specified request model.
     *
     * @param balanceId ID of the balance.
     * @param request new balance amount.
     * @throws BalanceServiceNotFoundException if balance with provided ID was not found.
     */
    void updateBalance(Long balanceId, UpdateBalanceRequest request) throws BalanceServiceNotFoundException;

    /**
     * Remove all balances.
     */
    void removeAllBalances();
}
