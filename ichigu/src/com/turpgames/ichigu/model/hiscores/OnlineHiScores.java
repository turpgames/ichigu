package com.turpgames.ichigu.model.hiscores;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Settings;
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
	private LeadersBoardButtons buttons;

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

		buttons = new LeadersBoardButtons();
		buttons.setY(550);
		buttons.setListener(new LeadersBoardButtons.IListener() {
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
				String scoreStr;
				for (Score score : leadersBoard.getScores()) {
					Player player = leadersBoard.getPlayer(score.getPlayerId());

					if (mode == Score.ModeStandard)
						scoreStr = Util.Strings.getTimeString(score.getScore());
					else
						scoreStr = "" + score.getScore();

					r.add(new LeadersBoardRow(rank++, scoreStr, player));
				}

				if (leadersBoard.getOwnScore() != null) {

					if (mode == Score.ModeStandard)
						scoreStr = Util.Strings.getTimeString(leadersBoard.getOwnScore().getScore());
					else
						scoreStr = "" + leadersBoard.getOwnScore().getScore();

					com.turpgames.framework.v0.social.Player f = Facebook.getPlayer();
					Player p = new Player();
					p.setEmail(f.getEmail());
					p.setFacebookId(f.getSocialId());
					p.setId(Util.Strings.parseInt(f.getId(), 0));
					p.setUsername(f.getName());

					r.add(new LeadersBoardRow(leadersBoard.getOwnRank(), scoreStr, p));
				}

				rows = r;

				sendHiScoresToServer();
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

	private void sendHiScoresToServer() {
		Util.Threading.runInBackground(new Runnable() {
			@Override
			public void run() {
				sendScoreToServer(Score.ModeMini, R.settings.hiscores.miniChallenge);
				sendScoreToServer(Score.ModeStandard, R.settings.hiscores.standard);
				sendScoreToServer(Score.ModeTime, R.settings.hiscores.timeChallenge);
			}
		});
	}

	private static void sendScoreToServer(int mode, String scoreSettingsKey) {
		final String flagSettingsKey = scoreSettingsKey + "-sent-to-server";

		boolean scoreAlreadySent = Settings.getBoolean(flagSettingsKey, false);
		if (scoreAlreadySent)
			return;

		int score = Settings.getInteger(scoreSettingsKey, 0);
		if (score < 1)
			return;

		Facebook.sendScore(mode, score, new ICallback() {
			@Override
			public void onSuccess() {
				Settings.putBoolean(flagSettingsKey, true);
			}

			@Override
			public void onFail(Throwable t) {

			}
		});
	}
}
