package com.blox.ichigu.model;

import java.util.ArrayList;
import java.util.List;

import com.blox.framework.v0.IDrawingInfo;
import com.blox.framework.v0.ITexture;
import com.blox.framework.v0.IView;
import com.blox.framework.v0.IViewFinder;
import com.blox.framework.v0.IViewSwitcher;
import com.blox.framework.v0.impl.ViewSwitcher;
import com.blox.framework.v0.util.FontManager;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.TextDrawer;
import com.blox.framework.v0.util.Vector;
import com.blox.ichigu.utils.R;

class LearningModeTutorial extends IchiguObject implements IViewFinder {
//	private IFont font;
	private int pageIndex;
	private List<TutorialPage> pages;
	private IViewSwitcher switcher;

	private IchiguImageButton nextButton;
	private IchiguImageButton prevButton;
	private IchiguImageButton skipButton;

	private ILearningModeTutorialListener listener;

	LearningModeTutorial(ILearningModeTutorialListener listener) {
		this.listener = listener;

//		font = FontManager.createDefaultFontInstance();
//		font.setScale(R.fontSize.small);
		
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
	}

	private void populatePages() {
		pages = new ArrayList<TutorialPage>();

		TutorialPage cuePage = new TutorialPage("1", "Symbols on the cards have\nfour characteristics:\nshape, color, fill and\nnumber of symbols.");
		pages.add(cuePage);
		
		cuePage = new TutorialPage("2", "Three different shapes:");
		ITexture cueTexture = Game.getResourceManager().getTexture(R.game.textures.shapes);
		TutorialObject cueObj = new TutorialObject(cueTexture, R.symbolpositions.tutorial300x96, 300, 96);
		cueObj.getColor().set(R.colors.ichiguWhite);
		cuePage.addImage(cueObj);
		pages.add(cuePage);
		
		cuePage = new TutorialPage("3", "Three different colors:");
		cueTexture = Game.getResourceManager().getTexture(R.game.textures.colors);
		cueObj = new TutorialObject(cueTexture, R.symbolpositions.tutorial300x96, 300, 96);
		cueObj.getColor().set(R.colors.ichiguWhite);
		cuePage.addImage(cueObj);
		pages.add(cuePage);
		
		cuePage = new TutorialPage("4", "Three different\nfill types:");
		cueTexture = Game.getResourceManager().getTexture(R.game.textures.fills);
		cueObj = new TutorialObject(cueTexture, R.symbolpositions.tutorial300x96, 300, 96);
		cueObj.getColor().set(R.colors.ichiguWhite);
		cuePage.addImage(cueObj);
		pages.add(cuePage);
		
		cuePage = new TutorialPage("5", "Three different\nnumber of symbols:");
		cueTexture = Game.getResourceManager().getTexture(R.game.textures.amounts);
		cueObj = new TutorialObject(cueTexture, R.symbolpositions.tutorial350x300, 350, 300);
		cueObj.getColor().set(R.colors.ichiguWhite);
		cuePage.addImage(cueObj);
		pages.add(cuePage);
		
		cuePage = new TutorialPage("6", "The objective is to find\ngroups of three cards\nwhere each of these four\ncharacteristics is either\nsame or different\nfor all cards.");
		pages.add(cuePage);
		
		cuePage = new TutorialPage("7", "Example ichigus:");
		Card cueCard1 = new Card(new CardAttributes(CardAttributes.Color_Blue, CardAttributes.Shape_Circle, CardAttributes.Count_1, CardAttributes.Pattern_Empty));
		Card cueCard2 = new Card(new CardAttributes(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Empty));
		Card cueCard3 = new Card(new CardAttributes(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Empty));
		cueCard1.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 1.5f - 40, 550));
		cueCard2.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 0.5f, 550));
		cueCard3.getLocation().set(new Vector(Game.getVirtualWidth() / 2 + Card.Width * 0.5f + 40, 550));
		cueCard1.open();
		cueCard2.open();
		cueCard3.open();
		cuePage.addImage(cueCard1);
		cuePage.addImage(cueCard2);
		cuePage.addImage(cueCard3);
		
		cueCard1 = new Card(new CardAttributes(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped));
		cueCard2 = new Card(new CardAttributes(CardAttributes.Color_Green, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped));
		cueCard3 = new Card(new CardAttributes(CardAttributes.Color_Red, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Striped));
		cueCard1.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 1.5f - 40, 550 - Card.Height - 20));
		cueCard2.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 0.5f, 550 - Card.Height - 20));
		cueCard3.getLocation().set(new Vector(Game.getVirtualWidth() / 2 + Card.Width * 0.5f + 40, 550 - Card.Height - 20));
		cueCard1.open();
		cueCard2.open();
		cueCard3.open();
		cuePage.addImage(cueCard1);
		cuePage.addImage(cueCard2);
		cuePage.addImage(cueCard3);
		pages.add(cuePage);
		
		cuePage = new TutorialPage("8", "Example ichigus:");
		cueCard1 = new Card(new CardAttributes(CardAttributes.Color_Blue, CardAttributes.Shape_Triangle, CardAttributes.Count_1, CardAttributes.Pattern_Filled));
		cueCard2 = new Card(new CardAttributes(CardAttributes.Color_Green, CardAttributes.Shape_Triangle, CardAttributes.Count_2, CardAttributes.Pattern_Filled));
		cueCard3 = new Card(new CardAttributes(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Filled));
		cueCard1.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 1.5f - 40, 550));
		cueCard2.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 0.5f, 550));
		cueCard3.getLocation().set(new Vector(Game.getVirtualWidth() / 2 + Card.Width * 0.5f + 40, 550));
		cueCard1.open();
		cueCard2.open();
		cueCard3.open();
		cuePage.addImage(cueCard1);
		cuePage.addImage(cueCard2);
		cuePage.addImage(cueCard3);
		
		cueCard1 = new Card(new CardAttributes(CardAttributes.Color_Green, CardAttributes.Shape_Circle, CardAttributes.Count_2, CardAttributes.Pattern_Empty));
		cueCard2 = new Card(new CardAttributes(CardAttributes.Color_Blue, CardAttributes.Shape_Square, CardAttributes.Count_1, CardAttributes.Pattern_Filled));
		cueCard3 = new Card(new CardAttributes(CardAttributes.Color_Red, CardAttributes.Shape_Triangle, CardAttributes.Count_3, CardAttributes.Pattern_Striped));
		cueCard1.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 1.5f - 40, 550 - Card.Height - 20));
		cueCard2.getLocation().set(new Vector(Game.getVirtualWidth() / 2 - Card.Width * 0.5f, 550 - Card.Height - 20));
		cueCard3.getLocation().set(new Vector(Game.getVirtualWidth() / 2 + Card.Width * 0.5f + 40, 550 - Card.Height - 20));
		cueCard1.open();
		cueCard2.open();
		cueCard3.open();
		cuePage.addImage(cueCard1);
		cuePage.addImage(cueCard2);
		cuePage.addImage(cueCard3);
		pages.add(cuePage);
	}

	void start() {
		pageIndex = 0;
		switcher.switchTo(pages.get(pageIndex).getId(), false);
		prevButton.deactivate();
		nextButton.activate();
		skipButton.activate();		
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
		Game.pushRenderingShift(0, -100, false);
		TextDrawer.draw(FontManager.defaultFont, (pageIndex + 1) + "/" + pages.size(), IDrawingInfo.viewport, TextDrawer.AlignN);
		Game.popRenderingShift();
		
		Game.pushRenderingShift(0, -175, false);
		if (switcher.isSwitching())
			switcher.render();
		else
			pages.get(pageIndex).render();
		Game.popRenderingShift();
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
