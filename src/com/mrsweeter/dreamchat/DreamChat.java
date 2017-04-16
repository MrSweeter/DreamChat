package com.mrsweeter.dreamchat;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Commands.Commands;

public class DreamChat extends JavaPlugin	{
	
	public static Logger log = Logger.getLogger("Minecraft");
	static PluginManager pm = Bukkit.getPluginManager();
	public static Map<String, String> messages = new HashMap<String, String>();
	
	public void onEnable()	{
		
		saveDefaultConfig();
		loadMessages();
		
		getCommand("dchat").setExecutor(new Commands(this));
		getCommand("dchatreload").setExecutor(new Commands(this));
		
		log.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamChat enable" + Color.GREEN + " ===============" + Color.RESET);
		
	}
	
	public void onDisable()	{
		
		log.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamChat disable" + Color.GREEN + " ===============" + Color.RESET);
		
	}
	
	private void loadMessages()	{
		
		messages.put("noPerm", this.getConfig().getString("messages.noPerm").replace("&", "§"));
		messages.put("noChannel", this.getConfig().getString("messages.noChannel").replace("&", "§"));
		messages.put("noMessage", this.getConfig().getString("messages.noMessage").replace("&", "§"));
		
	}
}
