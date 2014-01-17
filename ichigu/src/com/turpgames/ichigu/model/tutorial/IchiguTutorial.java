package com.turpgames.ichigu.model.tutorial;

import java.util.ArrayList;

import com.turpgames.framework.v0.component.ITutorialListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.Tutorial;
import com.turpgames.framework.v0.component.TutorialPage;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

class IchiguTutorial extends Tutorial {

	private TouchSlidingViewSwitcher tp = new TouchSlidingViewSwitcher();

	IchiguTutorial(ITutorialListener listener) {
		super(listener);
		tp.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight());
		for (TutorialPage page : pages)
			tp.addView(page);
	}
	
	@Override

	protected void drawPage() {
		pageTitle.draw();
		pagesInfo.draw();
		tp.draw();
	}

	
	@Override
	public void activate() {
		super.activate();
		tp.activate();
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		tp.deactivate();
	}

	@Override
	protected void addPagesInfo() {
		pagesInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pagesInfo.setPadding(0, 160f);
		pagesInfo.getColor().set(R.colors.ichiguCyan);
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
	protected void concreteAddNextButton() {
		nextButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.game.textures.next, R.colors.buttonDefault, R.colors.buttonTouched);
		nextButton.getLocation().set(Game.getScreenWidth() - (nextButton.getWidth() + Game.viewportToScreenX(30)), Game.viewportToScreenY(30));
	}

	@Override
	protected void concreteAddPrevButton() {
		prevButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.game.textures.prev, R.colors.buttonDefault, R.colors.buttonTouched);
		prevButton.deactivate();
		prevButton.getLocation().set(Game.viewportToScreenX(30), Game.viewportToScreenY(30));
	}

	@Override
	protected void populatePages() {
		pages = new ArrayList<TutorialPage>();

		TutorialPage page;

		page = new TutorialPage("1", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutOverviewTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutOverview), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("2", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutSymbols1), Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("3", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		Text info = page.addInfo(Ichigu.getString(R.strings.tutSymbols2), Text.HAlignCenter, 50);
		float cardSpace = 50;
		float x1 = (Game.getVirtualWidth() - (3 * R.sizes.cardWidth + 2 * cardSpace)) / 2;
		float x2 = x1 + R.sizes.cardWidth + cardSpace;
		float x3 = x2 + R.sizes.cardWidth + cardSpace;
		float y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 150;
		page.addImage(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count1, CardAttributes.patternEmpty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeSquare, CardAttributes.count2, CardAttributes.patternStriped, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeCircle, CardAttributes.count3, CardAttributes.patternFilled, x3, y));
		pages.add(page);

		page = new TutorialPage("4", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutIchigu), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("5", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeCircle, CardAttributes.count1, CardAttributes.patternEmpty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count1, CardAttributes.patternEmpty, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeTriangle, CardAttributes.count1, CardAttributes.patternEmpty, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSameColor), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("6", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeTriangle, CardAttributes.count1, CardAttributes.patternFilled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeTriangle, CardAttributes.count2, CardAttributes.patternFilled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count3, CardAttributes.patternFilled, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutSameShape), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("7", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeCircle, CardAttributes.count2, CardAttributes.patternEmpty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count1, CardAttributes.patternFilled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count3, CardAttributes.patternStriped, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentPatterns), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("8", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleNotIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count1, CardAttributes.patternFilled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeCircle, CardAttributes.count2, CardAttributes.patternFilled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeSquare, CardAttributes.count3, CardAttributes.patternFilled, x3, y));

		page.addInfo("!! " + String.format(Ichigu.getString(R.strings.tutTwoAndOneShape),
				Ichigu.getString(R.strings.square), Ichigu.getString(R.strings.circle))
				, Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("9", R.fontSize.medium);
		info = page.addInfo(Ichigu.getString(R.strings.tutSampleNotIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count2, CardAttributes.patternEmpty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeCircle, CardAttributes.count2, CardAttributes.patternEmpty, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count2, CardAttributes.patternEmpty, x3, y));

		page.addInfo("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addInfo("!! " + String.format(Ichigu.getString(R.strings.tutTwoAndOneColor),
				Ichigu.getString(R.strings.blue), Ichigu.getString(R.strings.red))
				, Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("10", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutGameModesTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("- " + Ichigu.getString(R.strings.tutSingleIchiguModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutPracticeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Ichigu.getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Ichigu.getString(R.strings.tutFullGameModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Ichigu.getString(R.strings.tutNormalModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Ichigu.getString(R.strings.tutFullChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		pages.add(page);

		page = new TutorialPage("11", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutPracticeModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutPracticeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("12", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutMiniChallengeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("13", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutFullModesTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutFullModes), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("14", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutNormalModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutNormalMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("15", R.fontSize.medium);
		page.addInfo(Ichigu.getString(R.strings.tutFullChallengeModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Ichigu.getString(R.strings.tutFullChallengeMode), Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);
	}
}
