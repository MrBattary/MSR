package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.model.api.request.CreateBalanceRequest;
import michael.linker.msr.web.model.api.request.UpdateBalanceRequest;
import michael.linker.msr.web.model.api.response.GetBalanceResponse;

/**
 * General interface for the web balance service implementation.
 * Used by BalanceController, uses BalanceCoreService.
 *
 * @see michael.linker.msr.web.controller.BalanceController
 * @see michael.linker.msr.core.service.IBalanceCoreService
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
     * Update a balance according to the specified request model.
     *
     * @param request new balance amount.
     * @throws BalanceServiceNotFoundException if balance with provided ID was not found.
     */
    void updateBalance(UpdateBalanceRequest request) throws BalanceServiceNotFoundException;

    /**
     * Remove all balances.
     */
    void removeAllBalances();
}
