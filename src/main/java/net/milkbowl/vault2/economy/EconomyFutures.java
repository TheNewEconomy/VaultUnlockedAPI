package net.milkbowl.vault2.economy;
/* This file is part of Vault.

    Vault is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Vault is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Vault.  If not, see <http://www.gnu.org/licenses/>.
 */


import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Provides a wrapper for the synchronous methods of {@link Economy} and the asynchronous methods of {@link AsyncEconomy}.
 * If the {@link Economy#async()} method is provided for this method attempts to perform the operation
 * asynchronously if, otherwise, it falls back to a synchronous operation.
 *
 * @author creatorfromhell
 * @since 2.20
 */
public final class EconomyFutures {

  private EconomyFutures() {
    throw new UnsupportedOperationException("Utility class");
  }

  /*
   * Account Methods
   */

  /**
   * Creates an account in the given economy system with the specified parameters.
   *
   * @param economy  The instance of the {@code Economy} system where the account will be created. Must not be null.
   * @param accountID The unique identifier for the account. Must not be null.
   * @param name The name to associate with the account. Must not be null.
   * @param player A boolean indicating whether the account is a player account or not.
   * @return A {@code CompletableFuture} that resolves to {@code true} if the account was successfully created,
   *         or {@code false} otherwise.
   */
  public static CompletableFuture<Boolean> createAccount(@NotNull final Economy economy,
                                                         @NotNull final UUID accountID,
                                                         @NotNull final String name,
                                                         final boolean player) {

    return economy.async()
            .map(async -> async.createAccount(accountID, name, player))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.createAccount(accountID, name, player)));
  }

  /**
   * Creates a new account using the provided Economy instance.
   *
   * @param economy  the economy instance used to create the account; must not be null
   * @param accountID  the unique identifier for the account to be created; must not be null
   * @param name  the name of the account owner; must not be null
   * @param worldName  the name of the world associated with the account; must not be null
   * @param player  a boolean indicating whether the account belongs to a player
   * @return a CompletableFuture that resolves to true if the account was successfully created, or false otherwise
   */
  public static CompletableFuture<Boolean> createAccount(@NotNull final Economy economy,
                                                         @NotNull final UUID accountID,
                                                         @NotNull final String name,
                                                         @NotNull final String worldName,
                                                         final boolean player) {

    return economy.async()
            .map(async -> async.createAccount(accountID, name, worldName, player))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.createAccount(accountID, name, worldName, player)));
  }

  /**
   * Retrieves a mapping of UUIDs to names using the provided Economy instance.
   *
   * @param economy The Economy instance used to retrieve the UUID-to-name map. Must not be null.
   * @return A CompletableFuture containing a map where the keys are UUIDs and the values are associated names.
   */
  public static CompletableFuture<Map<UUID, String>> getUUIDNameMap(@NotNull final Economy economy) {

    return economy.async()
            .map(AsyncEconomy::getUUIDNameMap)
            .orElseGet(() -> CompletableFuture.completedFuture(economy.getUUIDNameMap()));
  }

  /**
   * Retrieves the name of an account associated with the specified account ID.
   *
   * @param economy the economy instance used to fetch the account name, must not be null
   * @param accountID the unique identifier of the account whose name is to be retrieved, must not be null
   * @return a CompletableFuture containing an Optional with the account name if it exists, or an empty Optional otherwise
   */
  public static CompletableFuture<Optional<String>> getAccountName(@NotNull final Economy economy,
                                                                   @NotNull final UUID accountID) {

    return economy.async()
            .map(async -> async.getAccountName(accountID))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.getAccountName(accountID)));
  }

  /**
   * Checks asynchronously if an account exists in the provided economy system for the given account ID.
   *
   * @param economy   The economy system instance used to verify the account. Must not be null.
   * @param accountID The unique identifier of the account to check. Must not be null.
   * @return A CompletableFuture that resolves to true if the account exists, false otherwise.
   */
  public static CompletableFuture<Boolean> hasAccount(@NotNull final Economy economy,
                                                      @NotNull final UUID accountID) {

    return economy.async()
            .map(async -> async.hasAccount(accountID))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.hasAccount(accountID)));
  }

  /**
   * Checks asynchronously whether an account exists for the specified account ID and world name.
   *
   * @param economy   The economy instance to query. Must not be null.
   * @param accountID The unique identifier of the account to check. Must not be null.
   * @param worldName The name of the world where the account's existence is being verified. Must not be null.
   * @return A CompletableFuture that resolves to {@code true} if the account exists, or {@code false} otherwise.
   */
  public static CompletableFuture<Boolean> hasAccount(@NotNull final Economy economy,
                                                      @NotNull final UUID accountID,
                                                      @NotNull final String worldName) {

    return economy.async()
            .map(async -> async.hasAccount(accountID, worldName))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.hasAccount(accountID, worldName)));
  }

  /**
   * Renames an account in the given economy system.
   *
   * @param economy   The economy system where the account is managed. Must not be null.
   * @param accountID The unique identifier of the account to be renamed. Must not be null.
   * @param name      The new name for the account. Must not be null.
   * @return A CompletableFuture that resolves to a Boolean indicating whether the operation was successful.
   */
  public static CompletableFuture<Boolean> renameAccount(@NotNull final Economy economy,
                                                         @NotNull final UUID accountID,
                                                         @NotNull final String name) {

    return renameAccount(economy, "Vault", accountID, name);
  }

  /**
   * Renames an existing account in the specified economy system.
   *
   * @param economy      The economy instance to execute the renaming operation.
   * @param pluginName   The name of the plugin making the request.
   * @param accountID    The unique identifier of the account to be renamed.
   * @param name         The new name to assign to the account.
   * @return A CompletableFuture that resolves to a Boolean indicating
   *         whether the renaming operation was successful.
   */
  public static CompletableFuture<Boolean> renameAccount(@NotNull final Economy economy,
                                                         @NotNull final String pluginName,
                                                         @NotNull final UUID accountID,
                                                         @NotNull final String name) {

    return economy.async()
            .map(async -> async.renameAccount(pluginName, accountID, name))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.renameAccount(pluginName, accountID, name)));
  }

  /**
   * Deletes an account associated with the given plugin name and account ID.
   *
   * @param economy    The economy instance used to manage account operations. Must not be null.
   * @param pluginName The name of the plugin associated with the account. Must not be null.
   * @param accountID  The unique identifier of the account to be deleted. Must not be null.
   * @return A CompletableFuture containing a boolean value. The boolean indicates
   *         whether the account was successfully deleted (true) or not (false).
   */
  public static CompletableFuture<Boolean> deleteAccount(@NotNull final Economy economy,
                                                         @NotNull final String pluginName,
                                                         @NotNull final UUID accountID) {

    return economy.async()
            .map(async -> async.deleteAccount(pluginName, accountID))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.deleteAccount(pluginName, accountID)));
  }

  /*
   * Balance Methods
   */

  /**
   * Checks asynchronously if the specified account supports the given currency.
   *
   * @param economy    The Economy instance used to perform the currency support check.
   * @param pluginName The name of the plugin requesting the check.
   * @param accountID  The unique identifier of the account to be checked.
   * @param currency   The currency to check support for.
   * @return A CompletableFuture containing a Boolean result:
   *         true if the account supports the specified currency, false otherwise.
   */
  public static CompletableFuture<Boolean> accountSupportsCurrency(@NotNull final Economy economy,
                                                                   @NotNull final String pluginName,
                                                                   @NotNull final UUID accountID,
                                                                   @NotNull final String currency) {

    return economy.async()
            .map(async -> async.accountSupportsCurrency(pluginName, accountID, currency))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.accountSupportsCurrency(pluginName, accountID, currency)));
  }

  /**
   * Checks if the specified account supports the given currency in the provided world.
   *
   * @param economy The economy instance used to perform the currency check. Must not be null.
   * @param pluginName The name of the plugin associated with the account. Must not be null.
   * @param accountID The unique identifier of the account to be checked. Must not be null.
   * @param currency The currency type to be checked for support. Must not be null.
   * @param world The name of the world where the account's currency support is to be verified. Must not be null.
   * @return A CompletableFuture of Boolean indicating whether the account supports the specified currency in the given world.
   */
  public static CompletableFuture<Boolean> accountSupportsCurrency(@NotNull final Economy economy,
                                                                   @NotNull final String pluginName,
                                                                   @NotNull final UUID accountID,
                                                                   @NotNull final String currency,
                                                                   @NotNull final String world) {

    return economy.async()
            .map(async -> async.accountSupportsCurrency(pluginName, accountID, currency, world))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.accountSupportsCurrency(pluginName, accountID, currency, world)));
  }

  /**
   * Retrieves the balance of a specific account within a given economy plugin.
   *
   * @param economy    the economy instance managing the balances
   * @param pluginName the name of the plugin under which the account is maintained
   * @param accountID  the unique identifier of the account whose balance is to be retrieved
   * @return a CompletableFuture containing the balance of the specified account
   */
  public static CompletableFuture<BigDecimal> balance(@NotNull final Economy economy,
                                                      @NotNull final String pluginName,
                                                      @NotNull final UUID accountID) {

    return economy.async()
            .map(async -> async.balance(pluginName, accountID))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.balance(pluginName, accountID)));
  }

  /**
   * Retrieves the balance of the specified account in the given world using the provided Economy instance.
   *
   * @param economy    The Economy instance used to perform the balance operation.
   * @param pluginName The name of the plugin invoking this method.
   * @param accountID  The unique identifier of the account for which the balance is being retrieved.
   * @param world      The name of the world where the account's balance is being checked.
   * @return A CompletableFuture containing the balance of the account as a BigDecimal.
   */
  public static CompletableFuture<BigDecimal> balance(@NotNull final Economy economy,
                                                      @NotNull final String pluginName,
                                                      @NotNull final UUID accountID,
                                                      @NotNull final String world) {

    return economy.async()
            .map(async -> async.balance(pluginName, accountID, world))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.balance(pluginName, accountID, world)));
  }

  /**
   * Retrieves the balance for a specific account in a specified world and currency.
   *
   * @param economy      the economy instance used to fetch the balance
   * @param pluginName   the name of the plugin requesting the balance
   * @param accountID    the unique identifier of the account
   * @param world        the name of the world where the account is located
   * @param currency     the currency type for the balance
   * @return a CompletableFuture that resolves to the balance as a BigDecimal
   */
  public static CompletableFuture<BigDecimal> balance(@NotNull final Economy economy,
                                                      @NotNull final String pluginName,
                                                      @NotNull final UUID accountID,
                                                      @NotNull final String world,
                                                      @NotNull final String currency) {
    
    return economy.async()
            .map(async -> async.balance(pluginName, accountID, world, currency))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.balance(pluginName, accountID, world, currency)));
  }

  /**
   * Checks if a given account has a specified amount in the economy system.
   *
   * @param economy    The economy system being queried. Must not be null.
   * @param pluginName The name of the plugin initiating the request. Must not be null.
   * @param accountID  The unique identifier of the account being checked. Must not be null.
   * @param amount     The amount to check for in the account. Must not be null.
   * @return A CompletableFuture that, when completed, indicates whether the specified account 
   *         has at least the specified amount in the economy system.
   */
  public static CompletableFuture<Boolean> has(@NotNull final Economy economy,
                                               @NotNull final String pluginName,
                                               @NotNull final UUID accountID,
                                               @NotNull final BigDecimal amount) {
    
    return economy.async()
            .map(async -> async.has(pluginName, accountID, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.has(pluginName, accountID, amount)));
  }

  /**
   * Checks if the specified account has at least the given amount in the specified world 
   * using the provided economy system.
   *
   * @param economy The economy system to be used for the operation. Must not be null.
   * @param pluginName The name of the plugin requesting the operation. Must not be null.
   * @param accountID The unique identifier of the account to check. Must not be null.
   * @param worldName The name of the world for which the operation is being performed. Must not be null.
   * @param amount The amount to check if the account possesses. Must not be null.
   * @return A CompletableFuture containing a Boolean indicating whether the account has at least the specified amount.
   */
  public static CompletableFuture<Boolean> has(@NotNull final Economy economy,
                                               @NotNull final String pluginName,
                                               @NotNull final UUID accountID,
                                               @NotNull final String worldName,
                                               @NotNull final BigDecimal amount) {
    
    return economy.async()
            .map(async -> async.has(pluginName, accountID, worldName, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.has(pluginName, accountID, worldName, amount)));
  }

  /**
   * Checks if the specified account has at least the specified amount of currency
   * in the given world for the plugin's economy system.
   *
   * @param economy the economy instance used to perform the check
   * @param pluginName the name of the plugin requesting the check
   * @param accountID the unique identifier of the account to be checked
   * @param worldName the name of the world where the account exists
   * @param currency the currency in which the amount is to be checked
   * @param amount the minimum amount to check for
   * @return a CompletableFuture that resolves to {@code true} if the account has at least
   *         the specified amount of the given currency, or {@code false} otherwise
   */
  public static CompletableFuture<Boolean> has(@NotNull final Economy economy,
                                               @NotNull final String pluginName,
                                               @NotNull final UUID accountID,
                                               @NotNull final String worldName,
                                               @NotNull final String currency,
                                               @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.has(pluginName, accountID, worldName, currency, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.has(pluginName, accountID, worldName, currency, amount)));
  }

  /*
   * Transaction Methods
   */

  /**
   * Sets the specified amount for the provided account ID within the given economy instance.
   *
   * @param economy The economy instance to perform the operation on. Must not be null.
   * @param pluginName The name of the plugin invoking this operation. Must not be null.
   * @param accountID The unique identifier of the account for which the amount is being set. Must not be null.
   * @param amount The amount to set for the specified account. Must not be null.
   * @return A CompletableFuture containing the result of the economy operation.
   */
  public static CompletableFuture<EconomyResponse> set(@NotNull final Economy economy,
                                                       @NotNull final String pluginName,
                                                       @NotNull final UUID accountID,
                                                       @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.set(pluginName, accountID, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.set(pluginName, accountID, amount)));
  }

  /**
   * Sets the economy balance for a specific account in a given world.
   *
   * @param economy The economy implementation to be used. Must not be null.
   * @param pluginName The name of the plugin invoking this method. Must not be null.
   * @param accountID The unique identifier of the account whose balance is to be set. Must not be null.
   * @param worldName The name of the world in which the account's balance is to be set. Must not be null.
   * @param amount The amount to set as the account's balance. Must not be null.
   * @return A CompletableFuture containing the result of the balance set operation as an EconomyResponse.
   */
  public static CompletableFuture<EconomyResponse> set(@NotNull final Economy economy,
                                                       @NotNull final String pluginName,
                                                       @NotNull final UUID accountID,
                                                       @NotNull final String worldName,
                                                       @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.set(pluginName, accountID, worldName, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.set(pluginName, accountID, worldName, amount)));
  }

  /**
   * Sets the specified amount of the given currency for the specified account and world
   * in the provided economy instance.
   *
   * @param economy the economy instance to perform the operation with, must not be null
   * @param pluginName the name of the plugin initiating the operation, must not be null
   * @param accountID the unique identifier of the account, must not be null
   * @param worldName the name of the world where the operation is applied, must not be null
   * @param currency the currency to be updated, must not be null
   * @param amount the amount to be set for the given currency, must not be null
   * @return a CompletableFuture containing the result of the operation as an EconomyResponse
   */
  public static CompletableFuture<EconomyResponse> set(@NotNull final Economy economy,
                                                       @NotNull final String pluginName,
                                                       @NotNull final UUID accountID,
                                                       @NotNull final String worldName,
                                                       @NotNull final String currency,
                                                       @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.set(pluginName, accountID, worldName, currency, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.set(pluginName, accountID, worldName, currency, amount)));
  }

  /**
   * Asynchronously transfers a specified monetary amount from one user to another within the context of a given plugin.
   * The method ensures atomicity by attempting to revert the withdrawal if the deposit fails.
   *
   * @param economy the economy instance to perform the check against
   * @param pluginName the name of the plugin initiating the transfer; must not be null
   * @param from the unique identifier (UUID) of the user account from which the amount will be withdrawn; must not be null
   * @param to the unique identifier (UUID) of the user account to which the amount will be deposited; must not be null
   * @param amount the monetary amount to transfer; must not be null
   * @return a CompletableFuture containing an {@code MultiEconomyResponse} detailing the status of the transfer,
   *         including the final balances, success/failure type, and error messages (if any)
   */
  public static CompletableFuture<MultiEconomyResponse> transfer(@NotNull final Economy economy,
                                                                 @NotNull final String pluginName,
                                                                 @NotNull final UUID from,
                                                                 @NotNull final UUID to,
                                                                 @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.transfer(pluginName, from, to, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.transfer(pluginName, from, to, amount)));
  }

  /**
   * Asynchronously transfers a specific amount of currency from one account to another within the same world.
   * The transfer is performed as a withdrawal from the source account followed by a deposit
   * into the target account. If the deposit operation fails, the withdrawn amount is refunded
   * to the source account to ensure consistency.
   *
   * @param economy the economy instance to perform the check against
   * @param pluginName The name of the plugin initiating the transfer.
   *                   Must not be null.
   * @param from       The unique identifier of the sender's account.
   *                   Must not be null.
   * @param to         The unique identifier of the receiver's account.
   *                   Must not be null.
   * @param worldName  The name of the world in which the transfer is taking place.
   *                   Must not be null.
   * @param amount     The amount of currency to transfer.
   *                   Must not be null.
   * @return A CompletableFuture containing an {@code MultiEconomyResponse} containing the details of the transfer result,
   *         including the amount transferred, the resulting balances for both accounts, and
   *         the operation status. If the transfer fails, it contains error details.
   */
  public static CompletableFuture<MultiEconomyResponse> transfer(@NotNull final Economy economy,
                                                                 @NotNull final String pluginName,
                                                                 @NotNull final UUID from,
                                                                 @NotNull final UUID to,
                                                                 @NotNull final String worldName,
                                                                 @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.transfer(pluginName, from, to, worldName, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.transfer(pluginName, from, to, worldName, amount)));
  }

  /**
   * Asynchronously transfers a specified amount of currency from one account to another within a specific world.
   * The transfer involves withdrawing the amount from the source account and depositing it into
   * the target account. If the deposit fails, the withdrawn amount is returned to the source account
   * to ensure consistency.
   *
   * @param economy the economy instance to perform the check against
   * @param pluginName the name of the plugin initiating the transfer
   * @param from the unique identifier (UUID) of the source account
   * @param to the unique identifier (UUID) of the target account
   * @param worldName the name of the world in which the transfer is taking place
   * @param currency the name of the currency being transferred
   * @param amount the amount of currency to transfer
   * @return a CompletableFuture containing an {@link MultiEconomyResponse} containing information about the result of the transfer,
   *         including success status, balances, and error messages if applicable
   */
  public static CompletableFuture<MultiEconomyResponse> transfer(@NotNull final Economy economy,
                                                                 @NotNull final String pluginName,
                                                                 @NotNull final UUID from,
                                                                 @NotNull final UUID to,
                                                                 @NotNull final String worldName,
                                                                 @NotNull final String currency,
                                                                 @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.transfer(pluginName, from, to, worldName, currency, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.transfer(pluginName, from, to, worldName, currency, amount)));
  }

  /**
   * Determines if a specified amount can be withdrawn from a given account.
   *
   * @param economy the economy instance to perform the check against
   * @param pluginName the name of the plugin initiating the check
   * @param accountID the unique identifier of the account to check
   * @param amount the amount to be checked for withdrawal capability
   * @return a CompletableFuture containing the result of the withdrawal capability check
   *         as an EconomyResponse
   */
  public static CompletableFuture<EconomyResponse> canWithdraw(@NotNull final Economy economy,
                                                               @NotNull final String pluginName,
                                                               @NotNull final UUID accountID,
                                                               @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.canWithdraw(pluginName, accountID, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.canWithdraw(pluginName, accountID, amount)));
  }

  /**
   * Checks if a withdrawal of the specified amount can be made from the given account in the
   * specified world.
   *
   * @param economy The {@link Economy} instance used to check the withdrawal capability.
   * @param pluginName The name of the plugin requesting the withdrawal check.
   * @param accountID The unique identifier of the account to check.
   * @param worldName The name of the world where the withdrawal is being checked.
   * @param amount The amount to be checked for withdrawal eligibility.
   * @return A {@link CompletableFuture} that resolves to an {@link EconomyResponse} containing
   *         the result of the withdrawal check.
   */
  public static CompletableFuture<EconomyResponse> canWithdraw(@NotNull final Economy economy,
                                                               @NotNull final String pluginName,
                                                               @NotNull final UUID accountID,
                                                               @NotNull final String worldName,
                                                               @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.canWithdraw(pluginName, accountID, worldName, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.canWithdraw(pluginName, accountID, worldName, amount)));
  }

  /**
   * Checks if a withdrawal can be made from the specified account in the given world and currency.
   *
   * @param economy The economy implementation to use for performing the check.
   * @param pluginName The name of the plugin requesting the withdrawal check.
   * @param accountID The unique identifier of the account to check withdrawal eligibility for.
   * @param worldName The name of the world where the withdrawal is intended to occur.
   * @param currency The currency in which the withdrawal is requested.
   * @param amount The amount to be withdrawn.
   * @return A CompletableFuture that resolves to an EconomyResponse indicating whether the withdrawal is allowed and additional details (e.g., reason if not possible).
   */
  public static CompletableFuture<EconomyResponse> canWithdraw(@NotNull final Economy economy,
                                                               @NotNull final String pluginName,
                                                               @NotNull final UUID accountID,
                                                               @NotNull final String worldName,
                                                               @NotNull final String currency,
                                                               @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.canWithdraw(pluginName, accountID, worldName, currency, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.canWithdraw(pluginName, accountID, worldName, currency, amount)));
  }

  /**
   * Withdraws a specified amount from the given account using the provided economy system.
   *
   * @param economy The economy system handling the transaction. Must not be null.
   * @param pluginName The name of the plugin initiating the transaction. Must not be null.
   * @param accountID The unique identifier of the account from which the amount will be withdrawn. Must not be null.
   * @param amount The amount to withdraw from the account. Must not be null.
   * @return A CompletableFuture containing the result of the withdrawal operation, encapsulated in an EconomyResponse.
   */
  public static CompletableFuture<EconomyResponse> withdraw(@NotNull final Economy economy,
                                                            @NotNull final String pluginName,
                                                            @NotNull final UUID accountID,
                                                            @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.withdraw(pluginName, accountID, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.withdraw(pluginName, accountID, amount)));
  }

  /**
   * Withdraws a specified amount from a player's account in the given economy and world.
   *
   * @param economy     The economy system where the withdrawal will be performed. Must not be null.
   * @param pluginName  The name of the plugin performing the transaction. Must not be null.
   * @param accountID   The unique identifier (UUID) of the account holder. Must not be null.
   * @param worldName   The name of the world where the transaction occurs. Must not be null.
   * @param amount      The amount to be withdrawn from the account. Must not be null.
   * @return A CompletableFuture containing the result of the withdrawal operation as an EconomyResponse.
   */
  public static CompletableFuture<EconomyResponse> withdraw(@NotNull final Economy economy,
                                                            @NotNull final String pluginName,
                                                            @NotNull final UUID accountID,
                                                            @NotNull final String worldName,
                                                            @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.withdraw(pluginName, accountID, worldName, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.withdraw(pluginName, accountID, worldName, amount)));
  }

  /**
   * Initiates a withdrawal from a specified account with the given details.
   *
   * @param economy the economy instance used to handle the withdrawal transaction
   * @param pluginName the name of the plugin requesting the withdrawal
   * @param accountID the unique identifier of the account from which the withdrawal will occur
   * @param worldName the name of the world context for the transaction
   * @param currency the type of currency involved in the transaction
   * @param amount the amount of currency to be withdrawn
   * @return a CompletableFuture containing the result of the withdrawal in the form of an EconomyResponse
   */
  public static CompletableFuture<EconomyResponse> withdraw(@NotNull final Economy economy,
                                                            @NotNull final String pluginName,
                                                            @NotNull final UUID accountID,
                                                            @NotNull final String worldName,
                                                            @NotNull final String currency,
                                                            @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.withdraw(pluginName, accountID, worldName, currency, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.withdraw(pluginName, accountID, worldName, currency, amount)));
  }

  /**
   * Checks if a deposit of the specified amount can be made into the given account.
   *
   * @param economy The instance of the Economy system to be used for the deposit check. Must not be null.
   * @param pluginName The name of the plugin initiating the deposit check. Must not be null.
   * @param accountID The unique identifier of the account into which the deposit is being checked. Must not be null.
   * @param amount The amount to be deposited. Must not be null.
   * @return A CompletableFuture containing the result of the deposit check as an EconomyResponse.
   */
  public static CompletableFuture<EconomyResponse> canDeposit(@NotNull final Economy economy,
                                                              @NotNull final String pluginName,
                                                              @NotNull final UUID accountID,
                                                              @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.canDeposit(pluginName, accountID, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.canDeposit(pluginName, accountID, amount)));
  }

  /**
   * Checks if a deposit can be made into a specified account within the given economy system.
   *
   * @param economy The economy instance to handle the deposit check. Must not be null.
   * @param pluginName The name of the plugin requesting the deposit operation. Must not be null.
   * @param accountID The unique identifier of the account to check for deposit eligibility. Must not be null.
   * @param worldName The name of the world where the deposit operation will occur. Must not be null.
   * @param amount The amount to be deposited. Must not be null.
   * @return A CompletableFuture containing an EconomyResponse indicating whether the deposit can be made and, if not, the reason for failure.
   */
  public static CompletableFuture<EconomyResponse> canDeposit(@NotNull final Economy economy,
                                                              @NotNull final String pluginName,
                                                              @NotNull final UUID accountID,
                                                              @NotNull final String worldName,
                                                              @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.canDeposit(pluginName, accountID, worldName, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.canDeposit(pluginName, accountID, worldName, amount)));
  }

  /**
   * Determines if the specified account is eligible to receive the specified deposit.
   *
   * @param economy The economy instance used to process the deposit validation.
   * @param pluginName The name of the plugin initiating the transaction.
   * @param accountID The unique identifier of the account to check.
   * @param worldName The name of the world where the transaction is taking place.
   * @param currency The currency used for the deposit.
   * @param amount The amount of money to be deposited.
   * @return A CompletableFuture containing an EconomyResponse object indicating
   *         whether the account can receive the deposit, along with relevant
   *         status and message details.
   */
  public static CompletableFuture<EconomyResponse> canDeposit(@NotNull final Economy economy,
                                                              @NotNull final String pluginName,
                                                              @NotNull final UUID accountID,
                                                              @NotNull final String worldName,
                                                              @NotNull final String currency,
                                                              @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.canDeposit(pluginName, accountID, worldName, currency, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.canDeposit(pluginName, accountID, worldName, currency, amount)));
  }

  /**
   * Deposits a specified amount into an account.
   *
   * @param economy    The economy instance handling the deposit operation. Must not be null.
   * @param pluginName The name of the plugin initiating the deposit. Must not be null.
   * @param accountID  The unique identifier of the account to deposit into. Must not be null.
   * @param amount     The amount to be deposited. Must not be null.
   * @return A {@code CompletableFuture} containing the result of the deposit operation as an
   *         {@code EconomyResponse}.
   */
  public static CompletableFuture<EconomyResponse> deposit(@NotNull final Economy economy,
                                                           @NotNull final String pluginName,
                                                           @NotNull final UUID accountID,
                                                           @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.deposit(pluginName, accountID, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.deposit(pluginName, accountID, amount)));
  }

  /**
   * Deposits a specified amount of money into an account in the specified world.
   *
   * @param economy The instance of the Economy service being used to handle the transaction.
   * @param pluginName The name of the plugin initiating the transaction.
   * @param accountID The unique identifier of the account receiving the deposit.
   * @param worldName The name of the in-game world associated with the transaction.
   * @param amount The amount of money to deposit into the account.
   * @return A CompletableFuture containing the EconomyResponse, which provides details of the transaction outcome.
   */
  public static CompletableFuture<EconomyResponse> deposit(@NotNull final Economy economy,
                                                           @NotNull final String pluginName,
                                                           @NotNull final UUID accountID,
                                                           @NotNull final String worldName,
                                                           @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.deposit(pluginName, accountID, worldName, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.deposit(pluginName, accountID, worldName, amount)));
  }

  /**
   * Deposits a specified amount of currency into an account.
   *
   * @param economy The economy system that will handle the transaction.
   * @param pluginName The name of the plugin initiating the deposit.
   * @param accountID The universally unique identifier (UUID) of the account where the deposit will be made.
   * @param worldName The name of the world in which the deposit transaction should occur.
   * @param currency The currency type in which the deposit amount is denominated.
   * @param amount The amount of currency to deposit into the account.
   * @return A CompletableFuture containing the EconomyResponse, representing the result of the deposit operation.
   */
  public static CompletableFuture<EconomyResponse> deposit(@NotNull final Economy economy,
                                                           @NotNull final String pluginName,
                                                           @NotNull final UUID accountID,
                                                           @NotNull final String worldName,
                                                           @NotNull final String currency,
                                                           @NotNull final BigDecimal amount) {

    return economy.async()
            .map(async -> async.deposit(pluginName, accountID, worldName, currency, amount))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.deposit(pluginName, accountID, worldName, currency, amount)));
  }

  /*
   * Shared Account Methods
   */

  /**
   * Creates a shared account within the specified economy provider.
   *
   * @param economy The economy instance used to manage account operations. Must not be null.
   * @param pluginName The name of the plugin requesting the creation of the shared account. Must not be null.
   * @param accountID The unique identifier for the shared account being created. Must not be null.
   * @param name The display name of the shared account. Must not be null.
   * @param owner The unique identifier of the owner associated with the shared account. Must not be null.
   * @return A CompletableFuture that resolves to a boolean indicating the success or failure of the shared account creation.
   */
  public static CompletableFuture<Boolean> createSharedAccount(@NotNull final Economy economy,
                                                               @NotNull final String pluginName,
                                                               @NotNull final UUID accountID,
                                                               @NotNull final String name,
                                                               @NotNull final UUID owner) {

    return economy.async()
            .map(async -> async.createSharedAccount(pluginName, accountID, name, owner))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.createSharedAccount(pluginName, accountID, name, owner)));
  }

  /**
   * Retrieves a list of account IDs that are owned by the same owner as the specified account ID.
   *
   * @param economy    The Economy instance used to fetch account ownership details. Must not be null.
   * @param pluginName The name of the plugin for which the accounts are being queried. Must not be null.
   * @param accountID  The unique identifier of the account whose owner's other accounts are being retrieved. Must not be null.
   * @return A CompletableFuture containing a list of UUIDs representing the accounts owned by the same owner.
   */
  public static CompletableFuture<List<UUID>> accountsWithOwnerOf(@NotNull final Economy economy,
                                                                  @NotNull final String pluginName,
                                                                  @NotNull final UUID accountID) {

    return economy.async()
            .map(async -> async.accountsWithOwnerOf(pluginName, accountID))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.accountsWithOwnerOf(pluginName, accountID)));
  }

  /**
   * Retrieves a list of account IDs that have a membership to the specified plugin name for the given account ID.
   *
   * @param economy The economy instance used to fetch the account membership data.
   * @param pluginName The name of the plugin for which membership data is being queried.
   * @param accountID The unique identifier of the account for which memberships are being retrieved.
   * @return A CompletableFuture that resolves to a list of UUIDs representing account IDs with membership to the specified plugin.
   */
  public static CompletableFuture<List<UUID>> accountsWithMembershipTo(@NotNull final Economy economy,
                                                                       @NotNull final String pluginName,
                                                                       @NotNull final UUID accountID) {

    return economy.async()
            .map(async -> async.accountsWithMembershipTo(pluginName, accountID))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.accountsWithMembershipTo(pluginName, accountID)));
  }

  /**
   * Retrieves a list of account IDs that have been granted specific permissions to access a given account
   * within the context of the specified plugin.
   *
   * @param economy the economy system that manages account data and permissions
   * @param pluginName the name of the plugin requesting the information
   * @param accountID the unique identifier of the account being accessed
   * @param permissions the set of permissions required to access the account
   * @return a CompletableFuture containing a list of UUIDs representing the accounts with the specified access
   */
  public static CompletableFuture<List<UUID>> accountsWithAccessTo(@NotNull final Economy economy,
                                                                   @NotNull final String pluginName,
                                                                   @NotNull final UUID accountID,
                                                                   @NotNull final AccountPermission... permissions) {

    return economy.async()
            .map(async -> async.accountsWithAccessTo(pluginName, accountID, permissions))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.accountsWithAccessTo(pluginName, accountID, permissions)));
  }

  /**
   * Checks if the specified UUID corresponds to the owner of the account identified by the given account ID.
   *
   * @param economy    The Economy instance used to perform the account ownership check.
   * @param pluginName The name of the plugin requesting the account ownership verification.
   * @param accountID  The UUID of the account to check ownership for.
   * @param uuid       The UUID of the user to verify as the account owner.
   * @return A {@link CompletableFuture} containing a Boolean value; true if the user is the account owner, false otherwise.
   */
  public static CompletableFuture<Boolean> isAccountOwner(@NotNull final Economy economy,
                                                          @NotNull final String pluginName,
                                                          @NotNull final UUID accountID,
                                                          @NotNull final UUID uuid) {

    return economy.async()
            .map(async -> async.isAccountOwner(pluginName, accountID, uuid))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.isAccountOwner(pluginName, accountID, uuid)));
  }

  /**
   * Attempts to set an owner for a specified account in the provided economy system.
   *
   * @param economy the economy system where the ownership is to be set, must not be null
   * @param pluginName the name of the plugin initiating the operation, must not be null
   * @param accountID the unique identifier of the account, must not be null
   * @param uuid the unique identifier of the new owner, must not be null
   * @return a CompletableFuture that resolves to true if the operation is successful, or false otherwise
   */
  public static CompletableFuture<Boolean> setOwner(@NotNull final Economy economy,
                                                    @NotNull final String pluginName,
                                                    @NotNull final UUID accountID,
                                                    @NotNull final UUID uuid) {

    return economy.async()
            .map(async -> async.setOwner(pluginName, accountID, uuid))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.setOwner(pluginName, accountID, uuid)));
  }

  /**
   * Checks if a given UUID is a member of a specified account in the provided Economy system.
   *
   * @param economy The economy instance used to perform the membership check. Must not be null.
   * @param pluginName The name of the plugin performing the request. Must not be null.
   * @param accountID The unique identifier of the account to check membership for. Must not be null.
   * @param uuid The unique identifier of the potential account member. Must not be null.
   * @return A CompletableFuture that resolves to true if the UUID is a member of the account, or false otherwise.
   */
  public static CompletableFuture<Boolean> isAccountMember(@NotNull final Economy economy,
                                                           @NotNull final String pluginName,
                                                           @NotNull final UUID accountID,
                                                           @NotNull final UUID uuid) {

    return economy.async()
            .map(async -> async.isAccountMember(pluginName, accountID, uuid))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.isAccountMember(pluginName, accountID, uuid)));
  }

  /**
   * Adds a member to an existing account in the economy system.
   *
   * @param economy     The economy instance that manages account-related operations. Must not be null.
   * @param pluginName  The name of the plugin invoking this method. Must not be null.
   * @param accountID   The unique identifier of the account to which the member will be added. Must not be null.
   * @param uuid        The unique identifier of the member to be added to the account. Must not be null.
   * @return A CompletableFuture that resolves to true if the member is successfully added to the account,
   *         or false if the operation fails.
   */
  public static CompletableFuture<Boolean> addAccountMember(@NotNull final Economy economy,
                                                            @NotNull final String pluginName,
                                                            @NotNull final UUID accountID,
                                                            @NotNull final UUID uuid) {

    return economy.async()
            .map(async -> async.addAccountMember(pluginName, accountID, uuid))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.addAccountMember(pluginName, accountID, uuid)));
  }

  /**
   * Adds a member to the specified account with the given permissions.
   *
   * @param economy the economy system to execute the operation on, must not be null
   * @param pluginName the name of the plugin initiating the operation, must not be null
   * @param accountID the unique identifier of the account to which the member is being added, must not be null
   * @param uuid the unique identifier of the member being added, must not be null
   * @param initialPermissions the set of initial permissions to assign to the member, must not be null
   * @return a CompletableFuture that resolves to true if the member was successfully added, or false otherwise
   */
  public static CompletableFuture<Boolean> addAccountMember(@NotNull final Economy economy,
                                                           @NotNull final String pluginName,
                                                           @NotNull final UUID accountID,
                                                           @NotNull final UUID uuid,
                                                           @NotNull final AccountPermission... initialPermissions) {
    return economy.async()
            .map(async -> async.addAccountMember(pluginName, accountID, uuid, initialPermissions))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.addAccountMember(pluginName, accountID, uuid, initialPermissions)));
  }

  /**
   * Removes a member from a specific account within the provided economy system.
   *
   * @param economy The instance of the economy handler to perform the operation.
   * @param pluginName The name of the plugin requesting the operation.
   * @param accountID The unique identifier of the account from which the member should be removed.
   * @param uuid The unique identifier of the member to be removed from the account.
   * @return A CompletableFuture containing a Boolean indicating the success or failure of the operation.
   */
  public static CompletableFuture<Boolean> removeAccountMember(@NotNull final Economy economy,
                                                               @NotNull final String pluginName,
                                                               @NotNull final UUID accountID,
                                                               @NotNull final UUID uuid) {
    
    return economy.async()
            .map(async -> async.removeAccountMember(pluginName, accountID, uuid))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.removeAccountMember(pluginName, accountID, uuid)));
  }

  /**
   * Checks if a specific UUID has the given permission for the specified account under a plugin.
   *
   * @param economy       the Economy instance used to check permissions
   * @param pluginName    the name of the plugin for which the permission is being checked
   * @param accountID     the UUID of the account being checked
   * @param uuid          the UUID of the entity whose permissions are being verified
   * @param permission    the specific account permission to check for
   * @return a CompletableFuture containing a Boolean indicating whether the UUID has the specified permission for the account
   */
  public static CompletableFuture<Boolean> hasAccountPermission(@NotNull final Economy economy,
                                                                @NotNull final String pluginName,
                                                                @NotNull final UUID accountID,
                                                                @NotNull final UUID uuid,
                                                                @NotNull final AccountPermission permission) {
    
    return economy.async()
            .map(async -> async.hasAccountPermission(pluginName, accountID, uuid, permission))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.hasAccountPermission(pluginName, accountID, uuid, permission)));
  }

  /**
   * Updates the permission of a specific account in the economy system.
   *
   * @param economy the economy system instance used to update the account permission
   * @param pluginName the name of the plugin modifying the account permission
   * @param accountID the unique identifier of the target account
   * @param uuid the unique identifier of the entity (e.g., player) whose permission is being updated
   * @param permission the specific account permission to be updated
   * @param value the new value to assign to the permission (true or false)
   * @return a CompletableFuture that, when completed, indicates whether the permission update was successful
   */
  public static CompletableFuture<Boolean> updateAccountPermission(@NotNull final Economy economy,
                                                                   @NotNull final String pluginName,
                                                                   @NotNull final UUID accountID,
                                                                   @NotNull final UUID uuid,
                                                                   @NotNull final AccountPermission permission,
                                                                   final boolean value) {

    return economy.async()
            .map(async -> async.updateAccountPermission(pluginName, accountID, uuid, permission, value))
            .orElseGet(() -> CompletableFuture.completedFuture(economy.updateAccountPermission(pluginName, accountID, uuid, permission, value)));
  }
}