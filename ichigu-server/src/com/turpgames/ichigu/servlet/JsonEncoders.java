package com.turpgames.ichigu.servlet;

import com.turpgames.ichigu.entity.HiScore;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.json.JsonEncoder;

public final class JsonEncoders {
	private JsonEncoders() {

	}

	public static final JsonEncoder<Player> player = new JsonEncoder<Player>(Player.class) {
	};
	public static final JsonEncoder<Score> score = new JsonEncoder<Score>(Score.class) {
	};
	public static final JsonEncoder<HiScore> hiscore = new JsonEncoder<HiScore>(HiScore.class) {
	};
	public static final JsonEncoder<HiScore[]> hiscores = new JsonEncoder<HiScore[]>(HiScore[].class) {
	};
}
