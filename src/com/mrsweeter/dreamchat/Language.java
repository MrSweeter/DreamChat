package com.mrsweeter.dreamchat;

import org.bukkit.configuration.ConfigurationSection;

public class Language {
	
	public static void loadLanguage(PluginConfiguration config)	{
		
		ConfigurationSection c = config.getConfigurationSection("messages");
		
		noPerm = c.getString("noPerm").replace("&", "§");
		noChannel = c.getString("noChannel").replace("&", "§");
		noMessage = c.getString("noMessage").replace("&", "§");
		onlyPlayer = c.getString("onlyPlayer").replace("&", "§");
		prefix = c.getString("prefix").replace("&", "§");
		reload = c.getString("reload").replace("&", "§");
		ChannelList = c.getString("channel-list").replace("&", "§");
		lock = c.getString("lock-channel").replace("&", "§");
		noLocked = c.getString("no-lock").replace("&", "§");
		channelExist = c.getString("channelExist").replace("&", "§");
		channelNotExist = c.getString("channelNotExist").replace("&", "§");
		add = c.getString("addChannel").replace("&", "§");
		remove = c.getString("removeChannel").replace("&", "§");
		
	}
	
	public static String noPerm = "§cYou aren't allow to do this";
	public static String noChannel = "§cThere is not channel named {CHANNEL_NAME}";
	public static String noMessage = "§cYou didn't write message";
	public static String onlyPlayer = "§cOnly player can execute this command";
	public static String prefix = "§c[§aDreamChat§c] ";
	public static String reload = "§aReload complete";
	public static String ChannelList = "§6Channels availables: ";
	public static String lock = "§6Channel {CHANNEL_NAME} {STATUS}";
	public static String noLocked = "§cNo locked channel";
	public static String channelExist = "§cThis channel already exist";
	public static String channelNotExist = "§cThis channel doesn't exist";
	public static String add = "§aAdd complete";
	public static String remove = "§aRemove complete";
}
