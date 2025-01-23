package com.ngxdev.utils;

import org.bukkit.Bukkit;

public class Log
{
	public static void info(final String... messages)
	{
		if (messages.length == 1)
		{
			Bukkit.getLogger().info(messages[0]);
		}
		else
		{
			Bukkit.getLogger().info(Utils.compile(messages));
		}
	}
	
	public static void warn(final String... messages)
	{
		if (messages.length == 1)
		{
			Bukkit.getLogger().warning(messages[0]);
		}
		else
		{
			Bukkit.getLogger().warning(Utils.compile(messages));
		}
	}
	
	public static void severe(final String... messages)
	{
		if (messages.length == 1)
		{
			Bukkit.getLogger().severe(messages[0]);
		}
		else
		{
			Bukkit.getLogger().severe(Utils.compile(messages));
		}
	}
}
