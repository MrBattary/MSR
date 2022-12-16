package michael.linker.msr.web.service.balance;

import michael.linker.msr.core.model.balance.BalanceModel;
import michael.linker.msr.core.service.balance.BalanceCoreServiceFailedException;
import michael.linker.msr.core.service.balance.IBalanceCoreService;
import michael.linker.msr.web.model.api.request.CreateBalanceRequest;
import michael.linker.msr.web.model.api.request.UpdateBalanceRequest;
import michael.linker.msr.web.model.api.response.GetBalanceResponse;
import michael.linker.msr.web.util.validation.BalanceValidation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceWebService implements IBalanceWebService {
    private final IBalanceCoreService coreService;

    public BalanceWebService(IBalanceCoreService coreService) {
        this.coreService = coreService;
    }

    @Override
    public void createBalance(CreateBalanceRequest request)
            throws BalanceServiceBadDataException, BalanceServiceAlreadyExistsException {
        if (!BalanceValidation.isIdValid(request.id())) {
            throw new BalanceServiceBadDataException();
        }

        try {
            coreService.createBalance(new BalanceModel(request));
        } catch (BalanceCoreServiceFailedException e) {
            throw new BalanceServiceAlreadyExistsException(request.id());
        }
    }

    @Override
    public GetBalanceResponse getBalance(Long balanceId) throws BalanceServiceNotFoundException {
        if (!BalanceValidation.isIdValid(balanceId)) {
            throw new BalanceServiceBadDataException();
        }

        Optional<Long> amount = coreService.getBalance(balanceId);
        if (amount.isEmpty()) {
            throw new BalanceServiceNotFoundException(balanceId);
        }

        return new GetBalanceResponse(amount.get());
    }

    @Override
    public void updateBalance(Long balanceId, UpdateBalanceRequest request) throws BalanceServiceNotFoundException {
        if (!BalanceValidation.isIdValid(balanceId)) {
            throw new BalanceServiceBadDataException();
        }

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
