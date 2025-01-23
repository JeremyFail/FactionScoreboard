package com.ngxdev.utils;

import java.beans.ConstructorProperties;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils
{
	public static void Try(final TryLambda r)
	{
		try
		{
			r.run();
		}
		catch (Throwable throwable)
		{
			throwable.printStackTrace();
		}
	}
	
	public static void Try(final TryLambda _try, final Catchlambda _catch)
	{
		try
		{
			_try.run();
		}
		catch (Throwable throwable)
		{
			_catch.run(throwable);
		}
	}
	
	public static void Try(final TryLambda _try, final Catchlambda _catch, final Runnable _finally)
	{
		try
		{
			_try.run();
		}
		catch (Throwable throwable)
		{
			_catch.run(throwable);
		}
		finally
		{
			_finally.run();
		}
	}
	
	public static void sleep(final long time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (Throwable t)
		{
		}
	}
	
	public static void sleep(final long time, final int nano)
	{
		try
		{
			Thread.sleep(time, nano);
		}
		catch (Throwable t)
		{
		}
	}
	
	public static void endless(final Runnable task, final long sleep)
	{
		try
		{
			while (true)
			{
				Try(task::run, Throwable::printStackTrace);
				sleep(sleep);
			}
		}
		catch (Throwable t)
		{
		}
	}
	
	public static boolean IfError(final TryLambda _try)
	{
		try
		{
			_try.run();
		}
		catch (Throwable throwable)
		{
			return true;
		}
		return false;
	}
	
	public static void TryIf(final TryLambda r, final boolean b)
	{
		if (b)
		{
			try
			{
				r.run();
			}
			catch (Throwable throwable)
			{
				throwable.printStackTrace();
			}
		}
	}
	
	public static void TryIfNull(final Object o, final TryLambda r)
	{
		if (o == null)
		{
			try
			{
				r.run();
			}
			catch (Throwable throwable)
			{
				throwable.printStackTrace();
			}
		}
	}
	
	public static void TryIfNotNull(final Object o, final TryLambda r)
	{
		if (o != null)
		{
			try
			{
				r.run();
			}
			catch (Throwable throwable)
			{
				throwable.printStackTrace();
			}
		}
	}
	
	public static void Switch(final String value, final CaseField... cases)
	{
		for (final CaseField field : cases)
		{
			if (field.trigger.equals(value))
			{
				field.task.run();
				return;
			}
		}
	}
	
	public static boolean contains(final String[] strings, final String string)
	{
		for (final String s : strings)
		{
			if (s.equalsIgnoreCase(string))
			{
				return true;
			}
		}
		return false;
	}
	
	public static <T> T[] trim(final T[] array, final int amount)
	{
		return Arrays.copyOf(array, array.length - amount);
	}
	
	public static void close(final Closeable... closeables) throws Throwable
	{
		try
		{
			for (final Closeable closeable : closeables)
			{
				if (closeable != null)
				{
					closeable.close();
				}
			}
		}
		catch (Throwable $ex)
		{
			throw $ex;
		}
	}
	
	public static void close(final AutoCloseable... closeables) throws Throwable
	{
		try
		{
			for (final AutoCloseable closeable : closeables)
			{
				if (closeable != null)
				{
					closeable.close();
				}
			}
		}
		catch (Throwable $ex)
		{
			throw $ex;
		}
	}
	
	public static String compile(final Object[] objs)
	{
		return compile(objs, " ");
	}
	
	public static String compile(final Object[] objs, final String seperator)
	{
		final StringBuilder builder = new StringBuilder();
		for (final Object s : objs)
		{
			if (builder.length() != 0)
			{
				builder.append(seperator);
			}
			builder.append(s.toString());
		}
		return builder.toString();
	}
	
	public static String compile(final int[] ints, final String seperator)
	{
		final StringBuilder builder = new StringBuilder();
		for (final Object s : ints)
		{
			if (builder.length() != 0)
			{
				builder.append(seperator);
			}
			builder.append(s.toString());
		}
		return builder.toString();
	}
	
	public static String compile(final long[] longs, final String seperator)
	{
		final StringBuilder builder = new StringBuilder();
		for (final Object s : longs)
		{
			if (builder.length() != 0)
			{
				builder.append(seperator);
			}
			builder.append(s.toString());
		}
		return builder.toString();
	}
	
	public static String compile(final Object[] objs, final String seperator, final int start)
	{
		final StringBuilder builder = new StringBuilder();
		for (int i = start; i < objs.length; ++i)
		{
			final Object s = objs[i];
			if (builder.length() != 0)
			{
				builder.append(seperator);
			}
			builder.append(s.toString());
		}
		return builder.toString();
	}
	
	public static String getTime()
	{
		return getTime(-1L);
	}
	
	public static String getTime(final long date)
	{
		final Date d = (date == -1L) ? new Date() : new Date(date);
		final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(d);
	}
	
	public static UUID toUUID(final String uuid)
	{
		if (uuid.contains("-"))
		{
			return UUID.fromString(uuid);
		}
		return UUID.fromString(uuid.replaceFirst(
				"([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
	}
	
	public static int getInt(final String string) throws IllegalArgumentException
	{
		try
		{
			return Integer.parseInt(string);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("Failed to parse " + string + " as an integer");
		}
	}
	
	public static void reverse(final Queue q)
	{
		final Stack s = new Stack();
		while (!q.isEmpty())
		{
			s.push(q.poll());
		}
		while (!s.isEmpty())
		{
			q.offer(s.pop());
		}
	}
	
	public static <T> T[] shift(final T[] array, final T t)
	{
		System.arraycopy(array, 1, array, 0, array.length - 1);
		array[array.length - 1] = t;
		return array;
	}
	
	public static <T> List<T> toList(final Iterator<T> iterator)
	{
		final List<T> list = new ArrayList<T>();
		while (iterator.hasNext())
		{
			list.add(iterator.next());
			iterator.remove();
		}
		return list;
	}
	
	public static void createFile(final File file) throws IOException
	{
		if (!file.exists())
		{
			if (file.getParentFile() != null)
			{
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
	}
	
	public static void writeToFile(final File file, final String string) throws IOException
	{
		final String fixed = string.replaceAll("\r\n|\n|\r", System.lineSeparator());
		final PrintWriter writer = new PrintWriter(file);
		writer.write(fixed);
		writer.close();
	}
	
	public static void extractFirst(final File file, final File to) throws Exception
	{
		final ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file));
		final ZipEntry entry = zipIn.getNextEntry();
		if (entry != null)
		{
			extractFile(zipIn, to.getPath());
			zipIn.closeEntry();
		}
		zipIn.close();
	}
	
	public static void extractFile(final ZipInputStream zipIn, final String filePath) throws IOException
	{
		final File file = new File(filePath);
		createFile(file);
		final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		final byte[] bytesIn = new byte[4096];
		int read;
		while ((read = zipIn.read(bytesIn)) != -1)
		{
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
	
	public static class CaseField
	{
		String trigger;
		Runnable task;
		
		@ConstructorProperties({ "trigger", "task" })
		public CaseField(final String trigger, final Runnable task)
		{
			this.trigger = trigger;
			this.task = task;
		}
	}
	
	public interface Catchlambda
	{
		void run(final Throwable p0);
	}
	
	public interface TryLambda
	{
		void run() throws Exception;
	}
}
