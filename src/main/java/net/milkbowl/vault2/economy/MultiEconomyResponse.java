package net.milkbowl.vault2.economy;/* This file is part of Vault.

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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * MultiEconomyResponse
 *
 * @author creatorfromhell
 * @since 2.20
 */
public class MultiEconomyResponse {

  private final Map<UUID, BigDecimal> balances = new HashMap<>();

  /**
   * Amount modified by calling method
   */
  public final BigDecimal amount;
  /**
   * Success or failure of call. Using Enum of ResponseType to determine valid
   * outcomes
   */
  public final EconomyResponse.ResponseType type;
  /**
   * Error message if the variable 'type' is ResponseType.FAILURE
   */
  public final String errorMessage;

  public MultiEconomyResponse(final BigDecimal amount, final EconomyResponse.ResponseType type, final String errorMessage) {

    this.amount = amount;
    this.type = type;
    this.errorMessage = errorMessage;
  }

  /**
   * Updates the balance of the entity identified by the specified UUID.
   * If a balance already exists for the UUID, it will be replaced with the given amount.
   *
   * @param uuid the unique identifier of the entity whose balance is to be updated
   * @param amount the new balance amount to associate with the specified UUID
   */
  public void addBalance(final UUID uuid, final BigDecimal amount) {
    balances.put(uuid, amount);
  }

  /**
   * Retrieves the balance associated with the specified UUID.
   *
   * @param uuid the unique identifier of the entity whose balance is to be fetched
   * @return an {@code Optional} containing the balance if it exists, or an empty {@code Optional} if no balance is found
   */
  public Optional<BigDecimal> balance(final UUID uuid) {
    return Optional.ofNullable(balances.get(uuid));
  }

  public BigDecimal amount() {

    return amount;
  }

  public EconomyResponse.ResponseType type() {

    return type;
  }

  public String errorMessage() {

    return errorMessage;
  }
}