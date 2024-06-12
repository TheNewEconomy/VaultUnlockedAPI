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

package net.milkbowl.vault2.economy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.milkbowl.vault2.economy.EconomyResponse.ResponseType;

/**
 * The main economy API
 *
 */
public interface Economy {

    /*
     * Economy plugin-related methods follow.
     */

    /**
     * Checks if economy plugin is enabled.
     * 
     * @return true if the server's economy plugin has properly enabled.
     */
    boolean isEnabled();

    /**
     * Gets name of the economy plugin.
     * 
     * @return Name of the active economy plugin on the server.
     */
    String getName();

    /**
     * Returns true if the economy plugin supports banks.
     * 
     * @return true if the economy plugin supports banks.
     */
    boolean hasBankSupport();

    /**
     * Returns true if the economy plugin supports multiple currencies.
     *
     * @return true if the economy plugin supports multiple currencies.
     */
    boolean hasMultiCurrencySupport();

    /*
     * Currency-related methods follow.
     */

    /**
     * Some economy plugins round off after a certain number of digits. This
     * function returns the number of digits the plugin keeps or -1 if no rounding
     * occurs.
     * 
     * @return number of digits after the decimal point this plugin supports or -1
     *         if no rounding occurs.
     */
    int fractionalDigits();

    /**
     * Plugins use this method to format a given BigDecimal amount into a human-readable
     * amount using your economy plugin's currency names/conventions.
     *
     * @param amount to format.
     * @param currency the currency to use for the format.
     *
     * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
     */
    String format(BigDecimal amount, String currency);

    /**
     * Returns true if a currency with the specified name exists.
     *
     * @param currency the currency to use.
     *
     * @return true if a currency with the specified name exists.
     */
    boolean hasCurrency(String currency);

    /**
     * Returns the default currency that is able to be used in API operations. May not be human-readable.
     * 
     * @return name of the currency i.e.: USD
     */
    String defaultCurrency();

    /**
     * Returns the name of the default currency in plural form. If the economy being used
     * does not support currency names then an empty string will be returned.
     *
     * @return name of the currency (plural) ie: Dollars or Pounds.
     */
    String defaultCurrencyNamePlural();

    /**
     * Returns the name of the default currency in singular form. If the economy being used
     * does not support currency names then an empty string will be returned.
     * 
     * @return name of the currency (singular) ie: Dollar or Pound.
     */
    String defaultCurrencyNameSingular();

    /**
     * Returns a list of currencies used by the economy plugin. These are able to be used
     * in the calls in the methods of the API. May not be human-readable.
     *
     * @return list of currencies used by the economy plugin. These are able to be used
     * in the calls in the methods of the API.
     */
    List<String> currencies();

    /*
     * Account-related methods follow.
     */

    /**
     * Attempts to create a account for the given UUID.
     * 
     * @param uuid UUID associated with the account.
     * @param name UUID associated with the account.
     * @return true if the account creation was successful.
     */
    boolean createAccount(UUID uuid, String name);

    /**
     * Attempts to create an account for the given UUID on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then
     * false will always be returned.
     * 
     * @param uuid      UUID associated with the account.
     * @param name      UUID associated with the account.
     * @param worldName String name of the world.
     * @return if the account creation was successful
     */
    boolean createAccount(UUID uuid, String name, String worldName);

    /**
     * Returns a map that represents all the UUIDs which have accounts in the
     * plugin, as well as their last-known-name. This is used for Vault's economy
     * converter and should be given every account available.
     * 
     * @return a {@link Map} composed of the accounts keyed by their UUID, along
     *         with their associated last-known-name.
     */
    Map<UUID, String> getUUIDNameMap();

    /**
     * Gets the last known name of an account owned by the given UUID. Required for
     * messages to be more human-readable than UUIDs alone can provide.
     * 
     * @param uuid UUID associated with the account.
     * @return name of the account owner.
     */
    String getAccountName(UUID uuid);

