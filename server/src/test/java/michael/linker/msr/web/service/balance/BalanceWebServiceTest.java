package michael.linker.msr.web.service.balance;

import michael.linker.msr.core.service.balance.BalanceCoreServiceFailedException;
import michael.linker.msr.core.service.balance.IBalanceCoreService;
import michael.linker.msr.ParentTest;
import michael.linker.msr.web.model.balance.BalanceModel;
import michael.linker.msr.web.model.balance.BalanceUpdateModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class BalanceWebServiceTest extends ParentTest {

    @InjectMocks
    private BalanceWebService balanceWebService;

    @Mock
    private IBalanceCoreService balanceCoreServiceMocked;

    @Test
    void createBalance() {
        Mockito.doNothing()
                .when(balanceCoreServiceMocked).createBalance(Mockito.any(BalanceModel.class));
        BalanceModel testModel = new BalanceModel(1L, 1L);

        balanceWebService.createBalance(testModel);

        verify(balanceCoreServiceMocked, Mockito.times(1)).createBalance(Mockito.any(BalanceModel.class));
    }

    @Test
    void createBalanceAlreadyExists() {
        Mockito.doThrow(BalanceCoreServiceFailedException.class)
                .when(balanceCoreServiceMocked).createBalance(Mockito.any(BalanceModel.class));
        BalanceModel testModel = new BalanceModel(1L, 1L);

        assertThrows(BalanceServiceAlreadyExistsException.class,
                () -> balanceWebService.createBalance(testModel));

        verify(balanceCoreServiceMocked, Mockito.times(1)).createBalance(Mockito.any(BalanceModel.class));
    }


    @Test
    void getBalance() {
        Optional<Long> testOptional = Optional.of(1L);
        Mockito.doReturn(testOptional)
                .when(balanceCoreServiceMocked).getBalance(Mockito.anyLong());

        assertEquals(1L, balanceWebService.getBalance(1L).amount());

        verify(balanceCoreServiceMocked, Mockito.times(1)).getBalance(Mockito.anyLong());
    }

    @Test
    void getBalanceNotFound() {
        Optional<Long> testOptional = Optional.empty();
        Mockito.doReturn(testOptional)
                .when(balanceCoreServiceMocked).getBalance(Mockito.anyLong());
        Long testId = 1L;

        assertThrows(BalanceServiceNotFoundException.class,
                () -> balanceWebService.getBalance(testId));

        verify(balanceCoreServiceMocked, Mockito.times(1)).getBalance(Mockito.anyLong());
    }

    @Test
    void updateBalance() {
        Mockito.doNothing()
                .when(balanceCoreServiceMocked).changeBalance(Mockito.anyLong(), Mockito.anyLong());
        BalanceUpdateModel testModel = new BalanceUpdateModel(1L);

        balanceWebService.updateBalance(1L, testModel);

        verify(balanceCoreServiceMocked, Mockito.times(1)).changeBalance(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void updateBalanceZeroAmount() {
        Mockito.doNothing()
                .when(balanceCoreServiceMocked).changeBalance(Mockito.anyLong(), Mockito.anyLong());
        BalanceUpdateModel testEmptyModel = new BalanceUpdateModel(0L);

        balanceWebService.updateBalance(1L, testEmptyModel);

        verify(balanceCoreServiceMocked, Mockito.never()).changeBalance(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void updateBalanceNotFound() {
        Mockito.doThrow(BalanceCoreServiceFailedException.class)
                .when(balanceCoreServiceMocked).changeBalance(Mockito.anyLong(), Mockito.anyLong());
        BalanceUpdateModel testModel = new BalanceUpdateModel(1L);

        assertThrows(BalanceServiceNotFoundException.class,
                () -> balanceWebService.updateBalance(1L, testModel));

        verify(balanceCoreServiceMocked, Mockito.times(1)).changeBalance(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void removeAllBalances() {
        balanceCoreServiceMocked.removeAllBalances();

        verify(balanceCoreServiceMocked, Mockito.times(1)).removeAllBalances();
    }
}