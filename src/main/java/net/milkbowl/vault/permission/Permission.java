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
package net.milkbowl.vault.permission;

import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

/**
 * The main Permission API - allows for group and player based permission tests
 *
 * @deprecated in lieu of the modern Vault2. To update alter your import to net.milkbowl.vault2.permission.
 */
public abstract class Permission {

	protected static final Logger log = Logger.getLogger("Minecraft");
    protected Plugin plugin = null;

    /**
     * Gets name of permission method
     * @return Name of Permission Method
     */
    abstract public String getName();

    /**
     * Checks if permission method is enabled.
     * @return Success or Failure
     */
    abstract public boolean isEnabled();
    
    /**
     * Returns if the permission system is or attempts to be compatible with super-perms.
     * @return True if this permission implementation works with super-perms
     */
    abstract public boolean hasSuperPermsCompat();
    
    /**
     * Checks if player has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to check
     * @param permission Permission node
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean has(final String world, final String player, final String permission) {
        if (world == null) {
            return playerHas((String) null, player, permission);
        }
        return playerHas(world, player, permission);
    }

    /**
     * Checks if player has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to check
     * @param permission Permission node
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean has(final World world, final String player, final String permission) {
        if (world == null) {
            return playerHas((String) null, player, permission);
        }
        return playerHas(world.getName(), player, permission);
    }

    /**
     * Checks if a CommandSender has a permission node.
     * This will return the result of bukkits, generic .hasPermission() method and is identical in all cases.
     * This method will explicitly fail if the registered permission system does not register permissions in bukkit.
     * 
     * For easy checking of a commandsender
     * @param sender to check permissions on
     * @param permission to check for
     * @return true if the sender has the permission
     */
    public boolean has(final CommandSender sender, final String permission) {
        return sender.hasPermission(permission);
    }

    /**
     * Checks if player has a permission node. (Short for playerHas(...)
     * @param player Player Object
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean has(final Player player, final String permission) {
        return player.hasPermission(permission);
    }

    /**
     * Checks if player has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to check
     * @param permission Permission node
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    abstract public boolean playerHas(String world, String player, String permission);

    /**
     * Checks if player has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to check
     * @param permission Permission node
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean playerHas(final World world, final String player, final String permission) {
        if (world == null) {
            return playerHas((String) null, player, permission);
        }
        return playerHas(world.getName(), player, permission);
    }
    
    /**
     * Checks if player has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world String world name
     * @param player to check
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerHas(final String world, final OfflinePlayer player, final String permission) {
    	if (world == null) {
    		return has((String) null, player.getName(), permission);
    	}
        return has(world, player.getName(), permission);
    }

    /**
     * Checks if player has a permission node.
     * Defaults to world-specific permission check if the permission system supports it.
     * See {@link #playerHas(String, OfflinePlayer, String)} for explicit global or world checks.
     *  
     * @param player Player Object
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerHas(final Player player, final String permission) {
        return has(player, permission);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerAdd(String, OfflinePlayer, String)} instead.
     * Add permission to a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World name
     * @param player Player name
     * @param permission Permission node
     * @return Success or Failure
     */
    @Deprecated
    abstract public boolean playerAdd(String world, String player, String permission);

