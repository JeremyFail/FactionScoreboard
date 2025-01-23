package com.ngxdev.utils;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class FakePlayer implements OfflinePlayer
{
	private final String name;
	public Team t;
	private UUID uuid;
	
	public FakePlayer(final String name, final Team t)
	{
		this.uuid = UUID.randomUUID();
		this.name = name;
		(this.t = t).addEntry(this.getName());
	}
	
	public boolean isOnline()
	{
		return true;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public UUID getUniqueId()
	{
		return this.uuid;
	}
	
	public boolean isBanned()
	{
		return false;
	}
	
	public void setBanned(final boolean banned)
	{
	}
	
	public boolean isWhitelisted()
	{
		return false;
	}
	
	public void setWhitelisted(final boolean whitelisted)
	{
	}
	
	public Player getPlayer()
	{
		return null;
	}
	
	public long getFirstPlayed()
	{
		return 0L;
	}
	
	public long getLastPlayed()
	{
		return 0L;
	}
	
	public boolean hasPlayedBefore()
	{
		return false;
	}
	
	public Location getBedSpawnLocation()
	{
		return null;
	}
	
	public Map<String, Object> serialize()
	{
		return null;
	}
	
	public boolean isOp()
	{
		return false;
	}
	
	public void setOp(final boolean op)
	{
	}
	
	@Override
	public String toString()
	{
		return "FakePlayer{name='" + this.name + '\'' + "}";
	}
	
	@Override
	public void decrementStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void decrementStatistic(Statistic arg0, int arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void decrementStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void decrementStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void decrementStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void decrementStatistic(Statistic arg0, EntityType arg1, int arg2)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void incrementStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void incrementStatistic(Statistic arg0, int arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void incrementStatistic(Statistic arg0, Material arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void incrementStatistic(Statistic arg0, EntityType arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void incrementStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void incrementStatistic(Statistic arg0, EntityType arg1, int arg2) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setStatistic(Statistic arg0, int arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setStatistic(Statistic arg0, Material arg1, int arg2) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setStatistic(Statistic arg0, EntityType arg1, int arg2)
	{
		// TODO Auto-generated method stub
		
	}
}
