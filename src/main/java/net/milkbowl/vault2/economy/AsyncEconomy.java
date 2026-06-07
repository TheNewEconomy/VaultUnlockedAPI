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
 * AsyncEconomy
 *
 * @author creatorfromhell
 * @since 2.20
 */
public interface AsyncEconomy {

  /**
   * Asynchronously creates an account with the specified details.
   *
   * @param accountID The unique identifier for the account. Must not be null.
   * @param name      The name associated with the account. Must not be null.
   * @param player    Indicates whether the account belongs to a player (true) or not (false).
   *
   * @return A CompletableFuture that resolves to a Boolean indicating the success of the account
   * creation.
   */
  @NotNull
  CompletableFuture<Boolean> createAccount(@NotNull UUID accountID, @NotNull String name, boolean player);

  /**
   * Asynchronously creates an account with the specified details.
   *
   * @param accountID The unique identifier for the account. Must not be null.
   * @param name      The name associated with the account. Must not be null.
   * @param worldName The name of the world associated with the account. Must not be null.
   * @param player    Indicates whether the account is for a player (true) or not (false).
   *
   * @return A CompletableFuture that resolves to true if the account creation was successful, or
   * false otherwise.
   */
  @NotNull
  CompletableFuture<Boolean> createAccount(@NotNull UUID accountID,
                                           @NotNull String name,
                                           @NotNull String worldName,
                                           boolean player);

  /**
   * Asynchronously retrieves a map where the keys are UUIDs and the values are corresponding
   * names.
   *
   * @return a CompletableFuture that, when completed, provides a map of UUIDs to names.
   */
  @NotNull
  CompletableFuture<Map<UUID, String>> getUUIDNameMap();

  /**
   * Asynchronously retrieves the account name associated with the given account ID.
   *
   * @param accountID the unique identifier of the account for which the name is being retrieved;
   *                  must not be null.
   *
   * @return a CompletableFuture containing an Optional of the account name. If the account name is
   * present, it will be wrapped in the Optional. Otherwise, the Optional will be empty.
   */
  @NotNull
  CompletableFuture<Optional<String>> getAccountName(@NotNull UUID accountID);

  /**
   * Asynchronously checks whether an account exists for the specified account ID.
   *
   * @param accountID the unique identifier of the account to check; must not be null
   *
   * @return a CompletableFuture that will complete with {@code true} if the account exists,
   * {@code false} otherwise
   */
  @NotNull
  CompletableFuture<Boolean> hasAccount(@NotNull UUID accountID);

  /**
   * Asynchronously checks if an account exists for the given account ID within the specified
   * world.
   *
   * @param accountID the unique identifier of the account to check; must not be null.
   * @param worldName the name of the world where the account is being verified; must not be null.
   *
   * @return a CompletableFuture that completes with {@code true} if the account exists, or
   * {@code false} if it does not.
   */
  @NotNull
  CompletableFuture<Boolean> hasAccount(@NotNull UUID accountID, @NotNull String worldName);

  /**
   * Renames an account asynchronously based on the provided plugin name, account ID, and new name.
   *
   * @param pluginName the name of the plugin managing the account
   * @param accountID  the unique identifier of the account to be renamed
   * @param name       the new name to assign to the account
   *
   * @return a CompletableFuture that completes with a Boolean indicating whether the account was
   * successfully renamed
   */
  @NotNull
  CompletableFuture<Boolean> renameAccount(@NotNull String pluginName,
                                           @NotNull UUID accountID,
                                           @NotNull String name);

  /**
   * Asynchronously deletes an account identified by the specified plugin name and account ID.
   *
   * @param pluginName the name of the plugin associated with the account to be deleted, must not be
   *                   null
   * @param accountID  the unique identifier of the account to be deleted, must not be null
   *
   * @return a CompletableFuture that resolves to {@code true} if the account deletion is
   * successful, or {@code false} otherwise
   */
  @NotNull
  CompletableFuture<Boolean> deleteAccount(@NotNull String pluginName, @NotNull UUID accountID);

  /*
   * Balance Methods
   */