    /**
     * Add permission to a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to add to
     * @param permission Permission node
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerAdd(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean playerAdd(final World world, final String player, final String permission) {
        if (world == null) {
            return playerAdd((String) null, player, permission);
        }
        return playerAdd(world.getName(), player, permission);
    }

    /**
     * Add permission to a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world String world name
     * @param player to add to
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerAdd(final String world, final OfflinePlayer player, final String permission) {
        if (world == null) {
            return playerAdd((String) null, player.getName(), permission);
        }
        return playerAdd(world, player.getName(), permission);
    }

    /**
     * Add permission to a player ONLY for the world the player is currently on.
     * This is a world-specific operation, if you want to add global permission you must explicitly use NULL for the world.
     * See {@link #playerAdd(String, OfflinePlayer, String)} for global permission use.
     * 
     * @param player Player Object
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerAdd(final Player player, final String permission) {
        return playerAdd(player.getWorld().getName(), player, permission);
    }
 
    /**
     * Add transient permission to a player.
     * This implementation can be used by any subclass which implements a "pure" superperms plugin, i.e. 
     * one that only needs the built-in Bukkit API to add transient permissions to a player.
     * 
     * @param player to add to
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerAddTransient(final OfflinePlayer player, final String permission) throws UnsupportedOperationException {
    	if (player.isOnline()) {
    		return playerAddTransient((Player) player, permission);
    	}
		throw new UnsupportedOperationException(getName() + " does not support offline player transient permissions!");
	}

    /**
     * Add transient permission to a player.
     * This operation adds a permission onto the player object in bukkit via Bukkit's permission interface.
     * 
     * @param player Player Object
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerAddTransient(final Player player, final String permission) {
		for (final PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
			if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(plugin)) {
				paInfo.getAttachment().setPermission(permission, true);
				return true;
			}
		}

		final PermissionAttachment attach = player.addAttachment(plugin);
		attach.setPermission(permission, true);

		return true;
    }

    /**
     * Adds a world specific transient permission to the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 
     * @param worldName to check on
     * @param player to add to
     * @param permission to test
     * @return Success or Failure
     */
    public boolean playerAddTransient(final String worldName, final OfflinePlayer player, final String permission) {
    	return playerAddTransient(player, permission);
    }
    
    /**
     * Adds a world specific transient permission to the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 
     * @param worldName to check on
     * @param player to check
     * @param permission to check for
     * @return Success or Failure
     */
    public boolean playerAddTransient(final String worldName, final Player player, final String permission) {
    	return playerAddTransient(player, permission);
    }
	
    /**
     * Removes a world specific transient permission from the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 
     * @param worldName to remove for
     * @param player to remove for
     * @param permission to remove
     * @return Success or Failure
     */
    public boolean playerRemoveTransient(final String worldName, final OfflinePlayer player, final String permission) {
    	return playerRemoveTransient(player, permission);
    }
	
    /**
     * Removes a world specific transient permission from the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 
     * @param worldName to check on
     * @param player to check
     * @param permission to check for
     * @return Success or Failure
     */
    public boolean playerRemoveTransient(final String worldName, final Player player, final String permission) {
    	return playerRemoveTransient((OfflinePlayer) player, permission);
    }
    
    /**
     * Remove permission from a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world World name
     * @param player OfflinePlayer
     * @param permission Permission node
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemove(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    abstract public boolean playerRemove(String world, String player, String permission);

    /**
     * Remove permission from a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World name
     * @param player OfflinePlayer
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerRemove(final String world, final OfflinePlayer player, final String permission) {
        if (world == null) {
            return playerRemove((String) null, player.getName(), permission);
        }
        return playerRemove(world, player.getName(), permission);
    }

    /**
     * Remove permission from a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World name
     * @param player Player name
     * @param permission Permission node
     * @return Success or Failure
     */
    @Deprecated
    public boolean playerRemove(final World world, final String player, final String permission) {
        if (world == null) {
            return playerRemove((String) null, player, permission);
        }
        return playerRemove(world.getName(), player, permission);
    }

    /**
     * Remove permission from a player.
     * Will attempt to remove permission from the player on the player's current world.  This is NOT a global operation.
     * 
     * @param player Player Object
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerRemove(final Player player, final String permission) {
        return playerRemove(player.getWorld().getName(), player, permission);
    }
   

    /**
     * Remove transient permission from a player.
     * This implementation can be used by any subclass which implements a "pure" superperms plugin, i.e. 
     * one that only needs the built-in Bukkit API to remove transient permissions from a player.  Any subclass
     * implementing a plugin which provides its own API for this needs to override this method.
     * 
     * @param player OfflinePlayer
     * @param permission Permission node
     * @return Success or Failure
     */
	public boolean playerRemoveTransient(final OfflinePlayer player, final String permission) {
		if (player.isOnline()) {
			return playerRemoveTransient((Player) player, permission);
		} else {
			return false;
		}
	}
    
    /**
     * Remove transient permission from a player.
     * 
     * @param player Player Object
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean playerRemoveTransient(final Player player, final String permission) {
		for (final PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
			if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(plugin)) {
				paInfo.getAttachment().unsetPermission(permission);
				return true;
			}
		}
		return false;
    }
    
    /**
     * Checks if group has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World name
     * @param group Group name
     * @param permission Permission node
     * @return Success or Failure
     */
    abstract public boolean groupHas(String world, String group, String permission);

