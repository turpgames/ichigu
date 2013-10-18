package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.FontManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.Card;

public final class R {
	/**
	 * game.xml yapýsýndaki id ve key'ler
	 */
	public static final class game {
		public static final class controls {
			public static final class mainMenu {
				public static final String btnSound = "btnSound";
			}
		}

		public static final class forms {
			public static final String mainMenu = "mainMenu";
		}

		public static final class screens {
			public static final String menu = "menu";
		}

		public static final class sounds {
			public static final String error = "error";
			public static final String success = "success";
			public static final String timeUp = "time-up";
			public static final String wait = "wait";
			public static final String flip = "flip";
		}

		public static final class textures {
			public static final String logo = "logo";

			public static final String cardBorder = "card-border";
			public static final String cardClosed = "card-closed";
			public static final String cardEmpty = "card-empty";

			public static final String hint = "hint";
			public static final String next = "next";
			public static final String prev = "prev";
			public static final String skip = "play";
			public static final String refresh = "refresh";

			public static final String stripedCircle = "card-14";
			public static final String filledSquare = "card-22";
			public static final String emptyTriangle = "card-41";

			public static final class toolbar {
				public static final String back = "tb_back";
				public static final String settings = "tb_settings";
				public static final String musicPlay = "tb_music_play";
				public static final String musicStop = "tb_music_stop";
				public static final String vibrationOn = "tb_vibration_on";
				public static final String vibrationOff = "tb_vibration_off";
				public static final String soundOn = "tb_sound_on";
				public static final String soundOff = "tb_sound_off";
			}
		}
	}

	public static final class settings {
		public static final String music = "music";
		public static final String sound = "sound";
		public static final String vibration = "vibration";

		public static final class hiscores {
			public static final String practice = "hiscore_practice";
			public static final String challenge = "hiscore_challenge";
		}
	}

	public static final class learningModeScreen {
		public static final class layout {
			public static final Vector cardOnTable1Pos = new Vector(Game.getVirtualWidth() / 2 - Card.Width - 20, 4 * Game.getVirtualHeight() / 7 - 50);
			public static final Vector cardOnTable2Pos = new Vector(Game.getVirtualWidth() / 2 + 20, 4 * Game.getVirtualHeight() / 7 - 50);

			public static final Vector cardToSelect1Pos = new Vector(Game.getVirtualWidth() / 2 - Card.Width * 1.5f - 40, 2 * Game.getVirtualHeight() / 7 - 50);
			public static final Vector cardToSelect2Pos = new Vector(Game.getVirtualWidth() / 2 - Card.Width * 0.5f, 2 * Game.getVirtualHeight() / 7 - 50);
			public static final Vector cardToSelect3Pos = new Vector(Game.getVirtualWidth() / 2 + Card.Width * 0.5f + 40, 2 * Game.getVirtualHeight() / 7 - 50);

			public static final Vector[] positions = new Vector[] { cardOnTable1Pos, cardOnTable2Pos, cardToSelect1Pos, cardToSelect2Pos, cardToSelect3Pos };
		}
	}

	public static final class symbolpositions {
		public static final Vector firstOfOne = new Vector((Card.Width - Card.SymbolWidth) / 2, (Card.Height - Card.SymbolHeight) / 2);

		public static final Vector firstOfTwo = new Vector((Card.Width - Card.SymbolWidth) / 2, Card.Height / 2 - Card.SymbolHeight * 1.1f);
		public static final Vector secondOfTwo = new Vector((Card.Width - Card.SymbolWidth) / 2, Card.Height / 2 + Card.SymbolHeight * 0.1f);

		public static final Vector firstOfThree = new Vector((Card.Width - Card.SymbolWidth) / 2, 2 + Card.Height / 2 - Card.SymbolHeight * 1.65f);
		public static final Vector secondOfThree = new Vector((Card.Width - Card.SymbolWidth) / 2, 2 + Card.Height / 2 - Card.SymbolHeight * 0.5f);
		public static final Vector thirdOfThree = new Vector((Card.Width - Card.SymbolWidth) / 2, 2 + Card.Height / 2 + Card.SymbolHeight * 0.65f);

		public static Vector tutorial300x96 = new Vector(Game.getVirtualWidth() / 2 - 300 / 2, Game.getVirtualHeight() / 2 - 60);
		public static Vector tutorial350x300 = new Vector(Game.getVirtualWidth() / 2 - 350 / 2, 200);
	}

	public static final class colors {
		public static final Color ichiguWhite = Color.fromHex("#ffffffff");
		public static final Color ichiguBlack = Color.fromHex("#000000ff");
		public static final Color ichiguRed = Color.fromHex("#d0583bff");
		public static final Color ichiguGreen = Color.fromHex("#56bd89ff");
		public static final Color ichiguBlue = Color.fromHex("#3974c1ff");
		public static final Color ichiguYellow = Color.fromHex("#f9b000ff");
		public static final Color ichiguCyan = Color.fromHex("#00f9b0ff");
		public static final Color ichiguMagenta = Color.fromHex("#f900b0ff");
	}

	public static final class fontSize {
		public static final float xSmall = FontManager.defaultFontSize * 0.5f;
		public static final float small = FontManager.defaultFontSize * 0.625f;
		public static final float medium = FontManager.defaultFontSize * 0.75f;
		public static final float large = FontManager.defaultFontSize * 1f;
		public static final float xLarge = FontManager.defaultFontSize * 0.25f;
	}

	public static final class ui {
		public final static float imageButtonSize = 64;
	}

	private R() {
	}
}