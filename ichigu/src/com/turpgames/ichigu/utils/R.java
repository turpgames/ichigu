package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.FontManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;

public final class R {	
	public final static class sizes {
		public final static float maxScale = 0.2f;
		public final static float menuButtonSpacing = Game.scale(30);

		public final static float tutorialMarginTop = 220f;
		public final static float logoSize = Game.scale(140f);
		public final static int scoreImageSize = 64;

		public final static float tutorialbuttonSpacing = Game.scale(10);
		public final static float toolbarMargin = Game.scale(15);
		public final static int singleModeDividerHeight = 10;
		public final static int singleModeDividerWidth = 420;

		public final static int cardWidth = 100;
		public final static int cardHeight = 174;
		public final static int cardSpace = 7;
		public final static int symbolHeight = 45;
		public final static int symbolWidth = 45;

		public final static float questionMarkSize = 128;
		public final static float menuButtonSize = 64;
		public final static float flagButtonSize = 128;
		public final static float libgdxLogoWidth = 200;
		public final static float libgdxLogoHeight = 33;

		public final static float menuButtonSizeToScreen = Game.scale(menuButtonSize);
		public final static float langFlagButtonSizeToScreen = Game.scale(flagButtonSize);
	}

	public final static class durations {
		public final static float fadingDuration = 0.25f;
		public final static float blinkDuration = 1f;
		public final static int hintNotificationInterval = 30;
		public final static float miniModeBlockDuration = 2f;
		public final static int miniModeChallengeDuration = 60;
		public final static float toastSlideDuration = 0.2f;
		public final static float toastDisplayDurationPerWord = 0.15f;
		public final static float toastDisplayDurationBuffer = 1.5f;
		public final static float sudokuModeSwapDuration = 0.5f;
		public final static float fullModeTimerPauseDuration = 30f;
	}

	public final static class counts {
		public final static int ichiguCardCount = 3;
		public final static int ichiguDeckCardCount = 81;
		public final static int fullModeCols = 5;
		public final static int fullModeActiveCardCount = 12;
		public final static int fullModeExtraCardCount = 3;
		public final static int fullModeTotalCardsOnTable = fullModeActiveCardCount + fullModeExtraCardCount;
		public final static float fullModeSecondPerPenalty = 10f;
		public final static int blinkPerSecond = 10;
	}

	public final static class prices {
		public final static int singleHintPrice = 10;
		public final static int tripleHintPrice = 25;
		public final static int timerPausePrice = 25;
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

		public static final Color buttonDefault = ichiguWhite;
		public static final Color buttonTouched = ichiguYellow;
	}

	public static final class fontSize {
		public static final float xSmall = FontManager.defaultFontSize * 0.5f;
		public static final float small = FontManager.defaultFontSize * 0.625f;
		public static final float medium = FontManager.defaultFontSize * 0.75f;
		public static final float large = FontManager.defaultFontSize * 1f;
		public static final float xLarge = FontManager.defaultFontSize * 1.25f;
	}

	/**
	 * game.xml yapisindaki id ve key'ler
	 */
	public static final class game {
		public static final class params {
			public final static String turpAddress = "turp-address";
			public final static String libgdxAddress = "libgdx-address";
			public final static String playStoreAddress = "play-store-address";
			public final static String appStoreAddressIOS7 = "app-store-address-ios7";
			public final static String appStoreAddressOld = "app-store-address-old";
			public final static String twitterAddress = "twitter-address";
			public final static String facebookAddress = "facebook-address";
		}

		public static final class forms {
			public static final String mainMenu = "mainMenu";
			public static final String playMenu = "playMenu";
		}

		public static final class screens {
			public static final String menu = "menu";
			public static final String practice = "practice";
		}

		public static final class sounds {
			public static final String error = "error";
			public static final String success = "success";
			public static final String timeUp = "time-up";
			public static final String wait = "wait";
			public static final String flip = "flip";
		}

		public static final class textures {
			public static final class points {
				public static final String shapecircle = "shapecircle";
				public static final String shaperectangle = "shaperectangle";
				public static final String shapetriangle = "shapetriangle";
				public static final String shapeall = "shapeall";

				public static final String countone = "countone";
				public static final String counttwo = "counttwo";
				public static final String countthree = "countthree";
				public static final String countall = "countall";

				public static final String fillempty = "fillempty";
				public static final String fillstriped = "fillstriped";
				public static final String fillfull = "fillfull";
				public static final String fillall = "fillall";

				public static final String colorone = "colorone";
				public static final String colorall = "colorall";
			}

			public static final class singlegame {
				public static final String questionmark = "question-mark";
				public static final String correctmark = "correct-mark";
				public static final String incorrectmark = "incorrect-mark";
			}

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

