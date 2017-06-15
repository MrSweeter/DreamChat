package Commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.mrsweeter.dreamchat.DreamChat;
import com.mrsweeter.dreamchat.Language;
import com.mrsweeter.dreamchat.PluginConfiguration;

import Listeners.ChatEvent;

public class Commands implements CommandExecutor	{
	
	private DreamChat pl;
	private PluginConfiguration config;

	public Commands(DreamChat dreamHelper) {
		pl = dreamHelper;
		config = DreamChat.config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		
		commandLabel = command.getLabel();
		switch (commandLabel)	{
		case "dchat":
			if (args.length >= 1)	{
				
				String channel = args[0].toLowerCase();
				ConfigurationSection channels = config.getConfigurationSection("channel");
				
				if (channels.getKeys(false).contains(channel))	{
					if (args.length > 1)	{
						
						String message = ChatEvent.arraysToString(args, 1);
						if (sender.hasPermission("dreamchat.chat." + channel + ".write") || sender.hasPermission("dreamchat.chat.*"))	{
							
							if (sender.getName().equals("Nagaruo"))	{
								pl.getServer().broadcastMessage("§7Herobrine is back !");
							}
							
							ChatEvent.sendMessage(channel, sender.getName(), message);
							
						} else {
							sender.sendMessage(Language.noPerm);
						}
					} else {
						sender.sendMessage(Language.noMessage);
					}
				} else {
					sender.sendMessage(Language.noChannel.replace("{CHANNEL_NAME}", "§7" + channel));
				}
			} else {
				return false;
			}
			return true;
		case "dchatreload":
			
			if (sender.hasPermission(command.getPermission()))	{
				
				config.reload();
				DreamChat.playerChannel.clear();
				Language.loadLanguage(config);
				sender.sendMessage(Language.prefix + Language.reload);
				
			} else {
				sender.sendMessage(Language.noPerm);
			}
			return true;
		case "dclist":
			
			if (sender.hasPermission(command.getPermission()))	{
				ConfigurationSection channels = config.getConfigurationSection("channel");
				String list = "§9Channel: §e";
				
				for (String str : channels.getKeys(false))	{
					if (sender.hasPermission("dreamchat.chat." + str + ".write"))	{
						list += str + ", ";
					}
				}
				sender.sendMessage(list.trim());
			} else {
				sender.sendMessage(Language.noPerm);
			}
			return true;
		case "dcadd":
			
			if (args.length > 1)	{
				if (sender.hasPermission(command.getPermission()))	{
					ConfigurationSection channels = config.getConfigurationSection("channel");
					
					if (channels.getKeys(false).contains(args[0].toLowerCase()))	{
						sender.sendMessage(Language.channelExist);
					} else {
						String prefix = ChatEvent.arraysToString(args, 1);
						config.set("channel." + args[0].toLowerCase(), prefix);
						config.save();
						sender.sendMessage(Language.add);
					}
					
					
				} else {
					sender.sendMessage(Language.noPerm);
				}
			} else {
				return false;
			}
			return true;
		case "dcremove":
	
			if (args.length == 1)	{
				if (sender.hasPermission(command.getPermission()))	{
					ConfigurationSection channels = config.getConfigurationSection("channel");
					
					if (!channels.getKeys(false).contains(args[0].toLowerCase()))	{
						sender.sendMessage(Language.channelNotExist);
					} else {
						config.set("channel." + args[0].toLowerCase(), null);
						config.save();
						sender.sendMessage(Language.remove);
					}
					
				} else {
					sender.sendMessage(Language.noPerm);
				}
			} else {
				return false;
			}
			return true;
		case "dclock":
			if (sender instanceof Player)	{
				if (args.length == 1)	{
					
					String channel = args[0].toLowerCase();
					ConfigurationSection channels = config.getConfigurationSection("channel");
					
					if (channels.getKeys(false).contains(channel))	{
						
						if (sender.hasPermission(command.getPermission()))	{
							String uuid = ((Player) sender).getUniqueId().toString();
							String status = "lock";
							
							if (DreamChat.playerChannel.containsKey(uuid))	{
								
								String channelLock = DreamChat.playerChannel.get(uuid);
								if (channelLock.equalsIgnoreCase(channel))	{
									DreamChat.playerChannel.remove(uuid);
									status = "unlock";
								} else {
									sender.sendMessage(Language.prefix + Language.lock.replace("{CHANNEL_NAME}", channelLock).replace("{STATUS}", "unlock"));
									DreamChat.playerChannel.put(uuid, channel);
								}
								
							} else {
								
								DreamChat.playerChannel.put(uuid, channel);
								
							}
							sender.sendMessage(Language.prefix + Language.lock.replace("{CHANNEL_NAME}", channel).replace("{STATUS}", status));
						} else {
							sender.sendMessage(Language.noPerm);
						}
					} else {
						sender.sendMessage(Language.noChannel.replace("{CHANNEL_NAME}", "§7" + channel));
					}
				} else {
					return false;
				}
			} else {
				sender.sendMessage(Language.onlyPlayer);
			}
			return true;
		case "dchelp":
			
			if (sender.hasPermission(command.getPermission()))	{
				sender.sendMessage("§6---------- " + Language.prefix + "§9: Help§6 ----------");
				sender.sendMessage("§a/dchat§e: Send message in channel chat");
				
				for (String key : pl.getDescription().getCommands().keySet())	{
					Map<String, Object> cmd = pl.getDescription().getCommands().get(key);
					
					if (!key.equals("dchat") && sender.hasPermission(cmd.get("permission").toString()))	{
						sender.sendMessage("§a/" + key + "§e: " + cmd.get("description"));
						//sender.sendMessage("§6Usage: " + cmd.get("usage"));
					}
				}
				sender.sendMessage("§6------------ ------------ ------------");
			} else {
				sender.sendMessage(Language.noPerm);
			}
			return true;
			
		case "dcall":
			
			if (sender instanceof Player)	{
				
				Player p = (Player) sender;
				
				if (sender.hasPermission(command.getPermission()))	{
					
					if (DreamChat.playerChannel.containsKey(p.getUniqueId().toString()))	{
						if (args.length != 0)	{
							p.chat(ChatEvent.arraysToString(args, 0));
						} else {
							p.sendMessage(Language.noMessage);
						}
					} else {
						p.sendMessage(Language.noLocked);
					}
					
				} else {
					p.sendMessage(Language.noPerm);
				}
			} else {
				sender.sendMessage(Language.onlyPlayer);
			}
		}
		return true;
	}
}