    /**
     * Checks if group has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World Object
     * @param group Group name
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean groupHas(final World world, final String group, final String permission) {
        if (world == null) {
            return groupHas((String) null, group, permission);
        }
        return groupHas(world.getName(), group, permission);
    }

    /**
     * Add permission to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World name
     * @param group Group name
     * @param permission Permission node
     * @return Success or Failure
     */
    abstract public boolean groupAdd(String world, String group, String permission);

    /**
     * Add permission to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World Object
     * @param group Group name
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean groupAdd(final World world, final String group, final String permission) {
        if (world == null) {
            return groupAdd((String) null, group, permission);
        }
        return groupAdd(world.getName(), group, permission);
    }

    /**
     * Remove permission from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World name
     * @param group Group name
     * @param permission Permission node
     * @return Success or Failure
     */
    abstract public boolean groupRemove(String world, String group, String permission);

    /**
     * Remove permission from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World Object
     * @param group Group name
     * @param permission Permission node
     * @return Success or Failure
     */
    public boolean groupRemove(final World world, final String group, final String permission) {
        if (world == null) {
            return groupRemove((String) null, group, permission);
        }
        return groupRemove(world.getName(), group, permission);
    }

    /**
     * Check if player is member of a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world World Object
     * @param player to check
     * @param group Group name
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerInGroup(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    abstract public boolean playerInGroup(String world, String player, String group);

    /**
     * Check if player is member of a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world World Object
     * @param player to check
     * @param group Group name
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerInGroup(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean playerInGroup(final World world, final String player, final String group) {
        if (world == null) {
            return playerInGroup((String) null, player, group);
        }
        return playerInGroup(world.getName(), player, group);
    }
    
    /**
     * Check if player is member of a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World Object
     * @param player to check
     * @param group Group name
     * @return Success or Failure
     */
    public boolean playerInGroup(final String world, final OfflinePlayer player, final String group) {
        if (world == null) {
            return playerInGroup((String) null, player.getName(), group);
        }
        return playerInGroup(world, player.getName(), group);
    }

    /**
     * Check if player is member of a group.
     * This method will ONLY check groups for which the player is in that are defined for the current world.
     * This may result in odd return behaviour depending on what permission system has been registered.
     * 
     * @param player Player Object
     * @param group Group name
     * @return Success or Failure
     */
    public boolean playerInGroup(final Player player, final String group) {
        return playerInGroup(player.getWorld().getName(), player, group);
    }

    /**
     * Add player to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to add
     * @param group Group name
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerAddGroup(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    abstract public boolean playerAddGroup(String world, String player, String group);

    /**
     * Add player to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to add
     * @param group Group name
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerAddGroup(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean playerAddGroup(final World world, final String player, final String group) {
        if (world == null) {
            return playerAddGroup((String) null, player, group);
        }
        return playerAddGroup(world.getName(), player, group);
    }

    /**
     * Add player to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world String world name
     * @param player to add
     * @param group Group name
     * @return Success or Failure
     */
    public boolean playerAddGroup(final String world, final OfflinePlayer player, final String group) {
        if (world == null) {
            return playerAddGroup((String) null, player.getName(), group);
        }
        return playerAddGroup(world, player.getName(), group);
    }
    
    /**
     * Add player to a group.
     * This will add a player to the group on the current World.  This may return odd results if the permission system
     * being used on the server does not support world-specific groups, or if the group being added to is a global group.
     * 
     * @param player Player Object
     * @param group Group name
     * @return Success or Failure
     */
    public boolean playerAddGroup(final Player player, final String group) {
        return playerAddGroup(player.getWorld().getName(), player, group);
    }