			public static final String logo = "logo";
			public static final String splashLogo = "splash_logo";

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

			public static final String libgdx = "libgdx";

			public final static String backgroundPixel = "backgroundPixel";

			public static String questionCard = "question-card";

			public static final String hintSingle = "hint_single";
			public static final String hintTriple = "hint_triple";
			public static final String timerPause = "pause_timer";
		}
	}

	public static final class settings {
		public static final class hiscores {
			public static final String minichallenge = "hiscore_practice";
			public static final String normal = "hiscore_normal";
			public static final String normaltime = "hiscore_normal_time";
			public static final String fullchallenge = "hiscore_challenge";
		}

		public static final String music = "music";
		public static final String sound = "sound";

		public static final String vibration = "vibration";

		public static final String language = "language";
		public static final String country = "country";

		public static final String ichiguBalance = "ichigu-points";
		public static final String singleHintCount = "single-hint-count";
		public static final String tripleHintCount = "triple-hint-count";
		public static final String timerPauseCount = "timer-pause-count";
	}

	public static final class singleGameMode {
		public static final Vector cardOnTable1Pos = new Vector(
				Game.getVirtualWidth() / 2 - R.sizes.cardWidth * 1.5f - 40,
				4 * Game.getVirtualHeight() / 7 - 50);
		public static final Vector cardOnTable2Pos = new Vector(
				Game.getVirtualWidth() / 2 - R.sizes.cardWidth * 0.5f,
				4 * Game.getVirtualHeight() / 7 - 50);
		public static final Vector cardOnTable3Pos = new Vector(
				Game.getVirtualWidth() / 2 + R.sizes.cardWidth * 0.5f + 40,
				4 * Game.getVirtualHeight() / 7 - 50);
		public static final Vector markPos = new Vector(Game.getVirtualWidth()
				/ 2 + R.sizes.cardWidth * 0.5f + 40
				+ (R.sizes.cardWidth - R.sizes.questionMarkSize) / 2, 4
				* Game.getVirtualHeight() / 7 - 50
				+ (R.sizes.cardHeight - R.sizes.questionMarkSize) / 2);

		public static final Vector cardToSelect1Pos = new Vector(
				Game.getVirtualWidth() / 2 - R.sizes.cardWidth * 1.5f - 40,
				2 * Game.getVirtualHeight() / 7 - 50);
		public static final Vector cardToSelect2Pos = new Vector(
				Game.getVirtualWidth() / 2 - R.sizes.cardWidth * 0.5f,
				2 * Game.getVirtualHeight() / 7 - 50);
		public static final Vector cardToSelect3Pos = new Vector(
				Game.getVirtualWidth() / 2 + R.sizes.cardWidth * 0.5f + 40,
				2 * Game.getVirtualHeight() / 7 - 50);

		public static final Vector[] positions = new Vector[] {
				cardOnTable1Pos, cardOnTable2Pos, cardToSelect1Pos,
				cardToSelect2Pos, cardToSelect3Pos };
	}

	public static final class strings {
		public final static String howToPlay = "howToPlay";
		public final static String hiScores = "hiScores";
		public final static String about = "about";

		public final static String newGame = "newGame";
		public final static String backToMenu = "backToMenu";

		public final static String circle = "circle";
		public final static String square = "square";
		public final static String triangle = "triangle";

		public final static String blue = "blue";
		public final static String red = "red";
		public final static String green = "green";

		public final static String empty = "empty";
		public final static String striped = "striped";
		public final static String filled = "filled";

		public final static String sameShapes = "sameShapes";
		public final static String sameColors = "sameColors";
		public final static String samePatterns = "samePatterns";
		public final static String sameCounts = "sameCounts";
		public final static String differentShapes = "differentShapes";
		public final static String differentColors = "differentColors";
		public final static String differentPatterns = "differentPatterns";
		public final static String differentCounts = "differentCounts";

		public final static String found = "found";

		public final static String exitConfirm = "exitConfirm";
		public final static String resetConfirm = "resetConfirm";
		public final static String hiscoreResetConfirm = "hiscoreResetConfirm";
		public final static String exitProgramConfirm = "exitProgramConfirm";

		public final static String yes = "yes";
		public final static String no = "no";

		public final static String noIchigu = "noIchigu";
		public final static String oneIchigu = "oneIchigu";
		public final static String someIchigu = "someIchigu";

		public final static String buyFromMarket = "buyFromMarket";
		public final static String tryAgain = "tryAgain";
		public final static String wait = "wait";

		public final static String miniChallengeResult = "miniChallengeResult";
		public final static String fullChallengeResult = "fullChallengeResult";
		public final static String normalResult = "normalResult";

		public final static String newHiscore = "newHiscore";

		public final static String resetHiscore = "resetHiscore";
		public final static String hiscoreInfo = "hiscoreInfo";

