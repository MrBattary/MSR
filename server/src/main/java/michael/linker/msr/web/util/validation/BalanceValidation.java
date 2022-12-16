package michael.linker.msr.web.util.validation;

/**
 * Validation class for balance model fields.
 *
 * @see michael.linker.msr.core.model.balance.BalanceModel
 */
public class BalanceValidation {
    /**
     * Checks:
     * - ID is not null.
     *
     * @param id balance ID.
     * @return true if ID is valid, false otherwise.
     */
    public static boolean isIdValid(Long id) {
        return id != null;
    }

    /**
     * Checks if:
     * - Amount is not null.
     * - Amount is not 0.
     *
     * @param amount balance change amount.
     * @return true if amount is valid, false otherwise.
     */
    public static boolean isAmountValid(Long amount) {
        return amount != null && amount != 0L;
    }
}
