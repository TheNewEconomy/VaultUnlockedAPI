# VaultUnlockedAPI - Abstraction Library API for Bukkit Plugins - [![Build Status](https://ci.codemc.io/job/creatorfromhell/job/VaultUnlockedAPI/badge/icon)](https://ci.codemc.io/job/creatorfromhell/job/VaultUnlockedAPI/)

How to include the API with Maven: 
```xml
<repositories>
    <repository>
        <id>codemc-repo</id>
        <url>https://repo.codemc.org/repository/maven-public</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>net.milkbowl.vault</groupId>
        <artifactId>VaultUnlockedAPI</artifactId>
        <version>2.2</version><!-- Validate this is the most recent version from the CI -->
        <scope>provided</scope>
    </dependency>
</dependencies>
```

How to include the API with Gradle:
```groovy
repositories {
    maven { url 'https://repo.codemc.org/repository/maven-public' }
}
dependencies {
    compileOnly "net.milkbowl.vault:VaultUnlockedAPI:2.1"
}
```

**Note**: The VaultUnlockedAPI version has 2 numbers (major.minor), unlike Vault, which has 3. The 2
numbers in the VaultUnlockedAPI will always correspond to the 2 beginning numbers in a VaultUnlocked
version to make it clear what versions your plugin will for sure work with.

## Why VaultUnlocked?
I have no preference regarding which library is best suited for
your plugin development efforts. I believe a central suite (or "Vault")
of solutions is a more effective approach than focusing on a single
category of plugins. This is the concept behind VaultUnlocked.

### Key Features You'll Appreciate

* **No Source Code Integration Needed**
  VaultUnlocked operates as a standalone plugin, so you only need to obtain an instance of it. This prevents conflicts with multiple plugins using the same namespaces. Simply include VaultUnlocked.jar in your download zip file for seamless integration!
* **Extensive Plugin Support**
  VaultUnlocked provides an abstraction layer not just for Economic plugins but for Permission plugins as well, ensuring broad compatibility.
* **Freedom of Choice**
  One of the best aspects of Bukkit is the freedom to choose what to use. More options benefit developers, so here’s to embracing choice!

### Enhanced Features of VaultUnlocked

* **Multi-Currency Support**
* **More Friendly PR Acceptance**
* **Folia Support**

Let me know if you need any further modifications!

## License
Copyright (C) 2024 Daniel "creatorfromhell" Vidmar

VaultUnlocked is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

VaultUnlocked is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Vault.  If not, see <http://www.gnu.org/licenses/>.

## Building
VaultUnlockedAPI comes with all libraries needed to build from the current branch.

## Implementing VaultUnlocked
Implementing VaultUnlocked is quite simple. It requires getting the Economy, Permission, or Chat service from the Bukkit ServiceManager. See the example below:

```java
package com.example.plugin;

import java.util.logging.Logger;

import net.milkbowl.vault2.chat.Chat;
import net.milkbowl.vault2.economy.Economy;
import net.milkbowl.vault2.economy.EconomyResponse;
import net.milkbowl.vault2.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onDisable() {
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
    }
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            getLogger().info("Only players are supported for this Example Plugin, but you should not do this!!!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if(command.getLabel().equals("test-economy")) {
            // Lets give the player 1.05 currency (note that SOME economic plugins require rounding!)
            sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getUniqueId()))));
            EconomyResponse r = econ.depositPlayer(player.getUniqueId(), new BigDecimal("1.05"));
            if(r.transactionSuccess()) {
                sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
            } else {
                sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
            return true;
        } else if(command.getLabel().equals("test-permission")) {
            // Lets test if user has the node "example.plugin.awesome" to determine if they are awesome or just suck
            if(perms.has(player, "example.plugin.awesome")) {
                sender.sendMessage("You are awesome!");
            } else {
                sender.sendMessage("You suck!");
            }
            return true;
        } else {
            return false;
        }
    }
    
    public static Economy getEconomy() {
        return econ;
    }
    
    public static Permission getPermissions() {
        return perms;
    }
    
    public static Chat getChat() {
        return chat;
    }
}
```
