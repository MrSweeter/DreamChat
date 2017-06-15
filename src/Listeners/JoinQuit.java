package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreamchat.DreamChat;
import com.mrsweeter.dreamchat.Language;

public class JoinQuit implements Listener	{
	
	DreamChat pl;
	
	public JoinQuit(DreamChat main)	{
		pl = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)	{
		
		String uuid = event.getPlayer().getUniqueId().toString();
		
		if (DreamChat.playerChannel.containsKey(uuid))	{
			
			String channel = DreamChat.playerChannel.get(uuid);
			
			new BukkitRunnable() {
		        
	            @Override
	            public void run() {
	            	event.getPlayer().sendMessage(Language.prefix + Language.lock.replace("{CHANNEL_NAME}", channel).replace("{STATUS}", "lock"));
	            }
	            
	        }.runTaskLater(pl, 5);
		}	
	}
}