    /**
     * Checks if this UUID has an account yet.
     * 
     * @param uuid UUID to check for an existing account.
     * @return true if the UUID has an account.
     */
    boolean hasAccount(UUID uuid);

    /**
     * Checks if this UUID has an account yet on the given world.
     * 
     * @param uuid      UUID to check for an existing account.
     * @param worldName world-specific account.
     * @return if the UUID has an account.
     */
    boolean hasAccount(UUID uuid, String worldName);

    /**
     * A method which changes the name associated with the given UUID in the
     * Map<UUID, String> received from {@link #getUUIDNameMap()}.
     * 
     * @param uuid UUID whose account is having a name change.
     * @param name String name that will be associated with the UUID in the 
     *             Map<UUID, String> map.
     * @return true if the name change is successful.
     */
    boolean renameAccount(UUID uuid, String name);

    /*
     * Account balance related methods follow.
     */

    /**
     * Gets balance of an account associated with a UUID.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid UUID of the account to get a balance for.
     * @return Amount currently held in account associated with the given UUID.
     */
    BigDecimal getBalance(String pluginName, UUID uuid);

    /**
     * Gets balance of a UUID on the specified world. IMPLEMENTATION SPECIFIC - if
     * an economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid  UUID of the account to get a balance for.
     * @param world name of the world.
     * @return Amount currently held in account associated with the given UUID.
     */
    BigDecimal getBalance(String pluginName, UUID uuid, String world);

    /**
     * Gets balance of a UUID on the specified world. IMPLEMENTATION SPECIFIC - if
     * an economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid  UUID of the account to get a balance for.
     * @param world name of the world.
     * @param currency the currency to use.
     * @return Amount currently held in account associated with the given UUID.
     */
    BigDecimal getBalance(String pluginName, UUID uuid, String world, String currency);

    /**
     * Checks if the account associated with the given UUID has the amount - DO NOT
     * USE NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   the UUID associated with the account to check the balance of.
     * @param amount the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b>, False else wise.
     */
    boolean has(String pluginName, UUID uuid, BigDecimal amount);

    /**
     * Checks if the account associated with the given UUID has the amount in the
     * given world - DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an
     * economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid      the UUID associated with the account to check the balance of.
     * @param worldName the name of the world to check in.
     * @param amount    the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>,
     *         False else wise.
     */
    boolean has(String pluginName, UUID uuid, String worldName, BigDecimal amount);