  /**
   * Asynchronously checks if the given account supports the specified currency within the context
   * of the provided plugin.
   *
   * @param pluginName the name of the plugin managing the account and currency; must not be null
   * @param accountID  the unique identifier of the account to check; must not be null
   * @param currency   the currency identifier to check for support; must not be null
   *
   * @return a CompletableFuture that resolves to {@code true} if the account supports the currency,
   * or {@code false} otherwise
   */
  @NotNull
  CompletableFuture<Boolean> accountSupportsCurrency(@NotNull String pluginName,
                                                     @NotNull UUID accountID,
                                                     @NotNull String currency);

  /**
   * Asynchronously checks if a specific account supports the provided currency in the given world.
   *
   * @param pluginName the name of the plugin initiating the request; must not be null.
   * @param accountID  the unique identifier of the account to check; must not be null.
   * @param currency   the currency to verify support for; must not be null.
   * @param world      the world in which to check the currency support; must not be null.
   *
   * @return a CompletableFuture that will complete with a boolean indicating whether the account
   * supports the specified currency in the specified world.
   */
  @NotNull
  CompletableFuture<Boolean> accountSupportsCurrency(@NotNull String pluginName,
                                                     @NotNull UUID accountID,
                                                     @NotNull String currency,
                                                     @NotNull String world);

  /**
   * Asynchronously retrieves the balance for the specified account associated with a given plugin.
   *
   * @param pluginName the name of the plugin for which the balance is requested; must not be null
   * @param accountID  the unique identifier of the account; must not be null
   *
   * @return a CompletableFuture containing the balance as a BigDecimal; never null
   */
  @NotNull
  CompletableFuture<BigDecimal> balance(@NotNull String pluginName,@NotNull UUID accountID);

  /**
   * Asynchronously retrieves the balance of a specific account within a given world for the
   * specified plugin.
   *
   * @param pluginName the name of the plugin managing the account balances, must not be null
   * @param accountID  the unique identifier of the account to retrieve the balance for, must not be
   *                   null
   * @param world      the name of the world for which the account balance is being queried, must
   *                   not be null
   *
   * @return a CompletableFuture containing the balance as a BigDecimal when the operation is
   * completed
   */
  @NotNull
  CompletableFuture<BigDecimal> balance(@NotNull String pluginName,
                                        @NotNull UUID accountID,
                                        @NotNull String world);

  /**
   * Asynchronously retrieves the balance of a specified account for a given world and currency.
   *
   * @param pluginName the name of the plugin requesting the balance, must not be null
   * @param accountID the unique identifier of the account, must not be null
   * @param world the name of the world for which the balance is being retrieved, must not be null
   * @param currency the currency for which the balance is being retrieved, must not be null
   * @return a CompletableFuture containing the balance as a BigDecimal upon completion
   */
  @NotNull
  CompletableFuture<BigDecimal> balance(@NotNull String pluginName,
                                        @NotNull UUID accountID,
                                        @NotNull String world,
                                        @NotNull String currency);

  /**
   * Asynchronously checks if the specified plugin has the required attributes
   * associated with the given account and amount.
   *
   * @param pluginName the name of the plugin to be checked, must not be null
   * @param accountID the unique identifier of the account, must not be null
   * @param amount the monetary value to be verified, must not be null
   * @return a CompletableFuture containing a Boolean result indicating the outcome of the check
   */
  @NotNull
  CompletableFuture<Boolean> has(@NotNull String pluginName,
                                 @NotNull UUID accountID,
                                 @NotNull BigDecimal amount);

  @NotNull
  CompletableFuture<Boolean> has(@NotNull String pluginName,
                                 @NotNull UUID accountID,
                                 @NotNull String world,
                                 @NotNull BigDecimal amount);

  /**
   * Checks asynchronously if a specific plugin has the required data or configuration
   * for the given account, world, currency, and amount.
   *
   * @param pluginName the name of the plugin to check
   * @param accountID the unique identifier of the account
   * @param world the world associated with the query
   * @param currency the currency type to check
   * @param amount the amount to verify
   * @return a CompletableFuture that resolves to a boolean indicating the result of the check
   */
  @NotNull
  CompletableFuture<Boolean> has(@NotNull String pluginName,
                                 @NotNull UUID accountID,
                                 @NotNull String world,
                                 @NotNull String currency,
                                 @NotNull BigDecimal amount);

