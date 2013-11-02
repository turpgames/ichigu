package com.turpgames.ichigu.model.tutorial;

import java.util.ArrayList;

import com.turpgames.framework.v0.component.ITutorialListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.Tutorial;
import com.turpgames.framework.v0.component.TutorialPage;
import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.R;

public class IchiguTutorial extends Tutorial {
	private final static float marginTop = 160f;

	public IchiguTutorial(ITutorialListener listener) {
		super(listener);
	}

	@Override
	protected void addPagesInfo() {
		pagesInfo = new GameInfo();
		pagesInfo.locate(Text.HAlignCenter, Text.VAlignTop);
		pagesInfo.setPadding(0, 100f);
		pagesInfo.setColor(R.colors.ichiguCyan);
	}

	@Override
	protected void concreteAddNextButton() {
		nextButton = new ImageButton(R.ui.imageButtonWidth, R.ui.imageButtonHeight, R.game.textures.next, R.colors.buttonDefault, R.colors.buttonTouched);
		nextButton.getLocation().set(Game.getScreenWidth() - (nextButton.getWidth() + Game.viewportToScreenX(30)), Game.viewportToScreenY(50));
	}

	@Override
	protected void concreteAddPrevButton() {
		prevButton = new ImageButton(R.ui.imageButtonWidth, R.ui.imageButtonHeight, R.game.textures.prev, R.colors.buttonDefault, R.colors.buttonTouched);
		prevButton.deactivate();
		prevButton.getLocation().set(Game.viewportToScreenX(30), Game.viewportToScreenY(50));
	}

	@Override
	protected void populatePages() {
		pages = new ArrayList<TutorialPage>();

		TutorialPage page;

		page = new TutorialPage("1", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutOverviewTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutOverview), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("2", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutSymbols1), Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("3", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		Text info = page.addInfo(Game.getLanguageManager().getString(R.strings.tutSymbols2), Text.HAlignCenter, 50);
		float cardSpace = 50;
		float x1 = (Game.getVirtualWidth() - (3 * Card.Width + 2 * cardSpace)) / 2;
		float x2 = x1 + Card.Width + cardSpace;
		float x3 = x2 + Card.Width + cardSpace;
		float y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 150;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Square, CardAttributes.Count_2, CardAttributes.Pattern_Striped, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Circle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));
		pages.add(page);

		page = new TutorialPage("4", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutIchiguTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutIchigu), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("5", R.fontSize.medium);
		info = page.addInfo(Game.getLanguageManager().getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Circle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x3, y));

		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentShapes), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSameColor), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("6", R.fontSize.medium);
		info = page.addInfo(Game.getLanguageManager().getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped, x3, y));

		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSameShape), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("7", R.fontSize.medium);
		info = page.addInfo(Game.getLanguageManager().getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_2, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSameShape), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("8", R.fontSize.medium);
		info = page.addInfo(Game.getLanguageManager().getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Circle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Square, CardAttributes.Count_2, CardAttributes.Pattern_Striped, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentShapes), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSameColor), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentPatterns), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("9", R.fontSize.medium);
		info = page.addInfo(Game.getLanguageManager().getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Circle, CardAttributes.Count_2, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Striped, x3, y));

		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentShapes), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentPatterns), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("10", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutGameModesTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSingleIchiguModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutPracticeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutFullGameModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutRelaxModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutNormalModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutFullChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		pages.add(page);

		page = new TutorialPage("11", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutMiniChallengeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("12", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutRelaxModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutRelaxMode), Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("13", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutNormalModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutNormalMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("14", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutFullChallengeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutFullChallengeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("15", R.fontSize.medium);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutScoreTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutScore), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("16", R.fontSize.medium);
		info = page.addInfo(Game.getLanguageManager().getString(R.strings.tutSampleIchiguScoreTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 30;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_2, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutScoreSameShape), Text.HAlignLeft, Card.Height + 30 + 30).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutScoreDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutScoreSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutScoreDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutScoreTotal), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);
	}
}
