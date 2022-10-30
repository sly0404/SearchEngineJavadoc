package com.engine;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.html.HTMLDocument;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		String url = "https://docs.oracle.com/javase/8/docs/api/java/awt/event/package-frame.html";
		Warehouse warehouse = new Warehouse();
		/*warehouse.insertIntoWarehouse(1);
		warehouse.show();*/
		
		String url2 = "https://docs.oracle.com/javase/8/docs/api/java/awt/event/ActionEvent.html";
		Engine engine = new Engine(warehouse, url);
		//String text = "JavaScript is disabled on your browser. Skip navigation links Overview Package Class Use Tree Deprecated Index";
		
		/*engine.insertIntoWarehouseFromUrl(url2, 0);
		warehouse.show();*/
		
		/*String FILTER = "is,on,your,the,The,a,A,an,An,of,from,to,that,was,were,in,for,but,this,This,if,or";
		List<String> FILTER_LIST = Arrays.asList(FILTER.split(","));
		System.out.println(engine.getTextListFromUrl(url2));
		System.out.println();
		System.out.println();
		System.out.println(engine.filterFromList2(engine.getTextListFromUrl(url2), FILTER_LIST));*/
		
		//String context_api = "https://docs.oracle.com/javase/8/docs/api/";
		//System.out.println(engine.computeAbsoluteURLFromClassName(context_api, "../../../java/io/Serializable.html"));
		
		engine.createIndex(0);
		//engine.insertIntoWarehouseFromUrl(url2);
		warehouse.show();
	} 
	
}