  /*
   * Transactions
   */

  /**
   * Updates the balance of an account asynchronously.
   *
   * @param pluginName the name of the plugin requesting the operation, must not be null.
   * @param accountID the unique identifier of the account to update, must not be null.
   * @param amount the new amount to set for the account's balance, must not be null.
   * @return a CompletableFuture that, when completed, will contain the result of the operation as an EconomyResponse.
   */
  @NotNull
  CompletableFuture<EconomyResponse> set(@NotNull String pluginName,
                                         @NotNull UUID accountID,
                                         @NotNull BigDecimal amount);

  /**
   * Sets the specified amount asynchronously for the given account in the specified world.
   *
   * @param pluginName the name of the plugin initiating the request; must not be null
   * @param accountID the unique identifier of the account to modify; must not be null
   * @param world the name of the world where the account resides; must not be null
   * @param amount the amount to be set in the account; must not be null
   * @return a CompletableFuture that completes with an EconomyResponse containing the result of the operation; never null
   */
  @NotNull
  CompletableFuture<EconomyResponse> set(@NotNull String pluginName,
                                         @NotNull UUID accountID,
                                         @NotNull String world,
                                         @NotNull BigDecimal amount);

  /**
   * Asynchronously sets the balance for a specific account in the specified world and currency.
   *
   * @param pluginName the name of the plugin initiating the balance update, must not be null
   * @param accountID the unique identifier of the account whose balance is to be set, must not be null
   * @param world the name of the world in which the account balance is to be updated, must not be null
   * @param currency the currency in which the balance is to be set, must not be null
   * @param amount the amount to set for the account balance, must not be null
   * @return a {@link CompletableFuture} that completes with the result of the balance update encapsulated
   *         in an {@link EconomyResponse}, never null
   */
  @NotNull
  CompletableFuture<EconomyResponse> set(@NotNull String pluginName,
                                         @NotNull UUID accountID,
                                         @NotNull String world,
                                         @NotNull String currency,
                                         @NotNull BigDecimal amount);

  /**
   * Asynchronously transfers a specified monetary amount from one user to another within the context of a given plugin.
   * The method ensures atomicity by attempting to revert the withdrawal if the deposit fails.
   *
   * @param pluginName the name of the plugin initiating the transfer; must not be null
   * @param from the unique identifier (UUID) of the user account from which the amount will be withdrawn; must not be null
   * @param to the unique identifier (UUID) of the user account to which the amount will be deposited; must not be null
   * @param amount the monetary amount to transfer; must not be null
   * @return a CompletableFuture containing an {@code MultiEconomyResponse} detailing the status of the transfer,
   *         including the final balances, success/failure type, and error messages (if any)
   */
  CompletableFuture<MultiEconomyResponse> transfer(@NotNull final String pluginName,
                                                   @NotNull final UUID from,
                                                   @NotNull final UUID to,
                                                   @NotNull final BigDecimal amount);

  /**
   * Asynchronously transfers a specific amount of currency from one account to another within the same world.
   * The transfer is performed as a withdrawal from the source account followed by a deposit
   * into the target account. If the deposit operation fails, the withdrawn amount is refunded
   * to the source account to ensure consistency.
   *
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
  CompletableFuture<MultiEconomyResponse> transfer(@NotNull final String pluginName,
                                                   @NotNull final UUID from,
                                                   @NotNull final UUID to,
                                                   @NotNull final String worldName,
                                                   @NotNull final BigDecimal amount);

  /**
   * Asynchronously transfers a specified amount of currency from one account to another within a specific world.
   * The transfer involves withdrawing the amount from the source account and depositing it into
   * the target account. If the deposit fails, the withdrawn amount is returned to the source account
   * to ensure consistency.
   *
   * @param pluginName the name of the plugin initiating the transfer
   * @param from the unique identifier (UUID) of the source account
   * @param to the unique identifier (UUID) of the target account
   * @param worldName the name of the world in which the transfer is taking place
   * @param currency the name of the currency being transferred
   * @param amount the amount of currency to transfer
   * @return a CompletableFuture containing an {@link MultiEconomyResponse} containing information about the result of the transfer,
   *         including success status, balances, and error messages if applicable
   */
  CompletableFuture<MultiEconomyResponse> transfer(@NotNull final String pluginName,
                                                   @NotNull final UUID from,
                                                   @NotNull final UUID to,
                                                   @NotNull final String worldName,
                                                   @NotNull final String currency,
                                                   @NotNull final BigDecimal amount);

