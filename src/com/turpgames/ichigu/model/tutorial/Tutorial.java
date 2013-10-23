package com.turpgames.ichigu.model.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.IViewFinder;
import com.turpgames.framework.v0.IViewSwitcher;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.impl.ViewSwitcher;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.IchiguObject;
import com.turpgames.ichigu.model.display.IIchiguButtonListener;
import com.turpgames.ichigu.model.display.IchiguImageButton;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.GameInfo;
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
		page.addInfo("Overview", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Ichigu is a card game played with a deck of 81 cards.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("Each card in deck is unique and has some symbols on it.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("Objective of the game is to find groups of three cards: ichigus.", Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);

		page = new TutorialPage("2");
		page.addInfo("Symbols", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Symbols on the cards have four properties and each of them have three different values", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("- Shape: Triangle, Circle, Square", Text.HAlignLeft, 30).setPadX(30);
		page.addInfo("- Color: Red, Green, Blue", Text.HAlignLeft, 20).setPadX(30);
		page.addInfo("- Pattern: Empty, Striped, Filled", Text.HAlignLeft, 20).setPadX(30);
		page.addInfo("- Count: One, Two, Three", Text.HAlignLeft, 20).setPadX(30);
		page.addInfo("This totals to 81 unique cards in a deck.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("3 x 3 x 3 x 3 = 81", Text.HAlignCenter, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("3");
		page.addInfo("Symbols", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		Text info = page.addInfo("Three different shapes, colors, patterns and counts", Text.HAlignCenter, 50);
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
		page.addInfo("Ichigu", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("To make an ichigu all 3 cards must have", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("- Same or different shapes", Text.HAlignLeft, 30).setPadX(50);
		page.addInfo("- Same or different colors", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same or different patterns", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("- Same or different counts", Text.HAlignLeft, 20).setPadX(50);
		page.addInfo("In other words, it is NOT an ichigu if you can seperate three cards into:", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("Two ...s and one ...", Text.HAlignCenter, 30);
		pages.add(page);

		page = new TutorialPage("5");
		info = page.addInfo("Sample Ichigu", Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
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
		info = page.addInfo("Sample Ichigu", Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
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
		info = page.addInfo("Sample Ichigu", Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
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
		info = page.addInfo("Sample Ichigu", Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
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
		info = page.addInfo("Sample Ichigu", Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
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
		page.addInfo("Game Modes", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("- Single Ichigu Modes", Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- Practice Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Mini Challenge Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Full Game Modes", Text.HAlignLeft, 50).setPadX(50);
		page.addInfo("- Relax Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Normal Mode", Text.HAlignLeft, 30).setPadX(100);
		page.addInfo("- Full Challenge Mode", Text.HAlignLeft, 30).setPadX(100);
//		page.addInfo("- Multiplayer Mode", Text.HAlignLeft, 30).setPadX(100);
		pages.add(page);

		page = new TutorialPage("11");
		page.addInfo("Practice Mode", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("At the end of this tutorial you will find the Practice Mode game", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("In this mode, you will be given two cards and asked for the third card that makes an ichigu with the given two cards.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("Any time you need help, feel free to tap the tip button at the bottom right.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("12");
		page.addInfo("Mini Challenge Mode", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Mini Challenge Mode is played the same as Practice Mode.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("This time you have 1 minutes to find as many ichigus as you can. No tips.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("If you choose the wrong card, you will have to wait for 2 seconds to try again.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("13");
		page.addInfo("Relax Mode", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Relax Mode is the basic full-game mode. You will be given 12 open and 3 closed cards.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("You are expected to find an ichigu in 12 cards. If you need, you can open the 3 extra cards and look for an ichigu in 15 cards.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("Tips are available and game can be reset", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("14");
		page.addInfo("Normal Mode", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Normal Mode is set up exactly as Relax Mode but score and time is kept.", Text.HAlignLeft, 50).setPadX(10);
		pages.add(page);
		
		page = new TutorialPage("15");
		page.addInfo("Challenge Mode", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Challenge Mode is the challenging version of the Normal Mode.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("You have 5 minutes to find as many ichigus as you can.", Text.HAlignLeft, 30).setPadX(10);
		page.addInfo("No tips, no pause, no reset: pure challenge!", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

//		page = new TutorialPage("15");
//		page.addInfo("Multiplayer Mode", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
//		page.addInfo("Multiplayer Mode will be available soon where you can play a Full Game with other players online.", Text.HAlignLeft, 50).setPadX(10);
//		page.addInfo("Multiplayer Mode can be played with 2, 3 or 4 players at the same time.", Text.HAlignLeft, 30).setPadX(10);
//		pages.add(page);

		page = new TutorialPage("16");
		page.addInfo("Score", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Points are calculated according to the complexity of found ichigu.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("You get 1 point for a common property of ichigu cards and 3 points for different properties.", Text.HAlignLeft, 30).setPadX(10);
		pages.add(page);

		page = new TutorialPage("17");
		page.addInfo("Score", Text.HAlignCenter, marginTop).getColor().set(R.colors.ichiguYellow);
		page.addInfo("Taking hints in Normal Mode and Challenge Mode decreases score.", Text.HAlignLeft, 50).setPadX(10);
		page.addInfo("In Challenge Mode, if you find an ichigu after opening extra cards you get half of the normal points.", Text.HAlignLeft, 25).setPadX(10);
		page.addInfo("Your score won't be deducted if there were no ichigus in the 12 open cards before opening the extra cards.", Text.HAlignLeft, 25).setPadX(10);
		pages.add(page);

		page = new TutorialPage("18");
		info = page.addInfo("Ichigu Score", Text.HAlignCenter, marginTop);
		info.getColor().set(R.colors.ichiguYellow);
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
}
