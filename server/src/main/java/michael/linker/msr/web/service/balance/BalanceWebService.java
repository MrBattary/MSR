package michael.linker.msr.web.service.balance;

import michael.linker.msr.core.model.balance.BalanceModel;
import michael.linker.msr.core.service.balance.BalanceCoreServiceFailedException;
import michael.linker.msr.core.service.balance.IBalanceCoreService;
import michael.linker.msr.web.model.api.request.CreateBalanceRequest;
import michael.linker.msr.web.model.api.request.UpdateBalanceRequest;
import michael.linker.msr.web.model.api.response.GetBalanceResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceWebService implements IBalanceWebService {
    // TODO (ML): Provide constructor for the field
    private IBalanceCoreService coreService;

    @Override
    public void createBalance(CreateBalanceRequest request) throws BalanceServiceAlreadyExistsException {
        try {
            coreService.createBalance(new BalanceModel(request));
        } catch (BalanceCoreServiceFailedException e) {
            throw new BalanceServiceAlreadyExistsException(request.id());
        }
    }

    @Override
    public GetBalanceResponse getBalance(Long balanceId) throws BalanceServiceNotFoundException {
        Optional<Long> amount = coreService.getBalance(balanceId);
        if (amount.isEmpty()) {
            throw new BalanceServiceNotFoundException(balanceId);
        }

        return new GetBalanceResponse(amount.get());
    }

    @Override
    public void updateBalance(Long balanceId, UpdateBalanceRequest request) throws BalanceServiceNotFoundException {
        try {
            coreService.changeBalance(balanceId, request.amount());
        } catch (BalanceCoreServiceFailedException e) {
            throw new BalanceServiceNotFoundException(balanceId);
        }
    }

    @Override
    public void removeAllBalances() {
        coreService.removeAllBalances();
    }
}