  /**
   * Determines asynchronously if the specified amount can be withdrawn from the given account.
   *
   * @param pluginName the name of the plugin requesting the withdrawal check; must not be null
   * @param accountID the unique identifier of the account to check; must not be null
   * @param amount the amount to check for withdrawal eligibility; must not be null
   * @return a {@link CompletableFuture} that, when completed, provides an {@link EconomyResponse} indicating whether 
   *         the withdrawal can proceed and additional information about the result
   */
  @NotNull
  CompletableFuture<EconomyResponse> canWithdraw(@NotNull String pluginName,
                                                 @NotNull UUID accountID,
                                                 @NotNull BigDecimal amount);

  /**
   * Checks asynchronously if a specified withdrawal operation can be performed for a given account.
   *
   * @param pluginName the name of the plugin requesting the withdrawal check, must not be null.
   * @param accountID the unique identifier of the account to check, must not be null.
   * @param world the name of the world context for the withdrawal operation, must not be null.
   * @param amount the amount to withdraw, must not be null.
   * @return a CompletableFuture containing an EconomyResponse that indicates the outcome of the check.
   */
  @NotNull
  CompletableFuture<EconomyResponse> canWithdraw(@NotNull String pluginName,
                                                 @NotNull UUID accountID,
                                                 @NotNull String world,
                                                 @NotNull BigDecimal amount);

  /**
   * Asynchronously checks if a withdrawal operation can be performed on a specified account in the given world
   * and currency for the specified amount.
   *
   * @param pluginName the name of the plugin requesting the withdrawal check; must not be null
   * @param accountID the unique identifier of the account to check; must not be null
   * @param world the name of the world where the account resides; must not be null
   * @param currency the currency in which the transaction is to be performed; must not be null
   * @param amount the amount to be checked for withdrawal; must not be null
   * @return a CompletableFuture that resolves to an EconomyResponse containing the result of the withdrawal check
   */
  @NotNull
  CompletableFuture<EconomyResponse> canWithdraw(@NotNull String pluginName,
                                                 @NotNull UUID accountID,
                                                 @NotNull String world,
                                                 @NotNull String currency,
                                                 @NotNull BigDecimal amount);

  /**
   * Asynchronously withdraws a specified amount of money from an account associated with the given UUID.
   *
   * @param pluginName the name of the plugin requesting the withdrawal; must not be null
   * @param accountID the unique identifier of the account from which the amount will be withdrawn; must not be null
   * @param amount the amount of money to withdraw; must not be null
   * @return a CompletableFuture that will complete with an EconomyResponse indicating the result of the withdrawal 
   *         operation, or an error if the operation fails
   */
  @NotNull
  CompletableFuture<EconomyResponse> withdraw(@NotNull String pluginName,
                                              @NotNull UUID accountID,
                                              @NotNull BigDecimal amount);

  /**
   * Initiates an asynchronous operation to withdraw a specified amount from an account in a specified world context.
   *
   * @param pluginName the name of the plugin initiating the withdrawal, must not be null
   * @param accountID the unique identifier of the account from which the amount will be withdrawn, must not be null
   * @param world the name of the world in which the withdrawal is being performed, must not be null
   * @param amount the amount to withdraw from the account, must not be null
   * @return a CompletableFuture containing the result of the withdrawal operation as an EconomyResponse
   */
  @NotNull
  CompletableFuture<EconomyResponse> withdraw(@NotNull String pluginName,
                                              @NotNull UUID accountID,
                                              @NotNull String world,
                                              @NotNull BigDecimal amount);

