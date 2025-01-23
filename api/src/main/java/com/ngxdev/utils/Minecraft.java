package com.ngxdev.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.bukkit.ChatColor;

public class Minecraft
{
	public static int getPlayercount(final String ip, final int port)
	{
		try
		{
			final Socket sock = new Socket(ip, port);
			final DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			final DataInputStream in = new DataInputStream(sock.getInputStream());
			out.write(254);
			final StringBuilder str = new StringBuilder();
			int b;
			while ((b = in.read()) != -1)
			{
				if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24)
				{
					str.append((char) b);
				}
			}
			sock.close();
			return Integer.parseInt(str.toString().split("§")[1]);
		}
		catch (IOException ex)
		{
			return 0;
		}
	}
	
	public static String parse(final String raw)
	{
		return ChatColor.translateAlternateColorCodes('&', raw);
	}
}
