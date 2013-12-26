package com.turpgames.ichigu.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utils {
	private static final Random rnd = new Random();
	private static MessageDigest sha1;
	
	static {
		try {
			sha1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String readUtf8String(InputStream is) throws IOException {
		StringBuffer strBuffer = new StringBuffer();
		byte[] buffer = new byte[128];
		int bytesRead;
		while ((bytesRead = is.read(buffer, 0, buffer.length)) > 0)
			strBuffer.append(new String(buffer, 0, bytesRead, "UTF-8"));
		return strBuffer.toString();
	}

	public static boolean isNullOrWhitespace(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static List<Node> getChildElements(Node node) {
		NodeList childNodes = node.getChildNodes();

		List<Node> list = new ArrayList<Node>();

		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element)
				list.add(childNodes.item(i));
		}

		return list;
	}

	public static List<Node> getChildNodes(Node node, String childNodeName) {
		NodeList childNodes = node.getChildNodes();

		List<Node> list = new ArrayList<Node>();

		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodeName.equals(childNodes.item(i).getNodeName()))
				list.add(childNodes.item(i));
		}

		return list;
	}

	public static Node getChildNode(Node node, String childNodeName) {
		NodeList childNodes = node.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodeName.equals(childNodes.item(i).getNodeName()))
				return childNodes.item(i);
		}

		return null;
	}

	public static List<Node> findChildNodesByAttributeValue(Node node,
			String childNodeName, String attributeName, String attributeValue) {
		NodeList childNodes = node.getChildNodes();

		List<Node> list = new ArrayList<Node>();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			if (childNodeName.equals(child.getNodeName())
					&& attributeValue.equals(getAttributeValue(child,
							attributeName)))
				list.add(child);
		}

		return list;
	}

	public static Node findChildNodeByAttributeValue(Node node,
			String childNodeName, String attributeName, String attributeValue) {
		NodeList childNodes = node.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			if (childNodeName.equals(child.getNodeName())
					&& attributeValue.equals(getAttributeValue(child,
							attributeName)))
				return child;
		}

		return null;
	}

	public static String getAttributeValue(Node node, String attributeName) {
		if (node instanceof Element)
			return ((Element) node).getAttribute(attributeName);
		return null;
	}

	public static Document loadXml(InputStream is) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(is);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			close(is);
		}
	}

	public static Object createInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static float parseFloat(String f) {
		return Float.parseFloat(f);
	}

	public static int parseInt(String i) {
		return Integer.parseInt(i);
	}

	public static boolean parseBoolean(String b) {
		return Boolean.parseBoolean(b);
	}

	public static int randInt() {
		return rnd.nextInt();
	}

	/**
	 * 
	 * @param maxValue
	 * @return [0 - maxValue)
	 */
	public static int randInt(int maxValue) {
		if (maxValue == 0)
			return 0;
		return rnd.nextInt(maxValue);
	}

	/**
	 * 
	 * @param minValue
	 * @param maxValue
	 * @return [minValue - maxValue)
	 */
	public static int randInt(int minValue, int maxValue) {
		if (minValue == maxValue)
			return minValue;
		return randInt(maxValue - minValue) + minValue;
	}

	public static <T> void shuffle(T[] array) {
		int iter = array.length * array.length;
		while (iter-- > 0) {
			int x = randInt(array.length);
			int y = randInt(array.length);

			T tmp = array[x];
			array[x] = array[y];
			array[y] = tmp;
		}
	}

	public static <T> void shuffle(List<T> list) {
		int iter = list.size() * list.size();
		while (iter-- > 0) {
			int x = randInt(list.size());
			int y = randInt(list.size()-1);

			T tmp = list.get(x);
			list.remove(x);
			list.add(y, tmp);
		}
	}
	
	public static <T> void swap(List<T> list, int x, int y) {
		T xT = list.get(x);
		T yT = list.get(y);
		if (y > x) {
			list.remove(y);
			list.remove(x);
			list.add(x, yT);
			list.add(y, xT);
		}
		else {
			list.remove(x);
			list.remove(y);
			list.add(y, xT);
			list.add(x, yT);
		}
	}
	
	public static <T> void shuffle(T[] array, int start, int end) {
		int iter = (start - end) * (start - end);
		shuffle(array, start, end, iter);
	}

	public static <T> void shuffle(T[] array, int start, int end, int iter) {
		if (start == end || start + 1 == end)
			return;

		while (iter-- > 0) {
			int i1 = Utils.randInt(start, end);
			int i2 = Utils.randInt(start, end);
			while (i1 == i2)
				i2 = Utils.randInt(start, end);

			T tmp = array[i1];
			array[i1] = array[i2];
			array[i2] = tmp;
		}
	}

	public static <T> T random(T[] array) {
		return array[randInt(array.length)];
	}

	public static String getTimeString(int time) {
		int min = time / 60;
		int sec = time % 60;
		return (min < 10 ? ("0" + min) : ("" + min)) + ":"
				+ (sec < 10 ? ("0" + sec) : ("" + sec));
	}

	public static String getTimeString(float time) {
		int min = ((int) time) / 60;
		int sec = ((int)time) % 60;
		int msec = (int) ((time - (int)time)*1000);
		return (min < 10 ? ("0" + min) : ("" + min)) + ":"
				+ (sec < 10 ? ("0" + sec) : ("" + sec)) + "."
				+ msec;
	}
	
	public static void close(Closeable closable) {
		if (closable == null)
			return;
		try {
			closable.close();
		} catch (IOException e) {
			// ignore
			e.printStackTrace();
		}
	}

	public static void threadSleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String digest(String plainUtf8Text) {
		try {
			byte[] bytes = plainUtf8Text.getBytes("UTF-8");
			byte[] digest = sha1.digest(bytes);
			return DatatypeConverter.printHexBinary(digest);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}
}