  /**
   * Asynchronously withdraws a specified amount of currency from an account in a specific world.
   *
   * @param pluginName the name of the plugin requesting the transaction; cannot be null
   * @param accountID the unique identifier of the account from which the amount will be withdrawn; cannot be null
   * @param world the name of the world in which the transaction takes place; cannot be null
   * @param currency the currency to be withdrawn; cannot be null
   * @param amount the amount to be withdrawn from the account; cannot be null
   * @return a CompletableFuture that will complete with the result of the withdrawal operation
   */
  @NotNull
  CompletableFuture<EconomyResponse> withdraw(@NotNull String pluginName,
                                              @NotNull UUID accountID,
                                              @NotNull String world,
                                              @NotNull String currency,
                                              @NotNull BigDecimal amount);

  /**
   * Asynchronously checks if the specified amount can be deposited into the account 
   * associated with the given identifier.
   *
   * @param pluginName the name of the plugin requesting the operation; must not be null
   * @param accountID the unique identifier of the account; must not be null
   * @param amount the amount to check for deposit feasibility; must not be null
   * @return a CompletableFuture that resolves to an EconomyResponse indicating 
   *         whether the deposit is allowed or not
   */
  @NotNull
  CompletableFuture<EconomyResponse> canDeposit(@NotNull String pluginName,
                                                @NotNull UUID accountID,
                                                @NotNull BigDecimal amount);

  /**
   * Asynchronously checks if a specified amount can be deposited into an account.
   *
   * @param pluginName The name of the plugin initiating the check. Must not be null.
   * @param accountID The unique identifier of the account to check. Must not be null.
   * @param world The name of the world associated with the check. Must not be null.
   * @param amount The amount to be deposited. Must not be null.
   * @return A CompletableFuture that, when completed, will contain an EconomyResponse indicating 
   *         whether the deposit can be completed and additional response details.
   */
  @NotNull
  CompletableFuture<EconomyResponse> canDeposit(@NotNull String pluginName,
                                                @NotNull UUID accountID,
                                                @NotNull String world,
                                                @NotNull BigDecimal amount);

  /**
   * Asynchronously checks if a specified amount can be deposited into a player's account
   * for a given plugin, world, and currency.
   *
   * @param pluginName The name of the plugin requesting the deposit operation. Must not be null.
   * @param accountID The unique identifier of the account to check. Must not be null.
   * @param world The name of the world where the deposit is being checked. Must not be null.
   * @param currency The identifier of the currency for the deposit. Must not be null.
   * @param amount The amount to check for deposit eligibility. Must not be null.
   * @return A CompletableFuture holding the result of the deposit eligibility check
   *         encapsulated in an {@link EconomyResponse}.
   */
  @NotNull
  CompletableFuture<EconomyResponse> canDeposit(@NotNull String pluginName,
                                                @NotNull UUID accountID,
                                                @NotNull String world,
                                                @NotNull String currency,
                                                @NotNull BigDecimal amount);

  /**
   * Asynchronously deposits a specified amount into the account identified by the given account ID.
   *
   * @param pluginName the name of the plugin requesting the deposit; must not be null
   * @param accountID the UUID of the account where the deposit is to be made; must not be null
   * @param amount the amount to deposit into the account; must not be null
   * @return a CompletableFuture containing an EconomyResponse that indicates the result of the deposit operation
   */
  @NotNull
  CompletableFuture<EconomyResponse> deposit(@NotNull String pluginName,
                                             @NotNull UUID accountID,
                                             @NotNull BigDecimal amount);

  /**
   * Asynchronously deposits a specified amount into an account within a defined world context for a given plugin.
   *
   * @param pluginName the name of the plugin initiating the deposit, must not be null
   * @param accountID the unique identifier of the account to deposit funds into, must not be null
   * @param world the name of the world where the deposit is occurring, must not be null
   * @param amount the amount of money to deposit, must not be null
   * @return a CompletableFuture that resolves to an EconomyResponse indicating the result of the deposit operation
   */
  @NotNull
  CompletableFuture<EconomyResponse> deposit(@NotNull String pluginName,
                                             @NotNull UUID accountID,
                                             @NotNull String world,
                                             @NotNull BigDecimal amount);

