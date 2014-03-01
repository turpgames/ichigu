package com.turpgames.ichigu.model.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.IViewFinder;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ITutorialListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.component.TutorialPage;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

class IchiguTutorial extends GameObject implements IViewFinder, ILanguageListener {
	private int pageIndex;

	private final ITutorialListener listener;
	private final Text pageTitle;
	private final Text pagesInfo;
	private final List<TutorialPage> pages;
	private final TouchSlidingViewSwitcher viewSwitcher;
	private final TextButton btnGoToPractice;

	IchiguTutorial(ITutorialListener listener) {
		this.listener = listener;
		this.pageTitle = new Text();
		this.pagesInfo = new Text();
		this.pages = new ArrayList<TutorialPage>();
		
		this.viewSwitcher = new TouchSlidingViewSwitcher(false);
		this.viewSwitcher.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight());
		this.viewSwitcher.setListener(new TouchSlidingViewSwitcher.IListener() {
			@Override
			public void onViewSwitched(IView newView, IView oldView) {
				afterViewSwitched(newView, oldView);
			}
		});

		this.btnGoToPractice = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		this.btnGoToPractice.deactivate();
		this.btnGoToPractice.setWidth(Game.getVirtualWidth());
		this.btnGoToPractice.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyTutorialEnd();
			}
		});


		setPageTitle();
		setPagesInfo();
		populatePages();

		updatePageInfoText();

		Game.getLanguageManager().register(this);
	}

	public void activate() {
		pageIndex = 0;
		updatePageInfoText();
		viewSwitcher.activate();
	}

	public void deactivate() {
		viewSwitcher.deactivate();
		btnGoToPractice.deactivate();
	}

	@Override
	public IView findView(String id) {
		for (TutorialPage page : pages) {
			if (id.equals(page.getId()))
				return page;
		}
		return null;
	}

	@Override
	public void onLanguageChanged() {
		populatePages();
		setPageTitle();
	}

	@Override
	public void draw() {
		pageTitle.draw();
		pagesInfo.draw();
		viewSwitcher.draw();
	}

	private void updatePageInfoText() {
		pagesInfo.setText((pageIndex + 1) + "/" + pages.size());
	}

	private void notifyTutorialEnd() {
		if (listener != null)
			listener.onModeEnd();
	}

	private void afterViewSwitched(IView newView, IView oldView) {
		int newViewIndex = -1;

		for (TutorialPage page : pages) {
			newViewIndex++;
			if (page == newView)
				break;
		}

		pageIndex = newViewIndex;
		
		if (pageIndex == pages.size() - 1)
			this.btnGoToPractice.activate();
		else
			this.btnGoToPractice.deactivate();
		
		updatePageInfoText();
	}

	private void setPagesInfo() {
		pagesInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pagesInfo.setPadding(0, 160f);
		pagesInfo.getColor().set(R.colors.ichiguCyan);
	}

	private void setPageTitle() {
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.setText(Ichigu.getString(R.strings.howToPlay));
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);
	}

	private void populatePages() {
		pages.clear();

		Text info;
		TutorialPage page;		
		float cardSpace, x1, x2, x3, y;
		
		page = new TutorialPage("1", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutOverviewTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText(Ichigu.getString(R.strings.tutOverview), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("2", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText(Ichigu.getString(R.strings.tutSymbols1), Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("3", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		info = page.addText(Ichigu.getString(R.strings.tutSymbols2), Text.HAlignCenter, 50);
		cardSpace = 50;
		x1 = (Game.getVirtualWidth() - (3 * R.sizes.cardWidth + 2 * cardSpace)) / 2;
		x2 = x1 + R.sizes.cardWidth + cardSpace;
		x3 = x2 + R.sizes.cardWidth + cardSpace;
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 150;
		page.addObject(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count1, CardAttributes.patternEmpty, x1, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeSquare, CardAttributes.count2, CardAttributes.patternStriped, x2, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeCircle, CardAttributes.count3, CardAttributes.patternFilled, x3, y));
		pages.add(page);

		page = new TutorialPage("4", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText(Ichigu.getString(R.strings.tutIchigu), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("5", R.fontSize.medium);
		info = page.addText(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeCircle, CardAttributes.count1, CardAttributes.patternEmpty, x1, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count1, CardAttributes.patternEmpty, x2, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeTriangle, CardAttributes.count1, CardAttributes.patternEmpty, x3, y));

		page.addText("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutSameColor), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("6", R.fontSize.medium);
		info = page.addText(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeTriangle, CardAttributes.count1, CardAttributes.patternFilled, x1, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeTriangle, CardAttributes.count2, CardAttributes.patternFilled, x2, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count3, CardAttributes.patternFilled, x3, y));

		page.addText("- " + Ichigu.getString(R.strings.tutSameShape), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("7", R.fontSize.medium);
		info = page.addText(Ichigu.getString(R.strings.tutSampleIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addObject(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeCircle, CardAttributes.count2, CardAttributes.patternEmpty, x1, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count1, CardAttributes.patternFilled, x2, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count3, CardAttributes.patternStriped, x3, y));

		page.addText("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutDifferentPatterns), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("8", R.fontSize.medium);
		info = page.addText(Ichigu.getString(R.strings.tutSampleNotIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count1, CardAttributes.patternFilled, x1, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorGreen, CardAttributes.shapeCircle, CardAttributes.count2, CardAttributes.patternFilled, x2, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeSquare, CardAttributes.count3, CardAttributes.patternFilled, x3, y));

		page.addText("!! " + String.format(Ichigu.getString(R.strings.tutTwoAndOneShape),
				Ichigu.getString(R.strings.square), Ichigu.getString(R.strings.circle))
				, Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutDifferentColors), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutDifferentCounts), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("9", R.fontSize.medium);
		info = page.addText(Ichigu.getString(R.strings.tutSampleNotIchiguTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - R.sizes.cardHeight - 50;
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeSquare, CardAttributes.count2, CardAttributes.patternEmpty, x1, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorBlue, CardAttributes.shapeCircle, CardAttributes.count2, CardAttributes.patternEmpty, x2, y));
		page.addObject(Card.createTutorialCard(CardAttributes.colorRed, CardAttributes.shapeTriangle, CardAttributes.count2, CardAttributes.patternEmpty, x3, y));

		page.addText("- " + Ichigu.getString(R.strings.tutDifferentShapes), Text.HAlignLeft, R.sizes.cardHeight + 50 + 30).setPadX(50);
		page.addText("!! " + String.format(Ichigu.getString(R.strings.tutTwoAndOneColor),
				Ichigu.getString(R.strings.blue), Ichigu.getString(R.strings.red))
				, Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutSamePattern), Text.HAlignLeft, 20).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutSameCount), Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("10", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutGameModesTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText("- " + Ichigu.getString(R.strings.tutSingleIchiguModes), Text.HAlignLeft, 50).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutPracticeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addText("- " + Ichigu.getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addText("- " + Ichigu.getString(R.strings.tutFullGameModes), Text.HAlignLeft, 50).setPadX(50);
		page.addText("- " + Ichigu.getString(R.strings.tutNormalModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addText("- " + Ichigu.getString(R.strings.tutFullChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		pages.add(page);

		page = new TutorialPage("11", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutPracticeModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText(Ichigu.getString(R.strings.tutPracticeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("12", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText(Ichigu.getString(R.strings.tutMiniChallengeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("13", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutFullModesTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText(Ichigu.getString(R.strings.tutFullModes), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("14", R.fontSize.medium);
		page.addText(Ichigu.getString(R.strings.tutNormalModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop).getColor().set(R.colors.ichiguYellow);
		page.addText(Ichigu.getString(R.strings.tutNormalMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("15", R.fontSize.medium);
		info = page.addText(Ichigu.getString(R.strings.tutFullChallengeModeTitle), Text.HAlignCenter, R.sizes.tutorialMarginTop);
		info.getColor().set(R.colors.ichiguYellow);
		y = Game.getVirtualHeight() - R.sizes.tutorialMarginTop - info.getTextAreaHeight() - 120;
		info = page.addText(Ichigu.getString(R.strings.tutFullChallengeMode), Text.HAlignLeft, 30);
		y -= info.getTextAreaHeight();
		info.setPadX(10);
		this.btnGoToPractice.setText(Ichigu.getString(R.strings.tutPracticeModeTitle));
		this.btnGoToPractice.getLocation().set((Game.getVirtualWidth() - this.btnGoToPractice.getWidth()) / 2, y);
		page.addObject(btnGoToPractice);
		pages.add(page);
		
		this.viewSwitcher.clearViews();
		for (TutorialPage tp : pages)
			this.viewSwitcher.addView(tp);
	}
}
