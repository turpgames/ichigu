package com.blox.ichigu.model;

import java.util.ArrayList;
import java.util.List;

import com.blox.framework.v0.IView;
import com.blox.framework.v0.IViewFinder;
import com.blox.framework.v0.IViewSwitcher;
import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.impl.ViewSwitcher;
import com.blox.framework.v0.util.Game;
import com.blox.ichigu.utils.R;

class LearningModeTutorial extends IchiguObject implements IViewFinder {
	private final static float marginTop = 160f;

	private int pageIndex;
	private List<TutorialPage> pages;
	private IViewSwitcher switcher;

	private IchiguImageButton nextButton;
	private IchiguImageButton prevButton;
	private IchiguImageButton skipButton;

	private ILearningModeTutorialListener listener;

	private GameInfo pageInfo;

	LearningModeTutorial(ILearningModeTutorialListener listener) {
		this.listener = listener;

		pageInfo = new GameInfo();
		pageInfo.locate(Text.HAlignCenter, Text.VAlignTop);
		pageInfo.setPadding(0, 100f);

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
		page.addInfo("Ichigu is a card game played with a deck of 81 cards.", Text.HAlignLeft, marginTop).setPadX(10);
		page.addInfo("Each card in deck is unique and has some symbols on it.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("Objective of the game is to find groups of three cards, which is called an ichigu, from the deck as fast as possible.", Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("2");
		page.addInfo("Symbols on the cards have four properties and each of them have three different values", Text.HAlignLeft, marginTop).setPadX(10);
		page.addInfo("- Shape: Triangle, Circle, Square", Text.HAlignLeft, 30).setPadX(30);
		page.addInfo("- Color: Red, Green, Blue", Text.HAlignLeft, 20).setPadX(30);
		page.addInfo("- Pattern: Empty, Striped, Filled", Text.HAlignLeft, 20).setPadX(30);
		page.addInfo("- Count: One, Two, Three", Text.HAlignLeft, 20).setPadX(30);
		page.addInfo("That's why there are 81 unique cards in the deck.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("3 x 3 x 3 x 3 = 81", Text.HAlignCenter, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("3");
		Text info = page.addInfo("Three different shapes, colors, patterns and counts", Text.HAlignCenter, marginTop);
		float cardSpace = 50;
		float x1 = (Game.getVirtualWidth() - (3 * Card.Width + 2 * cardSpace)) / 2;
		float x2 = x1 + Card.Width + cardSpace;
		float x3 = x2 + Card.Width + cardSpace;
		float y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 100;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Square, CardAttributes.Count_2, CardAttributes.Pattern_Striped, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Circle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));
		pages.add(page);

		page = new TutorialPage("4");
		page.addInfo("To make an ichigu all 3 cards must have", Text.HAlignLeft, 175f).setPadX(10);
		page.addInfo("- Same or different shapes", Text.HAlignLeft, 30).setPadX(50);
		page.addInfo("- Same or different colors", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same or different patterns", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same or different counts", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("In other words, it is NOT an ichigu if you can seperate three cards into:", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("Two ...s and one ...", Text.HAlignCenter, 30);
		pages.add(page);

		page = new TutorialPage("5");
		info = page.addInfo("Sample ichigu:", Text.HAlignCenter, marginTop);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Circle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x3, y));

		page.addInfo("- Different shapes", Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- Same color", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same pattern", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same count", Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("6");
		info = page.addInfo("Sample ichigu:", Text.HAlignCenter, marginTop);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped, x3, y));

