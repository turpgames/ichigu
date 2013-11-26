package com.turpgames.ichigu.model.tutorial;

import java.util.ArrayList;

import com.turpgames.framework.v0.component.ITutorialListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.Tutorial;
import com.turpgames.framework.v0.component.TutorialPage;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

class IchiguTutorial extends Tutorial {
	private final static float marginTop = 220f;
	private final static float buttonSize = Game.scale(R.ui.imageButtonWidth);

	IchiguTutorial(ITutorialListener listener) {
		super(listener);
	}
	
	@Override
	protected void addPageTitle() {
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.setText(Ichigu.getString(R.strings.howToPlay));
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);	
	}

	@Override
	protected void addPagesInfo() {
		pagesInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pagesInfo.setPadding(0, 160f);
		pagesInfo.getColor().set(R.colors.ichiguCyan);
	}

	@Override
	protected void concreteAddNextButton() {
		nextButton = new ImageButton(buttonSize, buttonSize, R.game.textures.next, R.colors.buttonDefault, R.colors.buttonTouched);
		nextButton.getLocation().set(Game.getScreenWidth() - (nextButton.getWidth() + Game.viewportToScreenX(30)), Game.viewportToScreenY(30));
	}

	@Override
	protected void concreteAddPrevButton() {
		prevButton = new ImageButton(buttonSize, buttonSize, R.game.textures.prev, R.colors.buttonDefault, R.colors.buttonTouched);
		prevButton.deactivate();
		prevButton.getLocation().set(Game.viewportToScreenX(30), Game.viewportToScreenY(30));
	}

	@Override
	protected void populatePages() {
		pages = new ArrayList<TutorialPage>();

		TutorialPage page;

		page = new TutorialPage("1", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutOverviewTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutOverview), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("2", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutSymbols1), Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("3", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		Text info = page.addInfo(Ichigu.getString(R.strings.tutSymbols2), Text.HAlignCenter, 50);
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
		page.addInfo(Ichigu.getString(R.strings.tutIchiguTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutIchigu), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("5", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Circle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSameColor), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("6", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_2, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutSameShape), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("7", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Circle, CardAttributes.Count_2, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Striped, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentPatterns), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("8", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleNotIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Circle, CardAttributes.Count_2, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Square, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("!! " + String.format(Ichigu.getString(R.strings.tutTwoAndOneShape),
				Ichigu.getString(R.strings.square), Ichigu.getString(R.strings.circle))
				, Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("9", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleNotIchiguTitle), Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_2, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Circle, CardAttributes.Count_2, CardAttributes.Pattern_Empty, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_2, CardAttributes.Pattern_Empty, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("!! " + String.format(Ichigu.getString(R.strings.tutTwoAndOneColor),
				Ichigu.getString(R.strings.blue), Ichigu.getString(R.strings.red))
				, Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);
		
		page = new TutorialPage("10", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutGameModesTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSingleIchiguModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutPracticeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Ichigu.getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Ichigu.getString(R.strings.tutFullGameModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutNormalModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Ichigu.getString(R.strings.tutFullChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		pages.add(page);

		page = new TutorialPage("11", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutPracticeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutPracticeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("12", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutMiniChallengeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("13", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutFullModesTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutFullModes), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("14", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutNormalModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutNormalMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("15", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutFullChallengeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutFullChallengeMode), Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);
	}
}
