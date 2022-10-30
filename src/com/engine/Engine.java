package com.engine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Engine
{
	private Warehouse warehouse;
	private static final String context = "https://docs.oracle.com/javase/8/docs/api/java/awt/event/";
	private static final String context_api = "https://docs.oracle.com/javase/8/docs/api/";
	private Document document;
	private static final String attribute = "href";
	//set of words that have low-level semantic sense
	private static final String EMPTY_WORDS = "is,on,your,the,The,a,A,an,An,of,from,to,that,was,were,in,for,but,this,This,if,or,.,:,;";
	private static final List<String> EMPTY_WORDS_FILTER_LIST = Arrays.asList(EMPTY_WORDS.split(","));
	//urls yet performed
	private Set<String> performedURLs;
;
	public Engine(Warehouse warehouse, String baseUrl) throws IOException
	{
		this.warehouse = warehouse;
		performedURLs = new HashSet<String>();
		document = Jsoup.connect(baseUrl).get();
	}
	
	/*
	 * @param level level of seraching depth
	 */
	//level 0 means we don't deep dive into the others links to build the index
	
	public void createIndex(int level) throws IOException
	{
		String url = "https://docs.oracle.com/javase/8/docs/api/java/awt/event/package-frame.html";
		insertIntoWarehouseFromUrl(url, level);
	}
	
	/*
	 * @param url to search
	 * @return list of links in the url
	 */
	public List<Element> getClassLinksListFromUrl(String url) throws IOException
	{
		String attributeTitleValue = "class in ";
		Document document = Jsoup.connect(url).get();
		return document.getElementsByAttribute(attribute).stream().filter(e -> hasAttributeTitleContains(e, attributeTitleValue)).collect(Collectors.toList());
	}
	
	/*
	 * @param url to search
	 * @return string representing the text in the page of the url
	 */
	public String getTextListFromUrl(String url) throws IOException
	{
		String tagName = "div";
		StringBuffer buffer = new StringBuffer("");
		Document document = Jsoup.connect(url).get();
		List<Element> elementsList = document.getElementsByTag(tagName);
		elementsList.stream().forEach(element -> buffer.append(element.text() + " "));
		return buffer.toString();
	}
	
	public List<Element> getClassLinksListFromUrl6(String url) throws IOException
	{
		String attributeTitleValue = "class in ";
		Document document = Jsoup.connect(url).get();
		return document.getElementsByAttribute(attribute).stream().filter(e -> hasAttributeTitleContains(e, attributeTitleValue)).collect(Collectors.toList());
	}
	
	private List<String> filterFromList(String text, List<String> filterList)
	{
		List<String> textAsList = Arrays.asList(text.split(" "));
		List<String> textAsListFiltered = textAsList.stream().filter(s -> !filterList.contains(s)).collect(Collectors.toList());
		return textAsListFiltered;
	}
	
	public List<String> filterFromList2(String text, List<String> filterList)
		{
			return filterFromList( text, filterList);
		}
	
	public List<Element> getClassLinksListFromUrl4(String url) throws IOException
	{
		String attributeTitleValue = "class in ";
		Document document = Jsoup.connect(url).get();
		return document.getElementsByAttribute(attribute).stream().filter(e -> hasAttributeTitleContains(e, attributeTitleValue)).collect(Collectors.toList());
	}
	
	
	
	public void getInformations(String url) throws IOException
	{
		String tagName = "div";
		StringBuffer buffer = new StringBuffer("");
		Document document = Jsoup.connect(url).get();
		List<Element> elementsList = document.getElementsByTag(tagName);
		elementsList.stream().forEach(element -> buffer.append(element.text() + " "));
		//String rslt = filterFromList(buffer.toString(), FILTER_LIST);
		System.out.println(buffer);
		System.out.println();
		//System.out.println(rslt);
		
	}

	/*
	 *  @param url to search
	 * @param level level of seraching depth
	 * 
	 */
	//insert all words in the index at the specified level
	public void insertIntoWarehouseFromUrl(String url, int level) throws IOException
	{
		if (level == 0)
		{
			List<String> dataList = filterFromList(getTextListFromUrl(url), EMPTY_WORDS_FILTER_LIST);
			List<Element> elementsList = getClassLinksListFromUrl(url);
			dataList.stream().forEach(s -> warehouse.insert(s, url));
			elementsList.stream().forEach(element -> warehouse.insert(element.text(), url));
		}
		else
		{
			List<String> dataList = filterFromList(getTextListFromUrl(url), EMPTY_WORDS_FILTER_LIST);
			List<Element> elementsList = getClassLinksListFromUrl(url);
			dataList.stream().forEach(s -> warehouse.insert(s, url));
			elementsList.stream().forEach(element -> warehouse.insert(element.text(), url));
			elementsList.stream().forEach(element -> {
				try
				{
					insertIntoWarehouseFromUrl(computeAbsoluteURLFromClassName(context_api, element.attr(attribute)), level - 1);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			});
			
		}
	}
	
	public void insertIntoWarehouseFromUrl(String url) throws IOException
	{
		List<String> dataList = filterFromList(getTextListFromUrl(url), EMPTY_WORDS_FILTER_LIST);
		List<Element> elementsList = getClassLinksListFromUrl(url);
		dataList.stream().forEach(s -> warehouse.insert(s, url));
		elementsList.stream().forEach(element -> warehouse.insert(element.text(), url));
		performedURLs.add(url);
		elementsList.stream().forEach(element -> {
			try
			{	
				String absoluteURL = computeAbsoluteURLFromClassName(context_api, element.attr(attribute));
				if (!performedURLs.contains(absoluteURL))
					insertIntoWarehouseFromUrl(absoluteURL);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
	}
	
	private boolean hasAttributeTitleContains(Element element, String value)
	{
		String attributeTitle = "title";
		return element.attr(attributeTitle).startsWith(value);
	}
	
	public String computeAbsoluteURLFromClassName(String context, String hrefAttributeValue)
	{
		return context + hrefAttributeValue.replaceAll("\\.\\.\\/", "");
	}
}
