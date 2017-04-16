package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.mrsweeter.dreamchat.DreamChat;

public class Commands implements CommandExecutor	{
	
	private DreamChat pl;

	public Commands(DreamChat dreamHelper) {
		pl = dreamHelper;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		
		commandLabel = command.getLabel();
		switch (commandLabel)	{
		case "dchat":
			if (args.length >= 1)	{
				
				String channel = args[0].toLowerCase();
				ConfigurationSection channels = pl.getConfig().getConfigurationSection("channel");
				
				if (channels.getKeys(false).contains(channel))	{
					if (args.length > 1)	{
						
						String message = arraysToString(args, 1);
						if (sender.hasPermission("dreamchat.chat." + channel + "write") || sender.hasPermission(command.getPermission()))	{
							
							String prefix = channels.getString(channel);
							prefix = prefix.replace("&", "§");
							prefix = prefix.replace("{PLAYER}", sender.getName());
							
							for (Player p : Bukkit.getOnlinePlayers())	{
								
								if (p.hasPermission("dreamchat.chat." + channel + "read")  || p.hasPermission(command.getPermission()))	{
									p.sendMessage(prefix + message);
								}
							}
							
						} else {
							sender.sendMessage(DreamChat.messages.get("noPerm"));
						}
					} else {
						sender.sendMessage(DreamChat.messages.get("noMessage"));
					}
				} else {
					sender.sendMessage(DreamChat.messages.get("noChannel"));
				}
			} else {
				return false;
			}
			return true;
		case "dchatreload":
			
			if (sender.hasPermission(command.getPermission()))	{
				
				pl.saveDefaultConfig();
				sender.sendMessage("§c[§aDreamChat§c] §aReload complete");
				
			} else {
				sender.sendMessage(DreamChat.messages.get("noPerm"));
			}
			return true;
		}
		return true;
	}
	
	public static String arraysToString(String[] arrays, int begin){
		String str = "";
		for(int i = begin; i < arrays.length; i++){
			str += arrays[i];
			str += " ";
		}
		return str.trim();
	}
}
