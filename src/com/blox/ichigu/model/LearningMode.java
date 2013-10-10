package com.blox.ichigu.model;

public class LearningMode extends TrainingMode {
	private LearningModeHint hint;
	private LearningModeTutorial tutorial;

	public LearningMode() {
		hint = new LearningModeHint();
		tutorial = new LearningModeTutorial(new ILearningModeTutorialListener() {
			@Override
			public void onTutorialEnd() {
				notifyTutorialEnd();
			}
		});
	}

	private ILearningModeListener getModeListener() {
		return (ILearningModeListener)modeListener;
	}

	private void notifyTutorialEnd() {
		if (getModeListener() != null)
			getModeListener().onTutorialEnd();
	}

	@Override
	public void activateCardsOnTable() {
		super.activateCardsOnTable();

		Card[] allCards = cards.getAllCards();

		Card card3 = null;
		for (int i = 0; i < TrainingCards.CardToSelectCount; i++) {
			if (Card.isIchigu(allCards[0], allCards[1], cards.getCardsToSelect(i))) {
				card3 = cards.getCardsToSelect(i);
				break;
			}
		}

		hint.update(allCards[0], allCards[1], card3);
		hint.listenInput(true);
	}

	@Override
	public void deactivateCards() {
		hint.listenInput(false);
		super.deactivateCards();
	}

	public void drawTutorial() {
		tutorial.draw();
	}

	public void drawGame() {
		drawCards();
		hint.draw();
	}

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			allCards[i].draw();
	}

	public void endTutorial() {
		tutorial.end();
	}

	public void beginTutorial() {
		hint.listenInput(false);
		tutorial.start();
	}
}
