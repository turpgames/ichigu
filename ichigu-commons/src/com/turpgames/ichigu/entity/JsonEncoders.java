package com.turpgames.ichigu.entity;

import com.turpgames.json.JsonEncoder;

public final class JsonEncoders {
	private JsonEncoders() {

	}

	public static final JsonEncoder<Player> player = new JsonEncoder<Player>(Player.class);
	
	public static final JsonEncoder<Score> score = new JsonEncoder<Score>(Score.class);
	
	public static final JsonEncoder<LeadersBoard> leadersBoard = new JsonEncoder<LeadersBoard>(LeadersBoard.class);	
}
