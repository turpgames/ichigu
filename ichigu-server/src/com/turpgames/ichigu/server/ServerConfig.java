package com.turpgames.ichigu.server;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.turpgames.utils.Util;

public class ServerConfig {
	private final static Map<String, String> props = new HashMap<String, String>();

	static {
		load("/com/turpgames/ichigu/server/ichigu.properties");
		String env = getEnvironment();
		if (!Util.Strings.isNullOrWhitespace(env))
			load(String.format("/com/turpgames/ichigu/server/ichigu-%s.properties", env));
	}

	private static void load(String path) {

		InputStream is = null;
		try {
			is = ServerConfig.class.getResourceAsStream(path);
			Properties p = new Properties();
			p.load(is);

			for (Object key : p.keySet())
				props.put((String) key, p.getProperty((String) key));
		} catch (Throwable t) {
			t.printStackTrace();
			Util.IO.close(is);
		}
	}

	public static String getEnvironment() {
		return getProp("environment");
	}

	public static String getJdbcDriver() {
		return getProp("dbJdbcDriver");
	}

	public static String getDbConnectionString() {
		return getProp("dbConnectionString");
	}

	public static String getDbUser() {
		return getProp("dbUser");
	}

	public static String getDbPassword() {
		return getProp("dbPassword");
	}

	public static int getCacheTimeout() {
		return Util.Strings.parseInt(getProp("cacheTimeout"));
	}
	
	public static boolean isFakeScoreGeneratorEnabled() {
		return Util.Strings.parseBoolean(getProp("enableFakeScoreGenerator"));
	}
	
	public static int getFakeScoresPerDay() {
		return Util.Strings.parseInt(getProp("fakeScoresPerDay"));
	}

	public static int getMinFakeUserId() {
		return Util.Strings.parseInt(getProp("minFakeUserId"));
	}

	public static int getMaxFakeUserId() {
		return Util.Strings.parseInt(getProp("maxFakeUserId"));
	}

	public static int getMinMiniChallengeScore() {
		return Util.Strings.parseInt(getProp("minMiniChallengeScore"));
	}

	public static int getMaxMiniChallengeScore() {
		return Util.Strings.parseInt(getProp("maxMiniChallengeScore"));
	}

	public static int getMinStandardScore() {
		return Util.Strings.parseInt(getProp("minStandardScore"));
	}

	public static int getMaxStandardScore() {
		return Util.Strings.parseInt(getProp("maxStandardScore"));
	}

	public static int getMinTimeChallengeScore() {
		return Util.Strings.parseInt(getProp("minTimeChallengeScore"));
	}

	public static int getMaxTimeChallengeScore() {
		return Util.Strings.parseInt(getProp("maxTimeChallengeScore"));
	}
	
	private static String getProp(String key) {
		return props.get(key);
	}
}