    /**
     * Remove player from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world World Object
     * @param player to remove
     * @param group Group name
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemoveGroup(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    abstract public boolean playerRemoveGroup(String world, String player, String group);

    /**
     * Remove player from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world World Object
     * @param player to remove
     * @param group Group name
     * @return Success or Failure
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemoveGroup(String, OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean playerRemoveGroup(final World world, final String player, final String group) {
        if (world == null) {
            return playerRemoveGroup((String) null, player, group);
        }
        return playerRemoveGroup(world.getName(), player, group);
    }
    
    /**
     * Remove player from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world World Object
     * @param player to remove
     * @param group Group name
     * @return Success or Failure
     */
    public boolean playerRemoveGroup(final String world, final OfflinePlayer player, final String group) {
        if (world == null) {
            return playerRemoveGroup((String) null, player.getName(), group);
        }
        return playerRemoveGroup(world, player.getName(), group);
    }

    /**
     * Remove player from a group.
     * This will add a player to the group on the current World.  This may return odd results if the permission system
     * being used on the server does not support world-specific groups, or if the group being added to is a global group.
     * 
     * @param player Player Object
     * @param group Group name
     * @return Success or Failure
     */
    public boolean playerRemoveGroup(final Player player, final String group) {
        return playerRemoveGroup(player.getWorld().getName(), player, group);
    }

    /**
     * Gets the list of groups that this player has
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player OfflinePlayer
     * @return Array of groups
     * @deprecated As of VaultAPI 1.4 use {@link #getPlayerGroups(String, OfflinePlayer)} instead.
     */
    @Deprecated
    abstract public String[] getPlayerGroups(String world, String player);

    /**
     * Gets the list of groups that this player has
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player OfflinePlayer
     * @return Array of groups
     * @deprecated As of VaultAPI 1.4 use {@link #getPlayerGroups(String, OfflinePlayer)} instead.
     */
    @Deprecated
    public String[] getPlayerGroups(final World world, final String player) {
        if (world == null) {
            return getPlayerGroups((String) null, player);
        }
        return getPlayerGroups(world.getName(), player);
    }
    
    /**
     * Gets the list of groups that this player has
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world String world name
     * @param player OfflinePlayer
     * @return Array of groups
     */
    public String[] getPlayerGroups(final String world, final OfflinePlayer player) {
    	return getPlayerGroups(world, player.getName());
    }

    /**
     * Returns a list of world-specific groups that this player is currently in. May return unexpected results if
     * you are looking for global groups, or if the registered permission system does not support world-specific groups.
     * See {@link #getPlayerGroups(String, OfflinePlayer)} for better control of World-specific or global groups.
     * 
     * @param player Player Object
     * @return Array of groups
     */
    public String[] getPlayerGroups(final Player player) {
        return getPlayerGroups(player.getWorld().getName(), player);
    }

    /**
     * Gets players primary group
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to get from
     * @return Players primary group
     * @deprecated As of VaultAPI 1.4 use {@link #getPrimaryGroup(String, OfflinePlayer)} instead.
     */
    @Deprecated
    abstract public String getPrimaryGroup(String world, String player);

    /**
     * Gets players primary group
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     *
     * @param world String world name
     * @param player to get from
     * @return Players primary group
     * @deprecated As of VaultAPI 1.4 use {@link #getPrimaryGroup(String, OfflinePlayer)} instead.
     */
    @Deprecated
    public String getPrimaryGroup(final World world, final String player) {
        if (world == null) {
            return getPrimaryGroup((String) null, player);
        }
        return getPrimaryGroup(world.getName(), player);
    }
    
    /**
     * Gets players primary group
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 
     * @param world String world name
     * @param player to get from
     * @return Players primary group
     */
    public String getPrimaryGroup(final String world, final OfflinePlayer player) {
        return getPrimaryGroup(world, player.getName());
    }

    /**
     * Get players primary group.
     * Defaults to the players current world, so may return only world-specific groups.
     * In most cases {@link #getPrimaryGroup(String, OfflinePlayer)} is preferable.
     * 
     * @param player Player Object
     * @return Players primary group
     */
    public String getPrimaryGroup(final Player player) {
        return getPrimaryGroup(player.getWorld().getName(), player);
    }
    
    /**
     * Returns a list of all known groups
     * @return an Array of String of all groups
     */
    abstract public String[] getGroups();
    
    /**
     * Returns true if the given implementation supports groups.
     * @return true if the implementation supports groups
     */
    abstract public boolean hasGroupSupport();
}