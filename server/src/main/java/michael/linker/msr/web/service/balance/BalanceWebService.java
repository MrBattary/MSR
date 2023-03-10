package michael.linker.msr.web.service.balance;

import michael.linker.msr.web.model.balance.BalanceModel;
import michael.linker.msr.core.service.balance.BalanceCoreServiceFailedException;
import michael.linker.msr.core.service.balance.IBalanceCoreService;
import michael.linker.msr.web.model.api.response.GetBalanceResponse;
import michael.linker.msr.web.model.balance.BalanceUpdateModel;
import michael.linker.msr.web.util.validation.balance.BalanceValidation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceWebService implements IBalanceWebService {
    private final IBalanceCoreService coreService;

    public BalanceWebService(IBalanceCoreService coreService) {
        this.coreService = coreService;
    }

    @Override
    public void createBalance(BalanceModel request) throws BalanceServiceAlreadyExistsException {
        try {
            coreService.createBalance(request);
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
    public void updateBalance(Long balanceId, BalanceUpdateModel request) throws BalanceServiceNotFoundException {
        if (BalanceValidation.isAmountValid(request.amount())) {
            try {
                coreService.changeBalance(balanceId, request.amount());
            } catch (BalanceCoreServiceFailedException e) {
                throw new BalanceServiceNotFoundException(balanceId);
            }
        }
    }

    @Override
    public void removeAllBalances() {
        coreService.removeAllBalances();
    }
}
