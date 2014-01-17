package com.turpgames.ichigu.model.hiscores;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.social.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;
import com.turpgames.utils.Util;

class OnlineHiScores implements IHiScores {
	private Text pageTitle;
	private final HiScores parent;
	private ImageButton logoutOfFacebook;
	private volatile List<LeadersBoardRow> rows;
	private LeadersBoardButtons2 buttons;

	OnlineHiScores(HiScores parent) {
		this.parent = parent;

		pageTitle = new Text();
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);

		logoutOfFacebook = new ImageButton(R.sizes.loginWidth, R.sizes.loginHeight, R.game.textures.fb_logout);
		logoutOfFacebook.setLocation(Button.AlignS, 0, Game.viewportToScreenY(50));
		logoutOfFacebook.listenInput(false);
		logoutOfFacebook.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				logoutOfFacebook();
			}
		});

		rows = new ArrayList<LeadersBoardRow>();

		buttons = new LeadersBoardButtons2();
		buttons.setY(550);
		buttons.setListener(new LeadersBoardButtons2.IListener() {
			@Override
			public void onLeadersBoardModeChange() {
				loadHiScores();
			}
		});

		Game.getLanguageManager().register(this);

		setLanguageSensitiveInfo();
	}

	private void logoutOfFacebook() {
		Facebook.logout(new ICallback() {
			@Override
			public void onSuccess() {
				parent.updateView();
			}

			@Override
			public void onFail(Throwable t) {
				IchiguToast.showError(R.strings.logoutError);
			}
		});
	}

	@Override
	public void draw() {
		pageTitle.draw();
		logoutOfFacebook.draw();
		buttons.draw();
		for (LeadersBoardRow row : rows)
			row.draw();
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}

	@Override
	public void activate() {
		logoutOfFacebook.listenInput(true);
		buttons.activate();
		loadHiScores();
	}

	@Override
	public boolean deactivate() {
		logoutOfFacebook.listenInput(false);
		buttons.deactivate();
		return true;
	}

	@Override
	public String getId() {
		return "OnlineHiScores";
	}
	private void loadHiScores() {
		final int mode = buttons.getMode();
		int days = buttons.getDays();
		int whose = buttons.getWhose();

		Facebook.getLeadersBoard(mode, days, whose, new Facebook.ILeadersBoardCallback() {
			@Override
			public void onSuccess(LeadersBoard leadersBoard) {
				List<LeadersBoardRow> r = new ArrayList<LeadersBoardRow>();

				int rank = 1;
				for (Score score : leadersBoard.getScores()) {
					Player player = leadersBoard.getPlayer(score.getPlayerId());

					String scoreStr;
					if (mode == Score.ModeStandard)
						scoreStr = Util.Strings.getTimeString(score.getScore());
					else
						scoreStr = "" + score.getScore();

					r.add(new LeadersBoardRow(rank++, scoreStr, player));
				}

				rows = r;
			}

			@Override
			public void onFail(Throwable t) {
				IchiguToast.showError(R.strings.leadersBoardError + (Game.isDebug() && t != null ? ": " + t.getMessage() : ""));
				rows = new ArrayList<LeadersBoardRow>();
			}
		});
	}

	private void setLanguageSensitiveInfo() {
		pageTitle.setText(Ichigu.getString(R.strings.hiScores));
	}
}