		public final static String tutOverviewTitle = "tutOverviewTitle";
		public final static String tutOverview = "tutOverview";
		public final static String tutSymbolsTitle = "tutSymbolsTitle";
		public final static String tutSymbols1 = "tutSymbols1";
		public final static String tutSymbols2 = "tutSymbols2";
		public final static String tutIchiguTitle = "tutIchiguTitle";
		public final static String tutIchigu = "tutIchigu";
		public final static String tutSampleIchiguTitle = "tutSampleIchiguTitle";
		public final static String tutSameShape = "tutSameShape";
		public final static String tutSameColor = "tutSameColor";
		public final static String tutSamePattern = "tutSamePattern";
		public final static String tutSameCount = "tutSameCount";
		public final static String tutDifferentShapes = "tutDifferentShapes";
		public final static String tutDifferentColors = "tutDifferentColors";
		public final static String tutDifferentPatterns = "tutDifferentPatterns";
		public final static String tutDifferentCounts = "tutDifferentCounts";

		public final static String tutSampleNotIchiguTitle = "tutSampleNotIchiguTitle";
		public final static String tutTwoAndOneShape = "tutTwoAndOneShape";
		public final static String tutTwoAndOneColor = "tutTwoAndOneColor";
		public final static String tutTwoAndOnePattern = "tutTwoAndOnePattern";
		public final static String tutTwoAndOneCount = "tutTwoAndOneCount";

		public final static String tutGameModesTitle = "tutGameModesTitle";
		public final static String tutSingleIchiguModes = "tutSingleIchiguModes";
		public final static String tutFullGameModes = "tutFullGameModes";

		public final static String tutPracticeModeTitle = "tutPracticeModeTitle";
		public final static String tutPracticeMode = "tutPracticeMode";

		public final static String tutMiniChallengeModeTitle = "tutMiniChallengeModeTitle";
		public final static String tutMiniChallengeMode = "tutMiniChallengeMode";

		public final static String tutFullModesTitle = "tutFullModesTitle";
		public final static String tutFullModes = "tutFullModes";

		public final static String tutNormalModeTitle = "tutNormalModeTitle";
		public final static String tutNormalMode = "tutNormalMode";

		public final static String tutFullChallengeModeTitle = "tutFullChallengeModeTitle";
		public final static String tutFullChallengeMode = "tutFullChallengeMode";

		public final static String aboutInfo1 = "aboutInfo1";
		public final static String aboutInfo2 = "aboutInfo2";
		public final static String aboutInfo3 = "aboutInfo3";
		public final static String aboutThanks = "aboutThanks";

		public final static String insufficientIchiguBalance = "insufficientIchiguBalance";
		public final static String market = "market";
		public final static String marketPriceInfo = "marketPriceInfo";
		public final static String marketFeatureStatus = "marketFeatureStatus";
		public final static String singleHint = "singleHint";
		public final static String tripleHint = "tripleHint";
		public final static String pauseTimer = "pauseTimer";
		public final static String singleHintInfo = "singleHintInfo";
		public final static String tripleHintInfo = "tripleHintInfo";
		public final static String pauseTimerInfo = "pauseTimerInfo";
		public final static String bonusFeatureOnceWarning = "bonusFeatureOnceWarning";

		public final static String ok = "ok";
		public final static String buy = "buy";

		public final static String sudoku = "sudoku";
	}

	public static final class symbolpositions {
		public static final Vector firstOfOne = new Vector(
				(R.sizes.cardWidth - R.sizes.symbolWidth) / 2, (R.sizes.cardHeight - R.sizes.symbolHeight) / 2);

		public static final Vector firstOfTwo = new Vector(
				(R.sizes.cardWidth - R.sizes.symbolWidth) / 2, R.sizes.cardHeight / 2 - R.sizes.symbolHeight * 1.1f);

		public static final Vector secondOfTwo = new Vector(
				(R.sizes.cardWidth - R.sizes.symbolWidth) / 2, R.sizes.cardHeight / 2 + R.sizes.symbolHeight * 0.1f);

		public static final Vector firstOfThree = new Vector(
				(R.sizes.cardWidth - R.sizes.symbolWidth) / 2, 2 + R.sizes.cardHeight / 2 - R.sizes.symbolHeight * 1.65f);

		public static final Vector secondOfThree = new Vector(
				(R.sizes.cardWidth - R.sizes.symbolWidth) / 2, 2 + R.sizes.cardHeight / 2 - R.sizes.symbolHeight * 0.5f);

		public static final Vector thirdOfThree = new Vector(
				(R.sizes.cardWidth - R.sizes.symbolWidth) / 2, 2 + R.sizes.cardHeight / 2 + R.sizes.symbolHeight * 0.65f);
	}

	private R() {
	}
}