  /**
   * Asynchronously deposits the specified amount of currency into a given account.
   *
   * @param pluginName the name of the plugin initiating the deposit, must not be null
   * @param accountID the unique identifier of the account to deposit into, must not be null
   * @param world the world in which the transaction is taking place, must not be null
   * @param currency the name of the currency being deposited, must not be null
   * @param amount the amount of currency to deposit, must not be null
   * @return a CompletableFuture that resolves to an EconomyResponse containing the result of the deposit operation
   */
  @NotNull
  CompletableFuture<EconomyResponse> deposit(@NotNull String pluginName,
                                             @NotNull UUID accountID,
                                             @NotNull String world,
                                             @NotNull String currency,
                                             @NotNull BigDecimal amount);

  /*
   * Shared Accounts
   */

  /**
   * Asynchronously creates a shared account in the specified plugin with the provided details.
   *
   * @param pluginName the name of the plugin where the shared account will be created; must not be null
   * @param accountID the unique identifier of the shared account to be created; must not be null
   * @param name the name of the shared account; must not be null
   * @param owner the unique identifier of the owner of the shared account; must not be null
   * @return a {@code CompletableFuture} that resolves to {@code true} if the shared account is successfully created,
   *         or {@code false} if the operation fails
   */
  @NotNull
  CompletableFuture<Boolean> createSharedAccount(@NotNull String pluginName,
                                                 @NotNull UUID accountID,
                                                 @NotNull String name,
                                                 @NotNull UUID owner);

  /**
   * Asynchronously retrieves a list of account IDs associated with a specific owner.
   *
   * @param pluginName The name of the plugin for which the accounts are being queried. 
   *                   Must not be null.
   * @param accountID  The UUID of the account owner whose associated account IDs are being retrieved. 
   *                   Must not be null.
   * @return A CompletableFuture that resolves to a list of UUIDs representing the account IDs associated 
   *         with the specified owner.
   */
  @NotNull
  CompletableFuture<List<UUID>> accountsWithOwnerOf(@NotNull String pluginName,
                                                    @NotNull UUID accountID);

  /**
   * Asynchronously retrieves a list of account UUIDs that have a membership associated with the specified plugin.
   *
   * @param pluginName the name of the plugin for which memberships are being queried; must not be null
   * @param accountID the UUID of the account for which memberships are being checked; must not be null
   * @return a CompletableFuture that will complete with a list of UUIDs of accounts that have the specified membership
   */
  @NotNull
  CompletableFuture<List<UUID>> accountsWithMembershipTo(@NotNull String pluginName,
                                                         @NotNull UUID accountID);

  /**
   * Asynchronously retrieves a list of account IDs (UUIDs) that have the specified permissions
   * for the given plugin name and account ID.
   *
   * @param pluginName the name of the plugin for which access is being checked; must not be null
   * @param accountID the UUID of the account to check access for; must not be null
   * @param permissions the array of permissions being checked for the account; must not be null
   * @return a CompletableFuture that resolves to a list of UUIDs representing accounts with the specified permissions
   */
  @NotNull
  CompletableFuture<List<UUID>> accountsWithAccessTo(@NotNull String pluginName,
                                                     @NotNull UUID accountID,
                                                     @NotNull AccountPermission... permissions);

  /**
   * Asynchronously checks whether the specified user is the owner of the given account for the specified plugin.
   *
   * @param pluginName the name of the plugin to which the account belongs; must not be null.
   * @param accountID the unique identifier of the account being queried; must not be null.
   * @param uuid the unique identifier of the user being checked; must not be null.
   * @return a CompletableFuture that resolves to a Boolean indicating whether the user is the owner of the account.
   */
  @NotNull
  CompletableFuture<Boolean> isAccountOwner(@NotNull String pluginName,
                                            @NotNull UUID accountID,
                                            @NotNull UUID uuid);

  /**
   * Asynchronously sets the owner for a specific account associated with a plugin.
   *
   * @param pluginName the name of the plugin initiating the ownership change. Must not be null.
   * @param accountID the unique identifier of the account for which the owner is being set. Must not be null.
   * @param uuid the unique identifier of the new owner. Must not be null.
   * @return a {@link CompletableFuture} that resolves to {@code true} if the owner was successfully set, 
   *         or {@code false} if the operation failed.
   */
  @NotNull
  CompletableFuture<Boolean> setOwner(@NotNull String pluginName,
                                      @NotNull UUID accountID,
                                      @NotNull UUID uuid);