    /**
     * Checks if the account associated with the given UUID has the amount in the
     * given world - DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an
     * economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid      the UUID associated with the account to check the balance of.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>,
     *         False else wise.
     */
    boolean has(String pluginName, UUID uuid, String worldName, String currency, BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   the UUID associated with the account to withdraw from.
     * @param amount Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse withdraw(String pluginName, UUID uuid, BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid      the UUID associated with the account to withdraw from.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse withdraw(String pluginName, UUID uuid, String worldName, BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid      the UUID associated with the account to withdraw from.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse withdraw(String pluginName, UUID uuid, String worldName, String currency, BigDecimal amount);

    /**
     * Deposit an amount to an account associated with the given UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   the UUID associated with the account to deposit to.
     * @param amount Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse deposit(String pluginName, UUID uuid, BigDecimal amount);

    /**
     * Deposit an amount to an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid      the UUID associated with the account to deposit to.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse deposit(String pluginName, UUID uuid, String worldName, BigDecimal amount);

    /**
     * Deposit an amount to an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid      the UUID associated with the account to deposit to.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse deposit(String pluginName, UUID uuid, String worldName, String currency, BigDecimal amount);

    /*
     * Bank methods follow.
     */

    /**
     * Creates a bank account with the specified name and the given UUID as the
     * owner.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param name Name of account.
     * @param uuid UUID of the account should be linked to.
     * @return true if bank creation is successful.
     */
    boolean createBank(String pluginName, String name, UUID uuid);

    /**
     * Deletes a bank account with the specified UUID.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid UUID of the bank to be deleted.
     * @return true if the operation completed successfully
     */
    boolean deleteBank(String pluginName, UUID uuid);

    /**
     * Returns a map that represents all of the UUIDs which have banks in the
     * plugin, as well as their last-known-name. This is used for Vault's economy
     * converter and should be given every account available.
     * 
     * @return a {@link Map} composed of the accounts keyed by their UUID, along
     *         with their associated last-known-name.
     */
    Map<UUID, String> getBankUUIDNameMap();

    /**
     * Gets the last known name of an bank with the given UUID. Required for
     * messages to be more human-readable than UUIDs alone can provide.
     *
     * @param uuid UUID to look up.
     * @return name of the bank.
     */
    String getBankAccountName(UUID uuid);

    /**
     * Checks if this UUID has a bank yet.
     *
     * @param uuid UUID to check.
     * @return true if the UUID has an account.
     */
    boolean hasBankAccount(UUID uuid);

    /**
     * Checks if the specified bank account supports the specified currency.
     *
     * @param uuid UUID of the account.
     * @param currency the currency to use.
     * @return true if the bank supports the currency
     */
    boolean bankSupportsCurrency(UUID uuid, String currency);

    /**
     * A method which changes the name associated with the given UUID in the
     * Map<UUID, String> received from {@link #getBankUUIDNameMap()}.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid UUID which is having a name change.
     * @param name name that will be associated with the UUID in the 
     *             Map<UUID, String> map.
     * @return true if the name change is successful.
     */
    boolean renameBankAccount(String pluginName, UUID uuid, String name);

    /**
     * Returns the amount the bank has.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid UUID of the account.
     * @return amount which the bank holds as a balance.
     */
    BigDecimal bankBalance(String pluginName, UUID uuid);

    /**
     * Returns the amount the bank has.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid UUID of the account.
     * @param currency the currency to use.
     * @return amount which the bank holds as a balance.
     */
    BigDecimal bankBalance(String pluginName, UUID uuid, String currency);

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   UUID of the account.
     * @param amount to check for
     * @return true if the bank has the given amount.
     */
    boolean bankHas(String pluginName, UUID uuid, BigDecimal amount);

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   UUID of the account.
     * @param currency the currency to use.
     * @param amount to check for
     * @return true if the bank has the given amount.
     */
    boolean bankHas(String pluginName, UUID uuid, String currency, BigDecimal amount);

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   UUID of the account.
     * @param amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse bankWithdraw(String pluginName, UUID uuid, BigDecimal amount);

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   UUID of the account.
     * @param currency the currency to use.
     * @param amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse bankWithdraw(String pluginName, UUID uuid, String currency, BigDecimal amount);

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   UUID of the account.
     * @param amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse bankDeposit(String pluginName, UUID uuid, BigDecimal amount);

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param uuid   UUID of the account.
     * @param currency the currency to use.
     * @param amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    EconomyResponse bankDeposit(String pluginName, UUID uuid, String currency, BigDecimal amount);

    /**
     * Check if a UUID is the owner of a bank account.
     *
     * @param uuid     UUID of the player/object who might be an owner.
     * @param bankUUID UUID of the bank account to check ownership of.
     * @return true if the uuid is the owner of the bank associated with bankUUID.
     */
    boolean isBankOwner(UUID uuid, UUID bankUUID);

    /**
     * Check if the UUID is a member of the bank account
     *
     * @param uuid     UUID of the player/object who might be a member.
     * @param bankUUID UUID of the bank account to check membership of.
     * @return @return true if the uuid is a member of the bank associated with bankUUID.
     */
    boolean isBankMember(UUID uuid, UUID bankUUID);

    /**
     * Gets the list of banks' UUIDs.
     * 
     * @return the List of Banks' UUIDs.
     */
    List<UUID> getBanks();
}