		page.addInfo("- Same shape", Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- Different colors", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same pattern", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same count", Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("7");
		info = page.addInfo("Sample ichigu:", Text.HAlignCenter, marginTop);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_2, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("- Same shape", Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- Different colors", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same pattern", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Different counts", Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("8");
		info = page.addInfo("Sample ichigu:", Text.HAlignCenter, marginTop);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Circle, CardAttributes.Count_1, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Square, CardAttributes.Count_2, CardAttributes.Pattern_Striped, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("- Different shapes", Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- Same color", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Different patterns", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Different counts", Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("9");
		info = page.addInfo("Sample ichigu:", Text.HAlignCenter, marginTop);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 50;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Circle, CardAttributes.Count_2, CardAttributes.Pattern_Empty, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Striped, x3, y));

		page.addInfo("- Different shapes", Text.HAlignLeft, Card.Height + 50 + 30).setPadX(50);
		page.addInfo("- Different colors", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Different patterns", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Different counts", Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);

		page = new TutorialPage("10");
		page.addInfo("Game Modes", Text.HAlignCenter, marginTop);
		page.addInfo("- Training Modes", Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- Learning Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Practice Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Full Game Modes", Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- Relax Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Challenge Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Multiplayer Mode", Text.HAlignLeft, 30).setPadX(100);
		pages.add(page);

		page = new TutorialPage("11");
		page.addInfo("Learning Mode", Text.HAlignCenter, marginTop);
		page.addInfo("At the end of this tutorial you will find the Learning Mode game", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("In this mode, you will be given two cards and asked for the third card that makes an ichigu with the given two cards.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("Any time you need help feel free to tap tip button at the bottom of screen.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("12");
		page.addInfo("Practice Mode", Text.HAlignCenter, marginTop);
		page.addInfo("Practice Mode is basically same as Learning Mode but it is a little bit more challenging.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("This time you have to be fast and there won't be any tips.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("You will have 5 seconds to find the third card and if you make a mistake you have to wait for 2 seconds to give another answer.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("13");
		page.addInfo("Relax Mode", Text.HAlignCenter, marginTop);
		page.addInfo("Relax Mode is the basic Full Game Mode. You will be given 12 opened and 3 closed extra cards.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("You are expected to find an ichigu in 12 cards. If you need, you can open extra cards and look for an ichigu in 15 cards.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("Again you will have a tip button and any time you wish you can reset or pause game.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("14");
		page.addInfo("Challenge Mode", Text.HAlignCenter, marginTop);
		page.addInfo("Challenge Mode is the challenging version of the Relax Mode.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("There won't be a tip button and once you start you have to finish game. No pause, no reset.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("You will get more score as you find faster and more complex ichigus.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("15");
		page.addInfo("Multiplayer Mode", Text.HAlignCenter, marginTop);
		page.addInfo("Multiplayer Mode will be available soon where you can play a Full Game with other players online.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("Multiplayer Mode can be played with 2, 3 or 4 players at the same time.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("16");
		page.addInfo("Scores", Text.HAlignCenter, marginTop);
		page.addInfo("Scores are calculated over two basic parameters:", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("- How complex was the ichigu?", Text.HAlignLeft, 30).setPadX(20);
		page.addInfo("- How fast did you find the ichigu?", Text.HAlignLeft, 30).setPadX(20);
		page.addInfo("You get 1 point for a common property of ichigu cards and 3 point for different properties.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("Sum of property points is multiplied by a factor calculated over time.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("17");
		info = page.addInfo("Ichigu Score", Text.HAlignCenter, marginTop);
		y = Game.getVirtualHeight() - marginTop - info.getTextAreaHeight() - Card.Height - 30;
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Filled, x1, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_2, CardAttributes.Pattern_Filled, x2, y));
		page.addImage(Card.createTutorialCard(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled, x3, y));

		page.addInfo("- Same shape: 1 Point", Text.HAlignLeft, Card.Height + 30 + 30).setPadX(50);
		page.addInfo("- Different colors: 3 Points", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same pattern: 1 Point", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Different counts: 3 Points", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Total: 8 Points", Text.HAlignLeft, 20).setPadX(50);
		pages.add(page);
	}

	void start() {
		pageIndex = 0;
		switcher.switchTo(pages.get(pageIndex).getId(), false);
		prevButton.deactivate();
		nextButton.activate();
		skipButton.activate();
		updatePageInfoText();
	}

	void end() {
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

		if (switcher.isSwitching())
			switcher.render();
		else
			pages.get(pageIndex).render();
	}

	@Override
	public IView findView(String id) {
		for (TutorialPage page : pages) {
			if (id.equals(page.getId()))
				return page;
		}
		return null;
	}
}
