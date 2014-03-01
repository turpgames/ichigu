package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.utils.R;

public class LeadersBoardButtons implements IDrawable {
	public static interface IListener {
		void onLeadersBoardModeChange();
	}

	private Combobox modeOptions;
	private Combobox whoseOptions;
	private Combobox dayOptions;

	private int mode;
	private int days;
	private int whose;

	private float y;

	private IListener listener;

	public LeadersBoardButtons() {		
		modeOptions = new Combobox();
		modeOptions.addOption(R.strings.timeChallengeMode, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(Score.ModeTime, days, whose);
			}
		});
		modeOptions.addOption(R.strings.standardMode, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(Score.ModeStandard, days, whose);
			}
		});
		modeOptions.addOption(R.strings.miniChallengeMode, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(Score.ModeMini, days, whose);
			}
		});
		

		whoseOptions = new Combobox();
		whoseOptions.addOption(R.strings.general, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(mode, days, Score.General);
			}
		});
		whoseOptions.addOption(R.strings.friends, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(mode, days, Score.FriendsScores);
			}
		});
		whoseOptions.addOption(R.strings.myScores, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(mode, days, Score.MyScores);
			}
		});

		dayOptions = new Combobox();
		dayOptions.addOption(R.strings.allTime, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(mode, Score.AllTime, whose);
			}
		});
		dayOptions.addOption(R.strings.monthly, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(mode, Score.Monthly, whose);
			}
		});
		dayOptions.addOption(R.strings.weekly, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(mode, Score.Weekly, whose);
			}
		});
		dayOptions.addOption(R.strings.daily, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				notifyChange(mode, Score.Daily, whose);
			}
		});
		
		mode = Score.ModeTime;
		days = Score.AllTime;
		whose = Score.General;
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
			setButtonLocations();
			listener.onLeadersBoardModeChange();
		}
	}

	public int getMode() {
		return mode;
	}

	public int getDays() {
		return days;
	}

	public int getWhose() {
		return whose;
	}
	
	public void activate() {
		modeOptions.activate();
		whoseOptions.activate();
		dayOptions.activate();
		
		setButtonLocations();
	}
	
	public void deactivate() {
		modeOptions.deactivate();
		whoseOptions.deactivate();
		dayOptions.deactivate();
	}
	
	@Override
	public void draw() {
		modeOptions.draw();
		whoseOptions.draw();
		dayOptions.draw();
	}

	private void setButtonLocations() {
		modeOptions.setLocation(60, y + 80);
		whoseOptions.setLocation((Game.getVirtualWidth() - whoseOptions.getWidth()) / 2, y + 40);
		dayOptions.setLocation(Game.getVirtualWidth() - dayOptions.getWidth() - 60, y + 80);
	}
}
