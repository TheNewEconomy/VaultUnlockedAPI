# VaultUnlocked Economy Support Badge: Minimum Implementation Standard

The following document outlines the minimum implementation standard required for projects to achieve the VaultUnlocked Economy Support Badge. The requirements are divided into two categories: **Economy Plugins** (providers of the economy) and **Economy User Plugins** (e.g., banks, chest shops).

---

## **Standards Checklist**

### **1. Economy Plugins**
Economy Plugins must implement the following core functionality from the Economy interface to provide comprehensive economy functionality:

#### **Required Functionality**
- Fully Implement the non-default methods.
- Implement the methods for optional functionality:
  - boolean hasSharedAccountSupport();
  - boolean hasMultiCurrencySupport();
- If your plugin doesn't support the multi-currency functionality, defaulting to your base currency, and
specifying this on your project page, or GitHub repository.

#### **Optional Features**
Economy Plugins may optionally implement shared account and permission-related APIs:
- Shared Account Methods:
    - createSharedAccount(String pluginName, UUID accountID, String name, UUID owner)
    - isAccountOwner(String pluginName, UUID accountID, UUID uuid)
    - addAccountMember(String pluginName, UUID accountID, UUID uuid, AccountPermission... permissions)
    - removeAccountMember(String pluginName, UUID accountID, UUID uuid)
    - hasAccountPermission(String pluginName, UUID accountID, UUID uuid, AccountPermission permission)
    - updateAccountPermission(String pluginName, UUID accountID, UUID uuid, AccountPermission permission, boolean value)

---

### **2. Economy User Plugins**
Economy User Plugins, such as banks, chest shops, or auction houses, must implement a minimal set of functionality from the Economy interface to achieve "minimum implementation."

#### **Required Methods**

1. **Account Balance Management:**
    - balance(String pluginName, UUID accountID):
        - Retrieve the balance for a specific account.
    - withdraw(String pluginName, UUID accountID, BigDecimal amount):
        - Withdraw a specified amount from an account.
    - deposit(String pluginName, UUID accountID, BigDecimal amount):
        - Deposit a specified amount into an account.

---

#### **Multi-Currency Support**
If you're third-party plugin doesn't support using a different currency for your plugin, please make
sure to have this specified on the project page, or GitHub repository.

### **3. Evidence of Multi-Currency Support (For Economy Plugins Only)**
Economy Plugins must demonstrate multi-currency support by:
- Properly implementing hasMultiCurrencySupport() and returning true if supported.
- Providing functional implementations for:
    - currencies()
    - getDefaultCurrency(String pluginName)

---

## **Submission Requirements**
1. **Economy Plugins:**
    - A GitHub ticket detailing:
        - **Project Name**
        - **Usage of VaultUnlocked**
        - **Description of multi-currency support implementation** (if applicable).
    - Evidence of multi-currency support (if implemented) in the form of logs or outputs.

2. **Economy User Plugins:**
    - A GitHub ticket detailing:
        - **Project Name**
        - **Usage of VaultUnlocked** (e.g., bank, shop, auction house).
    - Evidence of successful implementation of the required balance-related methods through logs or outputs.

---

By adhering to these standards, your project will demonstrate compatibility with the VaultUnlocked Economy API and earn the Economy Support Badge. While optional features enhance the plugin's functionality, implementing the required methods ensures a baseline level of integration and interoperability.