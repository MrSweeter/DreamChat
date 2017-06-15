package com.mrsweeter.dreamchat;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Commands.Commands;
import Listeners.ChatEvent;
import Listeners.JoinQuit;

public class DreamChat extends JavaPlugin	{
	
	public static Logger log = Logger.getLogger("Minecraft");
	static PluginManager pm = Bukkit.getPluginManager();
	public static Map<String, String> messages = new HashMap<String, String>();
	public static Map<String, String> playerChannel = new HashMap<String, String>();
	public static PluginConfiguration config;
	
	public void onEnable()	{
		
		config = new PluginConfiguration(this, "configuration.yml", "configuration.yml", null);
		
		Language.loadLanguage(config);
		
		getCommand("dchat").setExecutor(new Commands(this));
		getCommand("dchatreload").setExecutor(new Commands(this));
		getCommand("dclist").setExecutor(new Commands(this));
		getCommand("dclock").setExecutor(new Commands(this));
		getCommand("dchelp").setExecutor(new Commands(this));
		getCommand("dcadd").setExecutor(new Commands(this));
		getCommand("dcremove").setExecutor(new Commands(this));
		
		pm.registerEvents(new JoinQuit(this), this);
		pm.registerEvents(new ChatEvent(this), this);
		
		log.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamChat enable" + Color.GREEN + " ===============" + Color.RESET);
		
	}
	
	public void onDisable()	{
		
		log.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamChat disable" + Color.GREEN + " ===============" + Color.RESET);
		
	}
}
