package com.turpgames.ichigu.model.hiscores;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.utils.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;
import com.turpgames.ichigu.utils.ScoreManager;
import com.turpgames.utils.Util;

class OnlineHiScores implements IHiScores {
	private Text pageTitle;
	private volatile List<LeadersBoardRow> rows;
	private LeadersBoardButtons buttons;

	OnlineHiScores() {
		pageTitle = new Text();
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);

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

	@Override
	public void draw() {
		pageTitle.draw();
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
		buttons.activate();
		loadHiScores();
	}

	@Override
	public boolean deactivate() {
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

		Ichigu.blockUI(R.strings.loadingScores);
		Facebook.getDefaultProfilePicture();	
		ScoreManager.instance.getLeadersBoard(mode, days, whose, new ScoreManager.ILeadersBoardCallback() {
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

					r.add(new LeadersBoardRow(leadersBoard.getOwnRank(), scoreStr, Facebook.getPlayer()));
				}

				rows = r;

				Ichigu.unblockUI();
			}

			@Override
			public void onFail(Throwable t) {
				IchiguToast.showError(R.strings.loadScoreFail + (Game.isDebug() && t != null ? ": " + t.getMessage() : ""));
				rows = new ArrayList<LeadersBoardRow>();
				Ichigu.unblockUI();
			}
		});
	}

	private void setLanguageSensitiveInfo() {
		pageTitle.setText(Ichigu.getString(R.strings.hiScores));
	}
}
