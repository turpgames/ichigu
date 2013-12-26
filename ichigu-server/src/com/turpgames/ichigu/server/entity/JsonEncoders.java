package com.turpgames.ichigu.server.entity;

import com.turpgames.ichigu.server.JsonEncoder;

public final class JsonEncoders {
	private JsonEncoders() {

	}
	
	public static final PlayerEncoder player = new PlayerEncoder();
	public static class PlayerEncoder extends JsonEncoder<Player> {
		public PlayerEncoder() {
			super(Player.class);
		}
	}
	
	public static final ScoreEncoder score = new ScoreEncoder();
	public static class ScoreEncoder extends JsonEncoder<Score> {
		public ScoreEncoder() {
			super(Score.class);
		}
	}
	
	public static final HiScoreEncoder hiscore = new HiScoreEncoder();
	public static class HiScoreEncoder extends JsonEncoder<HiScore> {
		public HiScoreEncoder() {
			super(HiScore.class);
		}
	}
}
