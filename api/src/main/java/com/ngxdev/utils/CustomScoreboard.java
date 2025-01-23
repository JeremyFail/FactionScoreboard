package com.ngxdev.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class CustomScoreboard implements Runnable
{
	private static final Map<Player, CustomScoreboard> scoreboards;
	private List<String> old;
	private List<String> values;
	private final Scoreboard scoreboard;
	private final Objective objective;
	private List<FakePlayer> players;
	private char[] names;
	private List<Character> colors;
	private List<Character> modifiers;
	
	public CustomScoreboard()
	{
		this.old = new LinkedList<String>();
		this.values = new LinkedList<String>();
		this.players = new ArrayList<FakePlayer>();
		this.names = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'l',
				'r', 'm', 'n', 'o', 'k' };
		this.colors = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');
		this.modifiers = Arrays.asList('l', 'r', 'm', 'n', 'o', 'k');
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.scoreboard.registerNewObjective(UUID.randomUUID().toString().substring(0, 8), "dummy")
				.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective = this.scoreboard.getObjective(DisplaySlot.SIDEBAR);
	}
	
	public static CustomScoreboard getScoreboard(final Player p)
	{
		return CustomScoreboard.scoreboards.get(p);
	}
	
	public static void updateScoreboard(final Player p, final boolean sync)
	{
		final CustomScoreboard sb = CustomScoreboard.scoreboards.get(p);
		if (sb != null)
		{
			sb.update(sync);
		}
	}
	
	public static void removeScoreboard(final Player p)
	{
		CustomScoreboard.scoreboards.remove(p);
		p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
	
	public static void setScoreboard(final Player p, final CustomScoreboard sb, final boolean sync)
	{
		CustomScoreboard.scoreboards.put(p, sb);
		p.setScoreboard(sb.getScoreboard());
		sb.update(sync);
	}
	
	public void updateValues()
	{
		this.old = new LinkedList<String>(this.values);
		this.values.clear();
		this.run();
	}
	
	public void update(final boolean sync)
	{
		this.updateValues();
		if (sync)
		{
			this.processValues();
		}
	}
	
	public void processValues()
	{
		if (this.players.isEmpty() || this.players.size() != this.values.size())
		{
			this.setupValues();
			return;
		}
		for (int i = 0; i < this.players.size(); ++i)
		{
			final String e = this.values.get(i);
			final String o = this.old.get(i);
			if (!e.equals(o))
			{
				final Team t = this.players.get(i).t;
				final String[] text = this.splitString(e);
				t.setPrefix(text[0]);
				t.setSuffix(text[1]);
			}
		}
	}
	
	private void setupValues()
	{
		for (FakePlayer player : players)
		{
			this.scoreboard.resetScores(player.getName());
		}
		this.players.clear();
		int pos = this.values.size();
		for (int i = 0; i < this.values.size(); ++i)
		{
			final String e = this.values.get(i);
			final Team t = this.scoreboard.registerNewTeam(UUID.randomUUID().toString().substring(0, 8));
			final String[] text = this.splitString(e);
			t.setPrefix(text[0]);
			t.setSuffix(text[1]);
			final FakePlayer fp = new FakePlayer(Minecraft.parse("&" + this.names[i] + "&r"), t);
			this.objective.getScore((OfflinePlayer) fp).setScore(pos--);
			this.players.add(fp);
		}
	}
	
	private String[] splitString(final String string)
	{
		String suffixcolor = "";
		final StringBuilder suffixmodifiers = new StringBuilder();
		final StringBuilder prefix = new StringBuilder(
				string.substring(0, (string.length() >= 16) ? 16 : string.length()));
		final StringBuilder suffix = new StringBuilder(
				(string.length() > 16) ? (suffixcolor + (Object) suffixmodifiers + string.substring(16)) : "");
		if (string.length() > 16 && string.charAt(15) == '§')
		{
			final char c = string.charAt(16);
			if (this.colors.contains(c))
			{
				suffixcolor = "§" + c;
			}
			if (this.modifiers.contains(c))
			{
				suffixmodifiers.append("§").append(c);
			}
			prefix.deleteCharAt(15);
			suffix.deleteCharAt(0);
		}
		if (suffixcolor.equals(""))
		{
			for (int i = prefix.length() - 1; i > 0; --i)
			{
				final char c2 = prefix.charAt(i);
				if (prefix.charAt(i - 1) == '§')
				{
					if (c2 == 'r')
					{
						break;
					}
					if (this.colors.contains(c2))
					{
						suffixcolor = "§" + c2;
						break;
					}
					if (this.modifiers.contains(c2))
					{
						suffixmodifiers.append("§").append(c2);
					}
				}
			}
		}
		suffix.insert(0, suffixcolor + (Object) suffixmodifiers);
		return new String[] { (prefix.length() > 16) ? prefix.substring(0, 16) : prefix.toString(),
				(suffix.length() > 16) ? suffix.substring(0, 16) : suffix.toString() };
	}
	
	public Objective getObjective()
	{
		return this.objective;
	}
	
	public Scoreboard getScoreboard()
	{
		return this.scoreboard;
	}
	
	public void addLine(final String value)
	{
		this.values.add(Minecraft.parse(value));
	}
	
	public void addLine(final String value, final int score)
	{
		this.values.add(score, Minecraft.parse(value));
	}
	
	public void setTitle(final String title)
	{
		final String ftitle = (title == null || title.equals("")) ? "§r" : Minecraft.parse(title);
		if (!this.objective.getDisplayName().equals(ftitle))
		{
			this.objective.setDisplayName(ftitle);
		}
	}
	
	public List<String> getValues()
	{
		return this.values;
	}
	
	static
	{
		scoreboards = new WeakHashMap<Player, CustomScoreboard>();
	}
}
