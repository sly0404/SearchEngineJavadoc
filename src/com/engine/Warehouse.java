package com.engine;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Warehouse
{
	private Map<String, List<String>> index;
	private static final String context = "https://docs.oracle.com/javase/8/docs/api/java/awt/event/";
	private Document document;
	private static final String attribute = "href";

	public Warehouse()
	{
		index  = new HashMap<String, List<String>>();
	}
	
	public void insert(String key, String value)
	{
		List<String> elementsList = index.get(key);
		if (elementsList == null)
		{
			elementsList = new ArrayList<String>();
			elementsList.add(value);
			index.put(key, elementsList);
		}
		else
		{
			if (!elementsList.contains(value))
			{
				elementsList.add(value);
			}
		}
	}
	
	public void show()
	{
		index.forEach((k, v) -> System.out.println(k + "  " + v));
	}
}
