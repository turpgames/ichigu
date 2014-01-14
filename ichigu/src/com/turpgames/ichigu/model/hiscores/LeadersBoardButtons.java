package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.social.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;
import com.turpgames.utils.Util;

public class LeadersBoardButtons implements IDrawable, ILanguageListener {
	public static interface IListener {
		void onLeadersBoardModeChange();
	}

	private TextButton modeMini;
	private TextButton modeStandard;
	private TextButton modeTimeChallenge;

	private TextButton myScores;
	private TextButton generalScores;
	private TextButton friendsScores;

	private TextButton dailyScores;
	private TextButton weeklyScores;
	private TextButton monhtlyScores;
	private TextButton allTimeScores;

	private int mode;
	private int days;
	private int whose;

	private float y;

	private IListener listener;

	public LeadersBoardButtons() {
		modeMini = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(modeMini, true);
				setActive(modeStandard, false);
				setActive(modeTimeChallenge, false);

				notifyChange(Score.ModeMini, days, whose);
			}
		});

		modeStandard = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(modeMini, false);
				setActive(modeStandard, true);
				setActive(modeTimeChallenge, false);

				notifyChange(Score.ModeStandard, days, whose);
			}
		});

		modeTimeChallenge = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(modeMini, false);
				setActive(modeStandard, false);
				setActive(modeTimeChallenge, true);

				notifyChange(Score.ModeTime, days, whose);
			}
		});

		myScores = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(myScores, true);
				setActive(friendsScores, false);
				setActive(generalScores, false);

				notifyChange(mode, days, Score.MyScores);
			}
		});

		generalScores = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(myScores, false);
				setActive(friendsScores, false);
				setActive(generalScores, true);

				notifyChange(mode, days, Score.General);
			}
		});

		friendsScores = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(myScores, false);
				setActive(friendsScores, true);
				setActive(generalScores, false);

				notifyChange(mode, days, Score.FriendsScores);
			}
		});

		dailyScores = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(dailyScores, true);
				setActive(weeklyScores, false);
				setActive(monhtlyScores, false);
				setActive(allTimeScores, false);

				notifyChange(mode, 1, whose);
			}
		});

		weeklyScores = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(dailyScores, false);
				setActive(weeklyScores, true);
				setActive(monhtlyScores, false);
				setActive(allTimeScores, false);

				notifyChange(mode, 7, whose);
			}
		});

		monhtlyScores = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(dailyScores, false);
				setActive(weeklyScores, false);
				setActive(monhtlyScores, true);
				setActive(allTimeScores, false);

				notifyChange(mode, 30, whose);
			}
		});

		allTimeScores = createTextButton(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setActive(dailyScores, false);
				setActive(weeklyScores, false);
				setActive(monhtlyScores, false);
				setActive(allTimeScores, true);

				notifyChange(mode, -1, whose);
			}
		});

		// default: timechallenge weekly general
		setActive(modeTimeChallenge, true);
		setActive(weeklyScores, true);
		setActive(generalScores, true);
		setActive(modeMini, false);
		setActive(modeStandard, false);
		setActive(dailyScores, false);
		setActive(monhtlyScores, false);
		setActive(allTimeScores, false);
		setActive(myScores, false);
		setActive(friendsScores, false);

		setLaguageSensitiveInfo();
	}

	private static TextButton createTextButton(IButtonListener listener) {
		TextButton btn = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		btn.setLocation(Button.AlignSW, 0, 0);
		btn.setFontScale(R.fontSize.small);
		btn.setListener(listener);
		return btn;
	}

	public void setListener(IListener listener) {
		this.listener = listener;
	}

	public void setY(float y) {
		this.y = y;
		setButtonLocations();
	}

	private void notifyChange(int mode, int days, int whose) {
		if (this.mode != mode || this.days != days || this.whose != whose) {
			this.mode = mode;
			this.days = days;
			this.whose = whose;
			listener.onLeadersBoardModeChange();
		}
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getWhose() {
		return whose;
	}

	public void setWhose(int whose) {
		this.whose = whose;
	}

	private static void setActive(TextButton btn, boolean isActive) {
		if (isActive) {
			btn.getColor().a = 1f;
		} else {
			btn.getColor().a = 0.5f;
		}
	}

	@Override
	public void draw() {
		modeMini.draw();
		modeStandard.draw();
		modeTimeChallenge.draw();

		myScores.draw();
		generalScores.draw();
		friendsScores.draw();

		dailyScores.draw();
		weeklyScores.draw();
		monhtlyScores.draw();
		allTimeScores.draw();
	}

	@Override
	public void onLanguageChanged() {
		setLaguageSensitiveInfo();
	}

	private void setLaguageSensitiveInfo() {
		modeMini.setText(Ichigu.getString(R.strings.miniChallengeMode));
		modeStandard.setText(Ichigu.getString(R.strings.standardMode));
		modeTimeChallenge.setText(Ichigu.getString(R.strings.timeChallengeMode));

		myScores.setText(Ichigu.getString(R.strings.myScores));
		generalScores.setText(Ichigu.getString(R.strings.general));
		friendsScores.setText(Ichigu.getString(R.strings.friends));

		dailyScores.setText(Ichigu.getString(R.strings.daily));
		weeklyScores.setText(Ichigu.getString(R.strings.weekly));
		monhtlyScores.setText(Ichigu.getString(R.strings.monthly));
		allTimeScores.setText(Ichigu.getString(R.strings.allTime));

		setButtonLocations();
	}

	private void setButtonLocations() {
		modeMini.getLocation().set(20, y + 80);
		modeStandard.getLocation().set((Game.getVirtualWidth() - modeStandard.getWidth()) / 2, y + 80);
		modeTimeChallenge.getLocation().set((Game.getVirtualWidth() - modeTimeChallenge.getWidth() - 20), y + 80);

		myScores.getLocation().set(20, y + 40);
		generalScores.getLocation().set((Game.getVirtualWidth() - generalScores.getWidth()) / 2, y + 40);
		friendsScores.getLocation().set((Game.getVirtualWidth() - friendsScores.getWidth() - 20), y + 40);

		dailyScores.getLocation().set(50, y);
		weeklyScores.getLocation().set(150, y);
		monhtlyScores.getLocation().set(275, y);
		allTimeScores.getLocation().set(400, y);
	}
}
