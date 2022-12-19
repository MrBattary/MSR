package michael.linker.msr.core.service.balance;

import michael.linker.msr.core.entity.balance.BalanceEntity;
import michael.linker.msr.core.repository.balance.BalanceRepository;
import michael.linker.msr.ParentTest;
import michael.linker.msr.web.model.balance.BalanceModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class BalanceCoreServiceTest extends ParentTest {
    @Autowired
    private BalanceCoreService balanceCoreService;
    @Mock
    private BalanceRepository balanceRepositoryMocked;

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(balanceCoreService, "repository", balanceRepositoryMocked);
    }

    @AfterEach
    public void flushCache() {
        // Mocked repository do nothing, so just clear all cache.
        // This is much easier than recreating the bean with a new one.
        balanceCoreService.removeAllBalances();
    }

    @Test
    void createBalance() {
        Mockito.doReturn(0L)
                .when(balanceRepositoryMocked).countById(Mockito.anyLong());
        BalanceEntity balanceEntityMocked = Mockito.mock(BalanceEntity.class);
        Mockito.doReturn(balanceEntityMocked)
                .when(balanceRepositoryMocked).saveAndFlush(Mockito.any(BalanceEntity.class));
        BalanceModel testModel = new BalanceModel(1L, 1L);

        balanceCoreService.createBalance(testModel);

        verify(balanceRepositoryMocked, Mockito.times(1)).countById(Mockito.anyLong());
        verify(balanceRepositoryMocked, Mockito.times(1)).saveAndFlush(Mockito.any(BalanceEntity.class));
    }

    @Test
    void createBalanceAlreadyExists() {
        Mockito.doReturn(1L)
                .when(balanceRepositoryMocked).countById(Mockito.anyLong());
        BalanceModel testModel = new BalanceModel(1L, 1L);

        assertThrows(BalanceCoreServiceFailedException.class,
                () -> balanceCoreService.createBalance(testModel));

        verify(balanceRepositoryMocked, Mockito.times(1)).countById(Mockito.anyLong());
        verify(balanceRepositoryMocked, Mockito.never()).saveAndFlush(Mockito.any(BalanceEntity.class));
    }

    @Test
    void getBalance() {
        BalanceEntity balanceEntityMocked = Mockito.mock(BalanceEntity.class);
        Optional<BalanceEntity> optionalMocked = Optional.of(balanceEntityMocked);
        Mockito.doReturn(1L)
                .when(balanceEntityMocked).getAmount();
        Mockito.doReturn(optionalMocked)
                .when(balanceRepositoryMocked).findById(Mockito.anyLong());
        Long testId = 1L;

        assertEquals(1L, balanceCoreService.getBalance(testId).get());

        verify(balanceRepositoryMocked, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void getBalanceNotFound() {
        Optional<BalanceEntity> optional = Optional.empty();
        Mockito.doReturn(optional)
                .when(balanceRepositoryMocked).findById(Mockito.anyLong());
        Long testId = 1L;

        assertEquals(Optional.empty(), balanceCoreService.getBalance(testId));
        verify(balanceRepositoryMocked, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void changeBalance() {
        BalanceEntity balanceEntityMocked = Mockito.mock(BalanceEntity.class);
        Optional<BalanceEntity> optionalMocked = Optional.of(balanceEntityMocked);

        Mockito.doReturn(optionalMocked)
                .when(balanceRepositoryMocked).findById(Mockito.anyLong());
        Mockito.doReturn(1L)
                .when(balanceEntityMocked).getAmount();
        Mockito.doNothing()
                .when(balanceEntityMocked).setAmount(Mockito.anyLong());

        balanceCoreService.changeBalance(1L, 1L);

        verify(balanceRepositoryMocked, Mockito.times(1)).findById(Mockito.anyLong());
        verify(balanceRepositoryMocked, Mockito.times(1)).save(Mockito.any(BalanceEntity.class));
    }

    @Test
    void changeBalanceNotFound() {
        Optional<BalanceEntity> optional = Optional.empty();
        Mockito.doReturn(optional)
                .when(balanceRepositoryMocked).findById(Mockito.anyLong());

        assertThrows(BalanceCoreServiceFailedException.class,
                () -> balanceCoreService.changeBalance(1L, 1L));

        verify(balanceRepositoryMocked, Mockito.times(1)).findById(Mockito.anyLong());
        verify(balanceRepositoryMocked, Mockito.never()).save(Mockito.any(BalanceEntity.class));
    }

    @Test
    void removeAllBalances() {
        Mockito.doNothing().when(balanceRepositoryMocked).deleteAllInBatch();

        balanceCoreService.removeAllBalances();

        verify(balanceRepositoryMocked, Mockito.times(1)).deleteAllInBatch();
    }

    @Test
    void cacheCreatesAfterGetBalance() {
        BalanceEntity balanceEntityMocked = Mockito.mock(BalanceEntity.class);
        Optional<BalanceEntity> optionalMocked = Optional.of(balanceEntityMocked);
        Mockito.doReturn(1L)
                .when(balanceEntityMocked).getAmount();
        Mockito.doReturn(optionalMocked)
                .when(balanceRepositoryMocked).findById(Mockito.anyLong());


        assertEquals(1L, balanceCoreService.getBalance(1L).get());
        assertEquals(1L, balanceCoreService.getBalance(1L).get());

        verify(balanceRepositoryMocked, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void cacheEvictAfterUpdateBalance() {
        BalanceEntity balanceEntityMocked = Mockito.mock(BalanceEntity.class);
        Optional<BalanceEntity> optionalMocked = Optional.of(balanceEntityMocked);
        Mockito.doReturn(1L)
                .when(balanceEntityMocked).getAmount();
        Mockito.doReturn(optionalMocked)
                .when(balanceRepositoryMocked).findById(Mockito.anyLong());
        Long testId = 1L;

        assertEquals(1L, balanceCoreService.getBalance(testId).get());
        balanceCoreService.changeBalance(1L, 1L);
        assertEquals(1L, balanceCoreService.getBalance(testId).get());

        verify(balanceRepositoryMocked, Mockito.times(3)).findById(Mockito.anyLong());
        verify(balanceRepositoryMocked, Mockito.times(1)).save(Mockito.any(BalanceEntity.class));
    }

    @Test
    void cacheEvictAfterDeleteAllBalances() {
        BalanceEntity balanceEntityMocked = Mockito.mock(BalanceEntity.class);
        Optional<BalanceEntity> optionalMocked = Optional.of(balanceEntityMocked);
        Mockito.doReturn(1L)
                .when(balanceEntityMocked).getAmount();
        Mockito.doReturn(optionalMocked)
                .when(balanceRepositoryMocked).findById(Mockito.anyLong());
        Long testId = 1L;

        assertEquals(1L, balanceCoreService.getBalance(testId).get());
        balanceCoreService.removeAllBalances();
        assertEquals(1L, balanceCoreService.getBalance(testId).get());

        verify(balanceRepositoryMocked, Mockito.times(2)).findById(Mockito.anyLong());
        verify(balanceRepositoryMocked, Mockito.times(1)).deleteAllInBatch();
    }
}