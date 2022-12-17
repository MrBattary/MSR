package michael.linker.msr.web.util.validation.balance;

import michael.linker.msr.web.model.balance.BalanceModel;

/**
 * Validation class for balance.
 *
 * @see BalanceModel
 */
public class BalanceValidation {
    /**
     * Checks if:
     * - Amount is not 0.
     *
     * @param amount balance change amount.
     * @return true if amount is valid, false otherwise.
     */
    public static boolean isAmountValid(Long amount) {
        return amount != 0L;
    }
}
