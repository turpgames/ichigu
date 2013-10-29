package com.turpgames.ichigu.model.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.IViewFinder;
import com.turpgames.framework.v0.IViewSwitcher;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.impl.ViewSwitcher;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.IchiguObject;
import com.turpgames.ichigu.model.display.IIchiguButtonListener;
import com.turpgames.ichigu.model.display.IchiguImageButton;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.info.GameInfo;
import com.turpgames.ichigu.utils.R;

public class Tutorial extends IchiguObject implements IViewFinder {
	private final static float marginTop = 160f;

	private int pageIndex;
	private List<TutorialPage> pages;
	private IViewSwitcher switcher;

	private IchiguImageButton nextButton;
	private IchiguImageButton prevButton;
	private IchiguImageButton skipButton;

	private ITutorialModeListener listener;

	private GameInfo pageInfo;

	public Tutorial(ITutorialModeListener listener) {
		this.listener = listener;

		pageInfo = new GameInfo();
		pageInfo.locate(Text.HAlignCenter, Text.VAlignTop);
		pageInfo.setPadding(0, 100f);
		pageInfo.setColor(R.colors.ichiguCyan);

		String pageSwitcher = Game.getParam("tutorial-page-switcher");
		switcher = ViewSwitcher.createInstance(pageSwitcher);
		switcher.setViewFinder(this);

		populatePages();

		nextButton = new IchiguImageButton();
		nextButton.setTexture(R.game.textures.next);
		nextButton.getLocation().set(Game.getVirtualWidth() - (nextButton.getWidth() + 10), 100);
		nextButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				next();
			}
		});

		prevButton = new IchiguImageButton();
		prevButton.setTexture(R.game.textures.prev);
		prevButton.deactivate();
		prevButton.getLocation().set(10, 100);
		prevButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				prev();
			}
		});

		skipButton = new IchiguImageButton();
		skipButton.setTexture(R.game.textures.skip);
		skipButton.getLocation().set((Game.getVirtualWidth() - skipButton.getWidth()) / 2, 50);
		skipButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				skip();
			}
		});

		updatePageInfoText();
	}

	private void populatePages() {
		pages = new ArrayList<TutorialPage>();

		TutorialPage page;

		page = new TutorialPage("1");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutOverviewTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutOverview), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("2");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutSymbolsTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutSymbols1), Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("3");
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

		page = new TutorialPage("4");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutIchiguTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutIchigu), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("5");
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

		page = new TutorialPage("6");
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

		page = new TutorialPage("7");
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

		page = new TutorialPage("8");
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

		page = new TutorialPage("9");
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

		page = new TutorialPage("10");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutGameModesTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutSingleIchiguModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutPracticeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutFullGameModes), Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutRelaxModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutNormalModeTitle), Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- " + Game.getLanguageManager().getString(R.strings.tutFullChallengeModeTitle), Text.HAlignLeft, 30).setPadX(100);
//		page.addInfo("- Multiplayer Mode", Text.HAlignLeft, 30).setPadX(100);
		pages.add(page);

		page = new TutorialPage("11");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutPracticeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutPracticeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("12");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutMiniChallengeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutMiniChallengeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("13");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutRelaxModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutRelaxMode), Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("14");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutNormalModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutNormalMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("15");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutFullChallengeModeTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutFullChallengeMode), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

//		page = new TutorialPage("15");
//		page.addInfo("Multiplayer Mode", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
//		page.addInfo("Multiplayer Mode will be available soon where you can play a Full Game with other players online.", Text.HAlignLeft, 50).setPadX(10);
//		page.addInfo("Multiplayer Mode can be played with 2, 3 or 4 players at the same time.", Text.HAlignLeft, 30).setPadX(10);
//		pages.add(page);

		page = new TutorialPage("16");
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutScoreTitle), Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo(Game.getLanguageManager().getString(R.strings.tutScore), Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

//		page = new TutorialPage("17");
//		page.addInfo("Score", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
//		page.addInfo("Taking hints in Normal Mode and Challenge Mode decreases score.", Text.HAlignLeft, 50).setPadX(10);
//		page.addInfo("In Challenge Mode, if you find an ichigu after opening extra cards you get half of the normal points.", Text.HAlignLeft, 25).setPadX(10);
//		page.addInfo("Your score won't be deducted if there were no ichigus in the 12 open cards before opening the extra cards.", Text.HAlignLeft, 25).setPadX(10);
//		pages.add(page);

		page = new TutorialPage("18");
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

	public void start() {
		pageIndex = 0;
		switcher.switchTo(pages.get(pageIndex).getId(), false);
		prevButton.deactivate();
		nextButton.activate();
		skipButton.activate();
		updatePageInfoText();
	}

	public void end() {
		prevButton.deactivate();
		nextButton.deactivate();
		skipButton.deactivate();
	}

	private void next() {
		if (pageIndex < pages.size() - 1) {
			pageIndex++;
			switcher.switchTo(pages.get(pageIndex).getId(), false);
		}
		else
			notifyTutorialEnd();
		prevButton.activate();
		updatePageInfoText();
	}

	private void prev() {
		if (pageIndex > 0) {
			pageIndex--;
			switcher.switchTo(pages.get(pageIndex).getId(), true);
		}
		if (pageIndex > 0) {
			prevButton.activate();
		}
		else {
			prevButton.deactivate();
		}
		updatePageInfoText();
	}

	private void updatePageInfoText() {
		pageInfo.setText((pageIndex + 1) + "/" + pages.size());
	}

	private void skip() {
		notifyTutorialEnd();
	}

	private void notifyTutorialEnd() {
		if (listener != null)
			listener.onTutorialEnd();
	}

	@Override
	public void draw() {
		drawPage();
		nextButton.draw();
		prevButton.draw();
		skipButton.draw();
	}

	private void drawPage() {
		pageInfo.draw();
		switcher.draw();
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
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_SCREEN);
	}
}
