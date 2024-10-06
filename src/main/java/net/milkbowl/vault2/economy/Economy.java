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

import net.kyori.adventure.text.Component;
import net.milkbowl.vault2.economy.EconomyResponse.ResponseType;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
    @NotNull
    String getName();

    /**
     * Returns true if the economy plugin supports shared accounts.
     *
     * @return true if the economy plugin supports shared accounts.
     */
    boolean hasSharedAccountSupport();

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
     * @param pluginName The name of the plugin that is calling the method.
     * @return number of digits after the decimal point this plugin supports or -1
     *         if no rounding occurs.
     */
    @NotNull
    int fractionalDigits(final String pluginName);

    /**
     * Plugins use this method to format a given BigDecimal amount into a human-readable
     * amount using your economy plugin's currency names/conventions.
     *
     * @param amount to format.
     *
     * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
     * @deprecated Use {@link #format(String, BigDecimal)} instead.
     */
    @NotNull
    String format(BigDecimal amount);

    /**
     * Plugins use this method to format a given BigDecimal amount into a human-readable
     * amount using your economy plugin's currency names/conventions.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param amount to format.
     *
     * @return Human-readable {@link Component text component} describing amount, ie 5 Dollars or 5.55 Pounds.
     */
    @NotNull
    Component format(final String pluginName, BigDecimal amount);

    /**
     * Plugins use this method to format a given BigDecimal amount into a human-readable
     * amount using your economy plugin's currency names/conventions.
     *
     * @param amount to format.
     * @param currency the currency to use for the format.
     *
     * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
     * @deprecated Use {@link #format(String, BigDecimal, String)} instead.
     */
    @NotNull
    String format(BigDecimal amount, final String currency);

    /**
     * Plugins use this method to format a given BigDecimal amount into a human-readable
     * amount using your economy plugin's currency names/conventions.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param amount to format.
     * @param currency the currency to use for the format.
     *
     * @return Human-readable {@link Component text component} describing amount, ie 5 Dollars or 5.55 Pounds.
     */
    @NotNull
    Component format(final String pluginName, BigDecimal amount, final String currency);

    /**
     * Returns true if a currency with the specified name exists.
     *
     * @param currency the currency to use.
     *
     * @return true if a currency with the specified name exists.
     */
    boolean hasCurrency(final String currency);

    /**
     * Used to get the default currency. This could be the default currency for the server globally or
     * for the default world if the implementation supports multi-world.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @return The currency that is the default for the server if multi-world support is not available
     * otherwise the default for the default world.
     *
     */
    @NotNull
    String getDefaultCurrency(final String pluginName);

    /**
     * Returns the name of the default currency in plural form. If the economy being used
     * does not support currency names then an empty string will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @return name of the currency (plural) ie: Dollars or Pounds.
     */
    @NotNull
    String defaultCurrencyNamePlural(final String pluginName);

    /**
     * Returns the name of the default currency in singular form. If the economy being used
     * does not support currency names then an empty string will be returned.
     * 
     * @param pluginName The name of the plugin that is calling the method.
     * @return name of the currency (singular) ie: Dollar or Pound.
     */
    @NotNull
    String defaultCurrencyNameSingular(final String pluginName);

    /**
     * Returns a list of currencies used by the economy plugin. These are able to be used
     * in the calls in the methods of the API. May not be human-readable.
     *
     * @return list of currencies used by the economy plugin. These are able to be used
     * in the calls in the methods of the API.
     */
    Collection<String> currencies();

    /*
     * Account-related methods follow.
     */

    /**
     * Attempts to create a account for the given UUID.
     * 
     * @param accountID UUID associated with the account.
     * @param name UUID associated with the account.
     * @return true if the account creation was successful.
     */
    boolean createAccount(UUID accountID, final String name);

    /**
     * Attempts to create an account for the given UUID on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then
     * false will always be returned.
     * 
     * @param accountID      UUID associated with the account.
     * @param name      UUID associated with the account.
     * @param worldName String name of the world.
     * @return if the account creation was successful
     */
    boolean createAccount(UUID accountID, final String name, final String worldName);

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
     * @param accountID UUID associated with the account.
     * @return An optional containing the last known name if the account exists, otherwise an empty
     * optional.
     */
    Optional<String> getAccountName(UUID accountID);

    /**
     * Checks if this UUID has an account yet.
     * 
     * @param accountID UUID to check for an existing account.
     * @return true if the UUID has an account.
     */
    boolean hasAccount(UUID accountID);

    /**
     * Checks if this UUID has an account yet on the given world.
     * 
     * @param accountID      UUID to check for an existing account.
     * @param worldName world-specific account.
     * @return if the UUID has an account.
     */
    boolean hasAccount(UUID accountID, final String worldName);

    /**
     * A method which changes the name associated with the given UUID in the
     * Map<UUID, final String> received from {@link #getUUIDNameMap()}.
     * 
     * @param accountID UUID whose account is having a name change.
     * @param name String name that will be associated with the UUID in the 
     *             Map<UUID, final String> map.
     * @return true if the name change is successful.
     */
    boolean renameAccount(UUID accountID, final String name);

    /*
     * Account balance related methods follow.
     */

    /**
     * Determines whether an account supports a specific currency.
     *
     * @param plugin    the name of the plugin
     * @param accountID      the UUID of the account
     * @param currency  the currency to check support for
     * @return true if the account supports the currency, false otherwise
     */
    boolean accountSupportsCurrency(final String plugin, final UUID accountID, final String currency);

    /**
     * Checks if the given account supports the specified currency in the given world.
     *
     * @param plugin   the name of the plugin requesting the check
     * @param accountID     the UUID of the player account
     * @param currency the currency code to check support for
     * @param world    the name of the world to check in
     * @return true if the account supports the currency in the world, false otherwise
     */
    boolean accountSupportsCurrency(final String plugin, final UUID accountID, final String currency, final String world);

    /**
     * Gets balance of an account associated with a UUID.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID UUID of the account to get a balance for.
     * @return Amount currently held in account associated with the given UUID.
     */
    @NotNull
    BigDecimal getBalance(final String pluginName, final UUID accountID);

    /**
     * Gets balance of a UUID on the specified world. IMPLEMENTATION SPECIFIC - if
     * an economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID  UUID of the account to get a balance for.
     * @param world name of the world.
     * @return Amount currently held in account associated with the given UUID.
     */
    @NotNull
    BigDecimal getBalance(final String pluginName, final UUID accountID, final String world);

    /**
     * Gets balance of a UUID on the specified world. IMPLEMENTATION SPECIFIC - if
     * an economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID  UUID of the account to get a balance for.
     * @param world name of the world.
     * @param currency the currency to use.
     * @return Amount currently held in account associated with the given UUID.
     */
    @NotNull
    BigDecimal getBalance(final String pluginName, final UUID accountID, final String world, final String currency);

    /**
     * Checks if the account associated with the given UUID has the amount - DO NOT
     * USE NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID   the UUID associated with the account to check the balance of.
     * @param amount the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b>, False else wise.
     */
    boolean has(final String pluginName, final UUID accountID, final BigDecimal amount);

    /**
     * Checks if the account associated with the given UUID has the amount in the
     * given world - DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an
     * economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID      the UUID associated with the account to check the balance of.
     * @param worldName the name of the world to check in.
     * @param amount    the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>,
     *         False else wise.
     */
    boolean has(final String pluginName, final UUID accountID, final String worldName, final BigDecimal amount);

    /**
     * Checks if the account associated with the given UUID has the amount in the
     * given world - DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an
     * economy plugin does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID      the UUID associated with the account to check the balance of.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>,
     *         False else wise.
     */
    boolean has(final String pluginName, final UUID accountID, final String worldName, final String currency, final BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID   the UUID associated with the account to withdraw from.
     * @param amount Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @NotNull
    EconomyResponse withdraw(final String pluginName, final UUID accountID, final BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID      the UUID associated with the account to withdraw from.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @NotNull
    EconomyResponse withdraw(final String pluginName, final UUID accountID, final String worldName, final BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID      the UUID associated with the account to withdraw from.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @NotNull
    EconomyResponse withdraw(final String pluginName, final UUID accountID, final String worldName, final String currency, final BigDecimal amount);

    /**
     * Deposit an amount to an account associated with the given UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID   the UUID associated with the account to deposit to.
     * @param amount Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @NotNull
    EconomyResponse deposit(final String pluginName, final UUID accountID, final BigDecimal amount);

    /**
     * Deposit an amount to an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID  the {@link UUID} associated with the account to deposit to.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @NotNull
    EconomyResponse deposit(final String pluginName, final UUID accountID, final String worldName, final BigDecimal amount);

    /**
     * Deposit an amount to an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     *
     * @param pluginName The name of the plugin that is calling the method.
     * @param accountID      the {@link UUID} associated with the account to deposit to.
     * @param worldName the name of the world to check in.
     * @param currency the currency to use.
     * @param amount    Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    @NotNull
    EconomyResponse deposit(final String pluginName, final UUID accountID, final String worldName, final String currency, final BigDecimal amount);

    /*
     * Shared Account Methods
     */

    /**
     * Creates a shared account with the specified parameters.
     *
     * @param pluginName the name of the plugin
     * @param accountID  the {@link UUID} of the account
     * @param name       the name of the account
     * @param owner      the {@link UUID} of the account owner
     * @return true if the shared account is successfully created, false otherwise
     */
    boolean createSharedAccount(final String pluginName, final UUID accountID, final String name, final UUID owner);

    /**
     * Determines whether the specified owner ID is the owner of the account associated with the given account ID and plugin name.
     *
     * @param pluginName the name of the plugin
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} to check for ownership of the account
     * @return true if the owner ID is the owner of the account, false otherwise
     */
    boolean isAccountOwner(final String pluginName, final UUID accountID, final UUID uuid);

    /**
     * Sets the owner of a specified plugin to the given accountID.
     *
     * @param pluginName The name of the plugin.
     * @param accountID  The {@link UUID} of the account
     * @param uuid       The {@link UUID} of the account to set as the owner.
     * @return true if the owner is successfully set, false otherwise.
     */
    boolean setOwner(final String pluginName, final UUID accountID, final UUID uuid);

    /**
     * Determines whether a specific member is an account member of a given plugin.
     *
     * @param pluginName The name of the plugin.
     * @param accountID The {@link UUID} of the account.
     * @param uuid The {@link UUID} to check for membership.
     * @return true if the member is an account member, false otherwise.
     */
    boolean isAccountMember(final String pluginName, final UUID accountID, final UUID uuid);

    /**
     * Adds a member to an account.
     *
     * @param pluginName The name of the plugin.
     * @param accountID  The {@link UUID} of the account.
     * @param uuid       The {@link UUID} of the member to be added.
     * @return true if the member was successfully added, false otherwise.
     */
    boolean addAccountMember(final String pluginName, final UUID accountID, final UUID uuid);

    /**
     * Adds a member to an account with the specified initial permissions.
     *
     * @param pluginName The name of the plugin.
     * @param accountID The {@link UUID} of the account.
     * @param uuid The {@link UUID} of the member to be added.
     * @param initialPermissions The initial permissions to be assigned to the member. The values for
     *                           these should be assumed to be "true."
     * @return true if the member was added successfully, false otherwise.
     */
    boolean addAccountMember(final String pluginName, final UUID accountID, final UUID uuid, final AccountPermission... initialPermissions);

    /**
     * Removes a member from an account.
     *
     * @param pluginName the name of the plugin managing the account
     * @param accountID the {@link UUID} of the account
     * @param uuid the {@link UUID} of the member to be removed
     * @return true if the member was successfully removed, false otherwise
     */
    boolean removeAccountMember(final String pluginName, final UUID accountID, final UUID uuid);

    /**
     * Checks if the specified account has the given permission for the given plugin.
     *
     * @param pluginName   the name of the plugin to check permission for
     * @param accountID    the {@link UUID} of the account
     * @param uuid         the {@link UUID} to check for the permission
     * @param permission   the permission to check for
     * @return true if the account has the specified permission, false otherwise
     */
    boolean hasAccountPermission(final String pluginName, final UUID accountID, final UUID uuid, final AccountPermission permission);

    /**
     * Updates the account permission for a specific plugin and user.
     *
     * @param pluginName   the name of the plugin
     * @param accountID    the {@link UUID} of the account
     * @param uuid         the {@link UUID} to update the permission for
     * @param permission   the new account permissions to set
     * @param value        the new permission value to set for this value
     * @return true if the account permission was successfully updated, false otherwise
     */
    boolean updateAccountPermission(final String pluginName, final UUID accountID, final UUID uuid, final AccountPermission permission, final boolean value);
}