  /**
   * Asynchronously checks whether the specified user is a member of the given account for the provided plugin.
   *
   * @param pluginName the name of the plugin associated with the account; must not be null
   * @param accountID the unique identifier of the account to check membership for; must not be null
   * @param uuid the unique identifier of the user to verify membership for; must not be null
   * @return a {@link CompletableFuture} that resolves to {@code true} if the user is a member of the account, 
   *         or {@code false} otherwise
   */
  @NotNull
  CompletableFuture<Boolean> isAccountMember(@NotNull String pluginName,
                                             @NotNull UUID accountID,
                                             @NotNull UUID uuid);

  /**
   * Asynchronously adds a member to an account.
   *
   * @param pluginName the name of the plugin invoking this operation
   * @param accountID the unique identifier of the account to which the member will be added
   * @param uuid the unique identifier of the member to be added
   * @return a CompletableFuture that resolves to true if the operation is successful, otherwise false
   */
  @NotNull
  CompletableFuture<Boolean> addAccountMember(@NotNull String pluginName,
                                              @NotNull UUID accountID,
                                              @NotNull UUID uuid);

  /**
   * Asynchronously adds a member to an account with the specified initial permissions.
   *
   * @param pluginName       the name of the plugin requesting the operation; must not be null
   * @param accountID        the unique identifier of the account to which the member is being added; must not be null
   * @param uuid             the unique identifier of the member being added; must not be null
   * @param initialPermissions the initial permissions granted to the member for the account; must not be null
   * @return a CompletableFuture that resolves to {@code true} if the member was successfully added, or {@code false} otherwise
   */
  @NotNull
  CompletableFuture<Boolean> addAccountMember(@NotNull String pluginName,
                                              @NotNull UUID accountID,
                                              @NotNull UUID uuid,
                                              @NotNull AccountPermission... initialPermissions);

  /**
   * Removes a member from an account asynchronously.
   *
   * @param pluginName the name of the plugin initiating the request. Must not be null.
   * @param accountID the unique identifier of the account. Must not be null.
   * @param uuid the unique identifier of the member to be removed. Must not be null.
   * @return a CompletableFuture that resolves to {@code true} if the member was successfully removed, 
   *         or {@code false} if the operation failed.
   */
  @NotNull
  CompletableFuture<Boolean> removeAccountMember(@NotNull String pluginName,
                                                 @NotNull UUID accountID,
                                                 @NotNull UUID uuid);

  /**
   * Asynchronously checks if the specified account has a given permission within the context of a specific plugin.
   *
   * @param pluginName the name of the plugin to check the permission for; must not be null.
   * @param accountID the unique identifier of the account being checked; must not be null.
   * @param uuid the unique identifier of the user or entity performing the check; must not be null.
   * @param permission the specific account permission to validate; must not be null.
   * @return a CompletableFuture that resolves to {@code true} if the account has the specified permission,
   *         or {@code false} otherwise.
   */
  @NotNull
  CompletableFuture<Boolean> hasAccountPermission(@NotNull String pluginName,
                                                  @NotNull UUID accountID,
                                                  @NotNull UUID uuid,
                                                  @NotNull AccountPermission permission);

  /**
   * Updates the account permission for a specific account asynchronously.
   *
   * @param pluginName   the name of the plugin requesting the permission update
   * @param accountID    the unique identifier of the account for which the permission needs to be updated
   * @param uuid         the unique identifier representing the subject of the permission change
   * @param permission   the specific permission to be updated for the account
   * @param value        the value to set for the specified permission (true to grant, false to revoke)
   * @return             a CompletableFuture that completes with a Boolean indicating the success or failure of the operation
   */
  @NotNull
  CompletableFuture<Boolean> updateAccountPermission(@NotNull String pluginName,
                                                     @NotNull UUID accountID,
                                                     @NotNull UUID uuid,
                                                     @NotNull AccountPermission permission,
                                                     boolean value);
}