package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mrsweeter.dreamchat.DreamChat;
import com.mrsweeter.dreamchat.PluginConfiguration;

public class ChatEvent implements Listener	{
	
	static DreamChat pl;
	static PluginConfiguration config;
	
	public ChatEvent(DreamChat main)	{
		pl = main;
		config = DreamChat.config;
	}
	
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent event)	{
		
		if (event.isAsynchronous())	{
			String uuid = event.getPlayer().getUniqueId().toString();
			
			if (DreamChat.playerChannel.containsKey(uuid))	{
				
				String channel = DreamChat.playerChannel.get(uuid);
				sendMessage(channel, event.getPlayer().getName(), event.getMessage());
				event.setCancelled(true);
			}
		}
	}
	
	public static void sendMessage(String channel, String sender, String message)	{
		
		String prefix = config.getString("channel." + channel);
		prefix = prefix.replace("&", "§");
		prefix = prefix.replace("{PLAYER}", sender);
		
		for (Player p : Bukkit.getOnlinePlayers())	{
			
			if (p.hasPermission("dreamchat.chat." + channel + ".read")  || p.hasPermission("dreamchat.chat.*"))	{
				p.sendMessage(prefix + message);
			}
		}	
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
