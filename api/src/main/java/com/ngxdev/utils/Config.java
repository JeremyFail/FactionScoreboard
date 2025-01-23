package com.ngxdev.utils;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config
{
	YamlConfiguration yaml;
	public File file;
	boolean cache;
	
	private static File getConfigFile(final String dir, final File loc)
	{
		if (dir == null)
		{
			return null;
		}
		File file;
		if (dir.contains("/"))
		{
			if (dir.startsWith("/"))
			{
				file = new File(loc + dir.replace("/", File.separator));
			}
			else
			{
				file = new File(loc + File.separator + dir.replace("/", File.separator));
			}
		}
		else
		{
			file = new File(loc, dir);
		}
		return file;
	}
	
	private static File parseFile(final String filePath, final InputStream resource, final File loc)
	{
		final File file = getConfigFile(filePath, loc);
		if (!file.exists())
		{
			try
			{
				file.getParentFile().mkdirs();
				file.createNewFile();
				if (resource != null)
				{
					try
					{
						final OutputStream out = new FileOutputStream(file);
						final byte[] buf = new byte[1024];
						int len;
						while ((len = resource.read(buf)) > 0)
						{
							out.write(buf, 0, len);
						}
						out.close();
						resource.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (IOException e2)
			{
				e2.printStackTrace();
			}
		}
		return file;
	}
	
	public Config(final String name, final InputStream resource, final File loc)
	{
		this(parseFile(name, resource, loc));
	}
	
	public Config(final String name, final InputStream resource, final JavaPlugin pl)
	{
		this(parseFile(name, resource, pl.getDataFolder()));
	}
	
	public Config(final String name, final File loc)
	{
		this(name, null, loc);
	}
	
	public Config(final String name, final JavaPlugin pl)
	{
		this(name, null, pl.getDataFolder());
	}
	
	public Config(final File file)
	{
		this.cache = false;
		this.yaml = new YamlConfiguration();
		this.file = file;
		this.reload();
	}
	
	public Config()
	{
		this.cache = false;
	}
	
	public void reload()
	{
		try
		{
			this.yaml.load(this.file);
		}
		catch (IOException | InvalidConfigurationException e)
		{
			e.printStackTrace();
		}
	}
	
	public void save()
	{
		try
		{
			if (!this.cache)
			{
				final BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
				writer.write(this.yaml.saveToString());
				writer.flush();
				writer.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void set(final String path, final Object value)
	{
		this.yaml.set(path, value);
		this.save();
	}
	
	public void set(final String path, final Object value, final boolean save)
	{
		this.yaml.set(path, value);
		if (save)
		{
			this.save();
		}
	}
	
	public void setDefault(final String path, final Object value)
	{
		if (this.get(path) == null)
		{
			this.set(path, value);
		}
		this.save();
	}
	
	public void setDefault(final String path, final Object value, final boolean save)
	{
		if (this.get(path) == null)
		{
			this.set(path, value, save);
		}
	}
	
	public void removeKey(final String path)
	{
		this.set(path, null, true);
		this.save();
	}
	
	public void addDefault(final String path, final Object value)
	{
		this.yaml.addDefault(path, value);
	}
	
	public void addDefaults(final Map<String, Object> defaults)
	{
		this.yaml.addDefaults((Map<String, Object>) defaults);
	}
	
	public void addDefaults(final Configuration defaults)
	{
		this.yaml.addDefaults(defaults);
	}
	
	public void setDefaults(final Configuration defaults)
	{
		this.yaml.setDefaults(defaults);
	}
	
	public Configuration getDefaults()
	{
		return this.yaml.getDefaults();
	}
	
	public ConfigurationSection getParent()
	{
		return this.yaml.getParent();
	}
	
	public Set<String> getKeys(final boolean deep)
	{
		return (Set<String>) this.yaml.getKeys(deep);
	}
	
	public Map<String, Object> getValues(final boolean deep)
	{
		return (Map<String, Object>) this.yaml.getValues(deep);
	}
	
	public boolean contains(final String path)
	{
		return this.yaml.contains(path);
	}
	
	public boolean isSet(final String path)
	{
		return this.yaml.isSet(path);
	}
	
	public String getCurrentPath()
	{
		return this.yaml.getCurrentPath();
	}
	
	public String getName()
	{
		return this.yaml.getName();
	}
	
	public Configuration getRoot()
	{
		return this.yaml.getRoot();
	}
	
	public ConfigurationSection getDefaultSection()
	{
		return this.yaml.getDefaultSection();
	}
	
	public Object get(final String path)
	{
		return this.yaml.get(path);
	}
	
	public Object get(final String path, final Object def)
	{
		return this.yaml.get(path, def);
	}
	
	public ConfigurationSection createSection(final String path)
	{
		return this.yaml.createSection(path);
	}
	
	public ConfigurationSection createSection(final String path, final Map<?, ?> map)
	{
		return this.yaml.createSection(path, (Map<?, ?>) map);
	}
	
	public String getString(final String path)
	{
		return this.yaml.getString(path);
	}
	
	public String getString(final String path, final String def)
	{
		return this.yaml.getString(path, def);
	}
	
	public boolean isString(final String path)
	{
		return this.yaml.isString(path);
	}
	
	public int getInt(final String path)
	{
		return this.yaml.getInt(path);
	}
	
	public int getInt(final String path, final int def)
	{
		return this.yaml.getInt(path, def);
	}
	
	public boolean isInt(final String path)
	{
		return this.yaml.isInt(path);
	}
	
	public boolean getBoolean(final String path)
	{
		return this.yaml.getBoolean(path);
	}
	
	public boolean getBoolean(final String path, final boolean def)
	{
		return this.yaml.getBoolean(path, def);
	}
	
	public boolean isBoolean(final String path)
	{
		return this.yaml.isBoolean(path);
	}
	
	public double getDouble(final String path)
	{
		return this.yaml.getDouble(path);
	}
	
	public double getDouble(final String path, final double def)
	{
		return this.yaml.getDouble(path, def);
	}
	
	public boolean isDouble(final String path)
	{
		return this.yaml.isDouble(path);
	}
	
	public long getLong(final String path)
	{
		return this.yaml.getLong(path);
	}
	
	public long getLong(final String path, final long def)
	{
		return this.yaml.getLong(path, def);
	}
	
	public boolean isLong(final String path)
	{
		return this.yaml.isLong(path);
	}
	
	public List<?> getList(final String path)
	{
		return (List<?>) this.yaml.getList(path);
	}
	
	public List<?> getList(final String path, final List<?> def)
	{
		return (List<?>) this.yaml.getList(path, (List<?>) def);
	}
	
	public boolean isList(final String path)
	{
		return this.yaml.isList(path);
	}
	
	public List<String> getStringList(final String path)
	{
		return (List<String>) this.yaml.getStringList(path);
	}
	
	public List<Integer> getIntegerList(final String path)
	{
		return (List<Integer>) this.yaml.getIntegerList(path);
	}
	
	public List<Boolean> getBooleanList(final String path)
	{
		return (List<Boolean>) this.yaml.getBooleanList(path);
	}
	
	public List<Double> getDoubleList(final String path)
	{
		return (List<Double>) this.yaml.getDoubleList(path);
	}
	
	public List<Float> getFloatList(final String path)
	{
		return (List<Float>) this.yaml.getFloatList(path);
	}
	
	public List<Long> getLongList(final String path)
	{
		return (List<Long>) this.yaml.getLongList(path);
	}
	
	public List<Byte> getByteList(final String path)
	{
		return (List<Byte>) this.yaml.getByteList(path);
	}
	
	public List<Character> getCharacterList(final String path)
	{
		return (List<Character>) this.yaml.getCharacterList(path);
	}
	
	public List<Short> getShortList(final String path)
	{
		return (List<Short>) this.yaml.getShortList(path);
	}
	
	public List<Map<?, ?>> getMapList(final String path)
	{
		return (List<Map<?, ?>>) this.yaml.getMapList(path);
	}
	
	public Vector getVector(final String path)
	{
		return this.yaml.getVector(path);
	}
	
	public Vector getVector(final String path, final Vector def)
	{
		return this.yaml.getVector(path, def);
	}
	
	public boolean isVector(final String path)
	{
		return this.yaml.isVector(path);
	}
	
	public OfflinePlayer getOfflinePlayer(final String path)
	{
		return this.yaml.getOfflinePlayer(path);
	}
	
	public OfflinePlayer getOfflinePlayer(final String path, final OfflinePlayer def)
	{
		return this.yaml.getOfflinePlayer(path, def);
	}
	
	public boolean isOfflinePlayer(final String path)
	{
		return this.yaml.isOfflinePlayer(path);
	}
	
	public ItemStack getItemStack(final String path)
	{
		return this.yaml.getItemStack(path);
	}
	
	public ItemStack getItemStack(final String path, final ItemStack def)
	{
		return this.yaml.getItemStack(path, def);
	}
	
	public boolean isItemStack(final String path)
	{
		return this.yaml.isItemStack(path);
	}
	
	public Color getColor(final String path)
	{
		return this.yaml.getColor(path);
	}
	
	public Color getColor(final String path, final Color def)
	{
		return this.yaml.getColor(path, def);
	}
	
	public boolean isColor(final String path)
	{
		return this.yaml.isColor(path);
	}
	
	public ConfigurationSection getConfigurationSection(final String path)
	{
		return this.yaml.getConfigurationSection(path);
	}
	
	public boolean isConfigurationSection(final String path)
	{
		return this.yaml.isConfigurationSection(path);
	}
	
	public static String createPath(final ConfigurationSection section, final String key)
	{
		return MemorySection.createPath(section, key);
	}
	
	public static String createPath(final ConfigurationSection section, final String key,
			final ConfigurationSection relativeTo)
	{
		return MemorySection.createPath(section, key, relativeTo);
	}
	
	@Override
	public String toString()
	{
		return this.yaml.toString();
	}
	
	public void setCache(final boolean cache)
	{
		this.cache = cache;
	}
}
