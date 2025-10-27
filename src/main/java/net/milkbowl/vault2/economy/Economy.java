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

import net.milkbowl.vault2.economy.EconomyResponse.ResponseType;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
   * Some economy plugins round off after a certain number of digits. This function returns the
   * number of digits the plugin keeps or -1 if no rounding occurs.
   *
   * @param pluginName The name of the plugin that is calling the method.
   *
   * @return number of digits after the decimal point this plugin supports or -1 if no rounding
   * occurs.
   */
  int fractionalDigits(@NotNull final String pluginName);

  /**
   * Retrieves the number of fractional digits for the specified currency associated with the given
   * plugin. This function returns the number of digits the plugin keeps or -1 if no rounding
   * occurs.
   * <p>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName the name of the plugin
   * @param currency   the currency for which the fractional digits are to be retrieved
   *
   * @return the number of fractional digits for the specified currency or -1 if no rounding occurs.
   */
  default int fractionalDigits(@NotNull final String pluginName, @NotNull final String currency) {

    return fractionalDigits(pluginName);
  }

  /**
   * Plugins use this method to format a given BigDecimal amount into a human-readable amount using
   * your economy plugin's currency names/conventions.
   *
   * @param amount to format.
   *
   * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
   *
   * @see #fractionalDigits(String)
   * @deprecated This method is deprecated as of version 2.8, and has been replaced by
   * {@link #format(String, BigDecimal)}. This allows economy plugins to know exactly if the account
   * is a player or not. This may be removed in a future release.
   */
  @NotNull
  @Deprecated
  String format(@NotNull final BigDecimal amount);

  /**
   * Plugins use this method to format a given BigDecimal amount into a human-readable amount using
   * your economy plugin's currency names/conventions.
   *
   * @param pluginName The name of the plugin that is calling the method.
   * @param amount     to format.
   *
   * @return Human-readable String describing amount, ie 5 Dollars or 5.55 Pounds.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  String format(@NotNull final String pluginName, @NotNull final BigDecimal amount);

  /**
   * Plugins use this method to format a given BigDecimal amount into a human-readable amount using
   * your economy plugin's currency names/conventions.
   * <p>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param amount   to format.
   * @param currency the currency to use for the format.
   *
   * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
   *
   * @see #fractionalDigits(String)
   * @deprecated This method is deprecated as of version 2.8, and has been replaced by
   * {@link #format(String, BigDecimal, String)}. This allows economy plugins to know exactly if the
   * account is a player or not. This may be removed in a future release.
   */
  @NotNull
  @Deprecated
  String format(@NotNull final BigDecimal amount, @NotNull final String currency);

  /**
   * Plugins use this method to format a given BigDecimal amount into a human-readable amount using
   * your economy plugin's currency names/conventions.
   * <p>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param amount     to format.
   * @param currency   the currency to use for the format.
   *
   * @return Human-readable String describing amount, ie 5 Dollars or 5.55 Pounds.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  String format(@NotNull final String pluginName, @NotNull final BigDecimal amount, @NotNull final String currency);

  /**
   * Returns true if a currency with the specified name exists.
   * <p>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param currency the currency to use.
   *
   * @return true if a currency with the specified name exists.
   */
  boolean hasCurrency(@NotNull final String currency);

  /**
   * Used to get the default currency. This could be the default currency for the server globally or
   * for the default world if the implementation supports multi-world.
   * <p>
   * If the economy being used does not support currency names, then the currency name itself
   * should be returned.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   *
   * @return The currency that is the default for the server if multi-world support is not available
   * otherwise the default for the default world.
   *
   */
  @NotNull
  String getDefaultCurrency(@NotNull final String pluginName);

  /**
   * Returns the name of the default currency in plural form.
   * <p>
   * If the economy being used does not support currency names, then the currency name itself
   * should be returned.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   *
   * @return name of the currency (plural) ie: Dollars or Pounds.
   */
  @NotNull
  String defaultCurrencyNamePlural(@NotNull final String pluginName);

  /**
   * Returns the name of the default currency in singular form.
   * <p>
   * If the economy being used does not support currency names, then the currency name itself
   * should be returned.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   *
   * @return name of the currency (singular) ie: Dollar or Pound.
   */
  @NotNull
  String defaultCurrencyNameSingular(@NotNull final String pluginName);

  /**
   * Returns a list of currencies used by the economy plugin. These are able to be used in the calls
   * in the methods of the API. May not be human-readable.
   *
   * @return list of currencies used by the economy plugin. These are able to be used in the calls
   * in the methods of the API.
   */
  @NotNull
  Collection<String> currencies();

  /*
   * Account-related methods follow.
   */
  /**
   * Attempts to create an account for the given UUID.
   *
   * @param accountID UUID associated with the account.
   * @param name      the human-readable name to associate with the account.
   *
   * @return true if the account creation was successful.
   *
   * @deprecated This method is deprecated as of version 2.8, and has been replaced by
   * {@link #createAccount(UUID, String, String, boolean)}. This allows economy plugins to know
   * exactly if the account is a player or not. This may be removed in a future release.
   */
  @Deprecated
  boolean createAccount(@NotNull final UUID accountID, @NotNull final String name);

  /**
   * Creates a new account with the provided information.
   *
   * @param accountID The UUID of the account to be created.
   * @param name      The name associated with the account.
   * @param player    A flag indicating if the account is a player account.
   *
   * @return true if the account was successfully created, false otherwise.
   */
  boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, final boolean player);

  /**
   * Attempts to create an account for the given UUID on the specified world
   * <p>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param accountID UUID associated with the account.
   * @param name      the human-readable name to associate with the account.
   * @param worldName String name of the world.
   *
   * @return if the account creation was successful
   *
   * @deprecated This method is deprecated as of version 2.8, and has been replaced by
   * {@link #createAccount(UUID, String, String, boolean)}. This allows economy plugins to know
   * exactly if the account is a player or not. This may be removed in a future release.
   */
  @Deprecated
  boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, @NotNull final String worldName);

  /**
   * Creates a new account with the given parameters.
   * <p>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param accountID The UUID of the account to be created.
   * @param name      The name of the account holder.
   * @param worldName The world name associated with the account.
   * @param player    A boolean indicating if the account belongs to a player.
   *
   * @return True if the account was successfully created, false otherwise.
   */
  boolean createAccount(@NotNull final UUID accountID, @NotNull final String name, @NotNull final String worldName, final boolean player);

  /**
   * Returns a map that represents all the UUIDs which have accounts in the plugin, as well as their
   * last-known-name. This is used for Vault's economy converter and should be given every account
   * available.
   *
   * @return a {@link Map} composed of the accounts keyed by their UUID, along with their associated
   * last-known-name.
   */
  @NotNull
  Map<UUID, String> getUUIDNameMap();

  /**
   * Gets the last known name of an account owned by the given UUID. Required for messages to be
   * more human-readable than UUIDs alone can provide.
   *
   * @param accountID UUID associated with the account.
   *
   * @return An optional containing the last-known name if the account exists, otherwise an empty
   * optional.
   */
  Optional<String> getAccountName(@NotNull final UUID accountID);

  /**
   * Checks if this UUID has an account yet.
   *
   * @param accountID UUID to check for an existing account.
   *
   * @return true if the UUID has an account.
   */
  boolean hasAccount(@NotNull final UUID accountID);

  /**
   * Checks if this UUID has an account yet in the given world.
   * <p>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param accountID UUID to check for an existing account.
   * @param worldName world-specific account.
   *
   * @return if the UUID has an account.
   */
  boolean hasAccount(@NotNull final UUID accountID, @NotNull final String worldName);

  /**
   * A method which changes the name associated with the given UUID in the value returned from
   * {@link #getUUIDNameMap()}.
   *
   * @param accountID UUID whose account is having a name change.
   * @param name      the human-readable name to associate with the account.
   *
   * @return true if the name change is successful.
   */
  boolean renameAccount(@NotNull final UUID accountID, @NotNull final String name);

  /**
   * Renames the account with the specified ID in the given plugin to the new name.
   *
   * @param plugin    The plugin name where the account exists
   * @param accountID The unique identifier of the account to be renamed
   * @param name      The new name to assign to the account
   *
   * @return true if the rename operation was successful, false otherwise
   */
  boolean renameAccount(@NotNull final String plugin, @NotNull final UUID accountID, @NotNull final String name);

  /**
   * Deletes the account associated with the specified UUID.
   *
   * @param plugin    the name of the plugin managing the account
   * @param accountID the UUID of the account to be deleted
   *
   * @return true if the account was successfully deleted, false otherwise
   */
  boolean deleteAccount(@NotNull final String plugin, @NotNull final UUID accountID);

  /*
   * Account balance related methods follow.
   */

  /**
   * Determines whether an account supports a specific currency.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param plugin    the name of the plugin
   * @param accountID the UUID of the account
   * @param currency  the currency to check support for.
   *
   * @return true if the account supports the currency, false otherwise
   */
  boolean accountSupportsCurrency(@NotNull final String plugin, @NotNull final UUID accountID, @NotNull final String currency);

  /**
   * Checks if the given account supports the specified currency in the given world.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param plugin    the name of the plugin requesting the check
   * @param accountID the UUID of the player account
   * @param currency  the currency code to check support for
   * @param world     the name of the world to check in
   *
   * @return true if the account supports the currency in the world, false otherwise
   */
  boolean accountSupportsCurrency(@NotNull final String plugin, @NotNull final UUID accountID, @NotNull final String currency, @NotNull final String world);

  /**
   * Gets balance of an account associated with a UUID.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * </p>
   * @param pluginName The name of the plugin that is calling the method. This is for logging purposes only.
   * @param accountID  UUID of the account to get a balance for.
   *
   * @return Amount currently held in account associated with the given UUID.
   *
   * @deprecated This method is deprecated as of version 2.9, and has been replaced by
   * {@link #balance(String, UUID)}. This allows economy plugins to know exactly if the account is a
   * player or not. This may be removed in a future release.
   */
  @NotNull
  @Deprecated
  BigDecimal getBalance(@NotNull final String pluginName, @NotNull final UUID accountID);

  /**
   * Gets balance of a UUID on the specified world.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  UUID of the account to get a balance for.
   * @param world      name of the world.
   *
   * @return Amount currently held in account associated with the given UUID.
   *
   * @deprecated This method is deprecated as of version 2.9, and has been replaced by
   * {@link #balance(String, UUID, String)}. This allows economy plugins to know exactly if the
   * account is a player or not. This may be removed in a future release.
   */
  @NotNull
  @Deprecated
  BigDecimal getBalance(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String world);

  /**
   * Gets balance of a UUID on the specified world and currency.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method. This is for logging purposes only.
   * @param accountID  UUID of the account to get a balance for.
   * @param world      name of the world.
   * @param currency   the currency to use.
   *
   * @return Amount currently held in account associated with the given UUID.
   *
   * @deprecated This method is deprecated as of version 2.9, and has been replaced by
   * {@link #balance(String, UUID, String, String)}. This allows economy plugins to know exactly if
   * the account is a player or not. This may be removed in a future release.
   */
  @NotNull
  @Deprecated
  BigDecimal getBalance(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String world, @NotNull final String currency);

  /**
   * Gets balance of an account associated with a UUID.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * </p>
   * @param pluginName The name of the plugin that is calling the method. This is for logging purposes only.
   * @param accountID  UUID of the account to get a balance for.
   *
   * @return Amount currently held in account associated with the given UUID.
   */
  @NotNull
  default BigDecimal balance(@NotNull final String pluginName, @NotNull final UUID accountID) {

    return getBalance(pluginName, accountID);
  }

  /**
   * Gets balance of a UUID on the specified world.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method. This is for logging purposes only.
   * @param accountID  UUID of the account to get a balance for.
   * @param world      name of the world.
   *
   * @return Amount currently held in account associated with the given UUID.
   */
  @NotNull
  default BigDecimal balance(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String world) {

    return getBalance(pluginName, accountID, world);
  }

  /**
   * Gets balance of a UUID on the specified world and currency.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method. This is for logging purposes only.
   * @param accountID  UUID of the account to get a balance for.
   * @param world      name of the world.
   * @param currency   the currency to use.
   *
   * @return Amount currently held in account associated with the given UUID.
   */
  @NotNull
  default BigDecimal balance(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String world, @NotNull final String currency) {

    return getBalance(pluginName, accountID, world, currency);
  }

  /**
   * Checks if the account associated with the given UUID has the amount
   * <p>
   * Note: Negative amounts should not be used. Use deposit methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * </p>
   * @param pluginName The name of the plugin that is calling the method. This is for logging purposes only.
   * @param accountID  the UUID associated with the account to check the balance of.
   * @param amount     the amount to check for.
   *
   * @return True if <b>UUID</b> has <b>amount</b>, False else wise.
   *
   * @see #fractionalDigits(String)
   */
  boolean has(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final BigDecimal amount);

  /**
   * Checks if the account associated with the given UUID has the amount in the given world
   * <p>
   * Note: Negative amounts should not be used. Use deposit methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the UUID associated with the account to check the balance of.
   * @param worldName  the name of the world to check in.
   * @param amount     the amount to check for.
   *
   * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>, False else wise.
   *
   * @see #fractionalDigits(String)
   */
  boolean has(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount);

  /**
   * Checks if the account associated with the given UUID has the amount in the given world
   * <p>
   * Note: Negative amounts should not be used. Use deposit methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the UUID associated with the account to check the balance of.
   * @param worldName  the name of the world to check in.
   * @param currency   the currency to use.
   * @param amount     the amount to check for.
   *
   * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>, False else wise.
   *
   * @see #fractionalDigits(String)
   */
  boolean has(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount);

  /**
   *
   * Sets the amount of monies for a player.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * </p>
   * @param pluginName the name of the plugin setting the currency
   * @param accountID  the unique identifier of the player's account
   * @param amount     the amount of currency to set for the player in the specified world
   *
   * @return an EconomyResponse object indicating the result of the operation
   */
  default EconomyResponse set(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final BigDecimal amount) {

    final BigDecimal balance = balance(pluginName, accountID);
    final int compare = balance.compareTo(amount);
    if(compare > 0) {
      return withdraw(pluginName, accountID, balance.subtract(amount));
    }

    if(compare < 0) {
      return deposit(pluginName, accountID, amount.subtract(balance));
    }

    return new EconomyResponse(BigDecimal.ZERO, amount, ResponseType.SUCCESS, "");
  }

  /**
   *
   * Sets the amount of monies for a player in a specific world.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param pluginName the name of the plugin setting the currency
   * @param accountID  the unique identifier of the player's account
   * @param worldName  the name of the world where the currency amount is being set
   * @param amount     the amount of currency to set for the player in the specified world
   *
   * @return an EconomyResponse object indicating the result of the operation
   */
  default EconomyResponse set(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount) {

    final BigDecimal balance = balance(pluginName, accountID, worldName);
    final int compare = balance.compareTo(amount);
    if(compare > 0) {
      return withdraw(pluginName, accountID, worldName, balance.subtract(amount));
    }

    if(compare < 0) {
      return deposit(pluginName, accountID, worldName, amount.subtract(balance));
    }

    return new EconomyResponse(BigDecimal.ZERO, amount, ResponseType.SUCCESS, "");
  }

  /**
   *
   * Sets the amount of specified currency for a player in a specific world.
   * <p>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName the name of the plugin setting the currency
   * @param accountID  the unique identifier of the player's account
   * @param worldName  the name of the world where the currency amount is being set
   * @param currency   the name of the currency being set
   * @param amount     the amount of currency to set for the player in the specified world
   *
   * @return an EconomyResponse object indicating the result of the operation
   */
  default EconomyResponse set(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount) {

    final BigDecimal balance = balance(pluginName, accountID, worldName, currency);
    final int compare = balance.compareTo(amount);
    if(compare > 0) {
      return withdraw(pluginName, accountID, worldName, currency, balance.subtract(amount));
    }

    if(compare < 0) {
      return deposit(pluginName, accountID, worldName, currency, amount.subtract(balance));
    }

    return new EconomyResponse(BigDecimal.ZERO, amount, ResponseType.SUCCESS, "");
  }

  /**
   * Withdraw an amount from an account associated with a UUID
   * <p>
   * Note: Negative amounts should not be used. Use deposit methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the UUID associated with the account to withdraw from.
   * @param amount     Amount to withdraw.
   *
   * @return {@link EconomyResponse} which includes the Economy plugin's {@link ResponseType} as to
   * whether the transaction was a Success, Failure, Unsupported.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  EconomyResponse withdraw(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final BigDecimal amount);

  /**
   * Withdraw an amount from an account associated with a UUID on a given world
   * <p>
   * Note: Negative amounts should not be used. Use deposit methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the UUID associated with the account to withdraw from.
   * @param worldName  the name of the world to check in.
   * @param amount     Amount to withdraw.
   *
   * @return {@link EconomyResponse} which includes the Economy plugin's {@link ResponseType} as to
   * whether the transaction was a Success, Failure, Unsupported.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  EconomyResponse withdraw(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount);

  /**
   * Withdraw an amount from an account associated with a UUID on a given world
   * <p>
   * Note: Negative amounts should not be used. Use deposit methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the UUID associated with the account to withdraw from.
   * @param worldName  the name of the world to check in.
   * @param currency   the currency to use.
   * @param amount     Amount to withdraw.
   *
   * @return {@link EconomyResponse} which includes the Economy plugin's {@link ResponseType} as to
   * whether the transaction was a Success, Failure, Unsupported.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  EconomyResponse withdraw(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount);

  /**
   * Deposit an amount to an account associated with the given UUID
   * <p>
   * Note: Negative amounts should not be used. Use withdraw methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the UUID associated with the account to deposit to.
   * @param amount     Amount to deposit.
   *
   * @return {@link EconomyResponse} which includes the Economy plugin's {@link ResponseType} as to
   * whether the transaction was a Success, Failure, Unsupported.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  EconomyResponse deposit(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final BigDecimal amount);

  /**
   * Deposit an amount to an account associated with a UUID on a given world
   * <p>
   * Note: Negative amounts should not be used. Use withdraw methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the {@link UUID} associated with the account to deposit to.
   * @param worldName  the name of the world to check in.
   * @param amount     Amount to deposit.
   *
   * @return {@link EconomyResponse} which includes the Economy plugin's {@link ResponseType} as to
   * whether the transaction was a Success, Failure, Unsupported.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  EconomyResponse deposit(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final BigDecimal amount);

  /**
   * Deposit an amount to an account associated with a UUID on a given world
   * <p>
   * Note: Negative amounts should not be used. Use withdraw methods instead.
   * <br>
   * Note: {@code pluginName} should be used for logging/diagnostics only and MUST NOT affect
   * business logic.
   * <br>
   * If the provider does not support multiple worlds, the provider's default world will be used.
   * <br>
   * If the provider does not support multi-currency, the provider's default currency will be
   * used.
   * </p>
   * @param pluginName The name of the plugin that is calling the method.
   * @param accountID  the {@link UUID} associated with the account to deposit to.
   * @param worldName  the name of the world to check in.
   * @param currency   the currency to use.
   * @param amount     Amount to deposit.
   *
   * @return {@link EconomyResponse} which includes the Economy plugin's {@link ResponseType} as to
   * whether the transaction was a Success, Failure, Unsupported.
   *
   * @see #fractionalDigits(String)
   */
  @NotNull
  EconomyResponse deposit(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String worldName, @NotNull final String currency, @NotNull final BigDecimal amount);

  /*
   * Shared Account Methods
   */

  /**
   * Creates a shared account with the specified parameters.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName the name of the plugin
   * @param accountID  the {@link UUID} of the account
   * @param name       the name of the account
   * @param owner      the {@link UUID} of the account owner
   *
   * @return true if the shared account is successfully created, false otherwise
   */
  boolean createSharedAccount(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final String name, @NotNull final UUID owner);

  /**
   * Retrieves a list of account IDs owned by the specified account ID.
   * <p>
   * If the provider does not support shared accounts, this will return an unmodifiable empty list.
   * </p>
   * @param pluginName the name of the plugin
   * @param accountID  the unique identifier of the account
   *
   * @return a list of account names owned by the specified account ID
   *
   * @since 2.14
   * @deprecated Use {@link #accountsWithOwnerOf(String, UUID)} instead as the string return allows ambiguity.
   */
  default List<String> accountsOwnedBy(@NotNull final String pluginName, @NotNull final UUID accountID) {

    return accountsAccessTo(pluginName, accountID, AccountPermission.OWNER);
  }

  /**
   * Retrieves a list of account IDs owned by the specified account ID.
   * <p>
   * If the provider does not support shared accounts, this will return an unmodifiable empty list.
   * </p>
   * @param pluginName the name of the plugin
   * @param accountID  the unique identifier of the account
   *
   * @return a list of account UUID owned by the specified account ID
   *
   * @since 2.17
   */
  default List<UUID> accountsWithOwnerOf(@NotNull final String pluginName, @NotNull final UUID accountID) {

    return accountsWithAccessTo(pluginName, accountID, AccountPermission.OWNER);
  }

  /**
   * Retrieves a list of account IDs that the specified account is a member of.
   * <p>
   * If the provider does not support shared accounts, this will return an unmodifiable empty list.
   * </p>
   * @param pluginName the name of the plugin
   * @param accountID  the UUID of the account to check membership for
   *
   * @return a List of String values representing the accounts that the account is a member of
   *
   * @since 2.14
   * @deprecated Use {@link #accountsWithMembershipTo(String, UUID)} instead as the string return allows ambiguity.
   */
  default List<String> accountsMemberOf(@NotNull final String pluginName, @NotNull final UUID accountID) {

    return accountsAccessTo(pluginName, accountID, AccountPermission.BALANCE, AccountPermission.DEPOSIT, AccountPermission.WITHDRAW);
  }

  /**
   * Retrieves a list of account IDs that the specified account is a member of.
   * <p>
   * If the provider does not support shared accounts, this will return an unmodifiable empty list.
   * </p>
   * @param pluginName the name of the plugin
   * @param accountID  the UUID of the account to check membership for
   *
   * @return a List of UUID values representing the accounts that the account is a member of
   *
   * @since 2.17
   */
  default List<UUID> accountsWithMembershipTo(@NotNull final String pluginName, @NotNull final UUID accountID) {

    return accountsWithAccessTo(pluginName, accountID, AccountPermission.BALANCE, AccountPermission.DEPOSIT, AccountPermission.WITHDRAW);
  }

  /**
   * Retrieves a list of account IDs that the specified account has the specified permissions for.
   * <p>
   * If the provider does not support shared accounts, this will return an unmodifiable empty list.
   * </p>
   * @param pluginName  the name of the plugin
   * @param accountID   the UUID of the account to check access for
   * @param permissions variable number of permissions to check for
   *
   * @return a list of accounts that the account has the specified permissions to
   *
   * @since 2.14
   * @deprecated Use {@link #accountsWithAccessTo(String, UUID, AccountPermission...)} instead as the string return allows ambiguity.
   */
  @Deprecated
  default List<String> accountsAccessTo(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final AccountPermission... permissions) {

    return Collections.unmodifiableList(new ArrayList<>());
  }

  /**
   * Retrieves a list of account IDs that the specified account has the specified permissions for.
   * <p>
   * If the provider does not support shared accounts, this will return an unmodifiable empty list.
   * </p>
   * @param pluginName  the name of the plugin
   * @param accountID   the UUID of the account to check access for
   * @param permissions variable number of permissions to check for
   *
   * @return a list of accounts that the account has the specified permissions to
   *
   * @since 2.17
   */
  default List<UUID> accountsWithAccessTo(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final AccountPermission... permissions) {

    return Collections.unmodifiableList(new ArrayList<>());
  }

  /**
   * Determines whether the specified owner ID is the owner of the account associated with the given
   * account ID and plugin name.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName the name of the plugin
   * @param accountID  the {@link UUID} of the account
   * @param uuid       the {@link UUID} to check for ownership of the account
   *
   * @return true if the owner ID is the owner of the account, false otherwise
   */
  boolean isAccountOwner(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid);

  /**
   * Sets the owner of a specified plugin to the given accountID.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName The name of the plugin.
   * @param accountID  The {@link UUID} of the account
   * @param uuid       The {@link UUID} of the account to set as the owner.
   *
   * @return true if the owner is successfully set, false otherwise.
   */
  boolean setOwner(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid);

  /**
   * Determines whether a specific member is an account member of a given plugin.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName The name of the plugin.
   * @param accountID  The {@link UUID} of the account.
   * @param uuid       The {@link UUID} to check for membership.
   *
   * @return true if the member is an account member, false otherwise.
   */
  boolean isAccountMember(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid);

  /**
   * Adds a member to an account.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName The name of the plugin.
   * @param accountID  The {@link UUID} of the account.
   * @param uuid       The {@link UUID} of the member to be added.
   *
   * @return true if the member was successfully added, false otherwise.
   */
  boolean addAccountMember(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid);

  /**
   * Adds a member to an account with the specified initial permissions.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName         The name of the plugin.
   * @param accountID          The {@link UUID} of the account.
   * @param uuid               The {@link UUID} of the member to be added.
   * @param initialPermissions The initial permissions to be assigned to the member. The values for
   *                           these should be assumed to be "true."
   *
   * @return true if the member was added successfully, false otherwise.
   */
  boolean addAccountMember(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission... initialPermissions);

  /**
   * Removes a member from an account.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName the name of the plugin managing the account
   * @param accountID  the {@link UUID} of the account
   * @param uuid       the {@link UUID} of the member to be removed
   *
   * @return true if the member was successfully removed, false otherwise
   */
  boolean removeAccountMember(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid);

  /**
   * Checks if the specified account has the given permission for the given plugin.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName the name of the plugin to check permission for
   * @param accountID  the {@link UUID} of the account
   * @param uuid       the {@link UUID} to check for the permission
   * @param permission the permission to check for
   *
   * @return true if the account has the specified permission, false otherwise
   */
  boolean hasAccountPermission(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission);

  /**
   * Updates the account permission for a specific plugin and user.
   * <p>
   * If the provider does not support shared accounts, this will return false.
   * </p>
   * @param pluginName the name of the plugin
   * @param accountID  the {@link UUID} of the account
   * @param uuid       the {@link UUID} to update the permission for
   * @param permission the new account permissions to set
   * @param value      the new permission value to set for this value
   *
   * @return true if the account permission was successfully updated, false otherwise
   */
  boolean updateAccountPermission(@NotNull final String pluginName, @NotNull final UUID accountID, @NotNull final UUID uuid, @NotNull final AccountPermission permission, final boolean value);
}
