package michael.linker.msr.web.controller;

import michael.linker.msr.ParentTest;
import michael.linker.msr.web.model.api.request.CreateBalanceRequest;
import michael.linker.msr.web.model.api.request.UpdateBalanceRequest;
import michael.linker.msr.web.model.api.response.GetBalanceResponse;
import michael.linker.msr.web.model.balance.BalanceModel;
import michael.linker.msr.web.model.balance.BalanceUpdateModel;
import michael.linker.msr.web.service.balance.BalanceServiceAlreadyExistsException;
import michael.linker.msr.web.service.balance.BalanceServiceNotFoundException;
import michael.linker.msr.web.service.balance.IBalanceWebService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class BalanceControllerTest extends ParentTest {
    @InjectMocks
    private BalanceController balanceController;
    @Mock
    private IBalanceWebService mockedBalanceWebService;

    @Test
    void createBalance() {
        Mockito.doNothing()
                .when(mockedBalanceWebService).createBalance(Mockito.any());
        CreateBalanceRequest testRequest = new CreateBalanceRequest(1L, 1L);

        assertEquals(HttpStatus.OK, balanceController.createBalance(testRequest).getStatusCode());

        verify(mockedBalanceWebService, Mockito.times(1)).createBalance(Mockito.any(BalanceModel.class));
    }

    @Test
    void createBalanceAlreadyExists() {
        Mockito.doThrow(BalanceServiceAlreadyExistsException.class)
                .when(mockedBalanceWebService).createBalance(Mockito.any(BalanceModel.class));
        CreateBalanceRequest testRequest = new CreateBalanceRequest(1L, 1L);

        assertThrows(BalanceServiceAlreadyExistsException.class,
                () -> balanceController.createBalance(testRequest));

        verify(mockedBalanceWebService, Mockito.times(1)).createBalance(Mockito.any(BalanceModel.class));
    }

    @Test
    void getBalance() {
        GetBalanceResponse testResponse = new GetBalanceResponse(-1L);
        Mockito.doReturn(testResponse)
                .when(mockedBalanceWebService).getBalance(Mockito.any());

        assertEquals(HttpStatus.OK, balanceController.getBalance("1").getStatusCode());

        verify(mockedBalanceWebService, Mockito.times(1)).getBalance(Mockito.anyLong());
    }

    @Test
    void getBalanceNotFound() {
        Mockito.doThrow(BalanceServiceNotFoundException.class)
                .when(mockedBalanceWebService).getBalance(Mockito.any());

        assertThrows(BalanceServiceNotFoundException.class,
                () -> balanceController.getBalance("1"));

        verify(mockedBalanceWebService, Mockito.times(1)).getBalance(Mockito.anyLong());
    }

    @Test
    void updateBalance() {
        Mockito.doNothing()
                .when(mockedBalanceWebService)
                .updateBalance(Mockito.anyLong(), Mockito.any(BalanceUpdateModel.class));
        UpdateBalanceRequest testRequest = new UpdateBalanceRequest(1L);

        assertEquals(HttpStatus.OK, balanceController.updateBalance("1", testRequest).getStatusCode());

        verify(mockedBalanceWebService, Mockito.times(1))
                .updateBalance(Mockito.anyLong(), Mockito.any(BalanceUpdateModel.class));
    }

    @Test
    void updateBalanceNotFound() {
        Mockito.doThrow(BalanceServiceNotFoundException.class)
                .when(mockedBalanceWebService)
                .updateBalance(Mockito.anyLong(), Mockito.any(BalanceUpdateModel.class));
        UpdateBalanceRequest testRequest = new UpdateBalanceRequest(1L);

        assertThrows(BalanceServiceNotFoundException.class,
                () -> balanceController.updateBalance("1", testRequest));

        verify(mockedBalanceWebService, Mockito.times(1))
                .updateBalance(Mockito.anyLong(), Mockito.any(BalanceUpdateModel.class));
    }

    @Test
    void removeAllBalances() {
        Mockito.doNothing()
                .when(mockedBalanceWebService)
                .removeAllBalances();

        assertEquals(HttpStatus.OK, balanceController.removeAllBalances().getStatusCode());

        verify(mockedBalanceWebService, Mockito.times(1)).removeAllBalances();
    }
}