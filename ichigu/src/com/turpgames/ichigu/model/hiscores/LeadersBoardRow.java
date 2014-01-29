package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Image;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.social.Facebook;
import com.turpgames.ichigu.utils.R;

public class LeadersBoardRow implements IDrawable {
	private final float profilePictureSize = 35f;
	private final float rowWidth = 500f;
	private final float rowHeight = 40f;
	private float rankWidth = 0.1f;
	private float imageWidth = 0.1f;
	private float nameWidth = 0.7f;
	// private float scoreWidth = 0.1f;

	// LeadersBoard (25, 100) x (525, 525) karesine çizilecek
	private final Vector bottomLeft = new Vector(25, 100);
	private final Vector topRight = new Vector(525, 525);

	private final Text rank;
	private final Image profilePicture;
	private final Text playerName;
	private final Text score;

	public LeadersBoardRow(int rank, String score, Player player) {
		this.rank = createText(rank + ".");

		this.profilePicture = new Image();
		this.profilePicture.setTextureFromUrl(player.getFacebookProfilePictureUrl(), Facebook.getDefaultProfilePicture());
		this.profilePicture.setWidth(profilePictureSize);
		this.profilePicture.setHeight(profilePictureSize);

		this.playerName = createText(player.getUsername());
		this.score = createText(score);

		if (Facebook.getPlayer().getSocialId().equals(player.getFacebookId())) {
			this.rank.getColor().set(R.colors.ichiguYellow);
			this.playerName.getColor().set(R.colors.ichiguYellow);
			this.score.getColor().set(R.colors.ichiguYellow);
		}

		setLocations(rank - 1);
	}

	private void setLocations(int rowIndex) {
		float y = topRight.y - (rowIndex * rowHeight);
		float x = bottomLeft.x;
		rank.setLocation(x, y);
		profilePicture.getLocation().set(x + rowWidth * rankWidth, y - (rowHeight - profilePictureSize));
		playerName.setLocation(x + rowWidth * (rankWidth + imageWidth), y);
		score.setLocation(x + rowWidth * (rankWidth + imageWidth + nameWidth), y);
	}

	private static Text createText(String content) {
		Text text = new Text();
		text.setText(content);
		text.setAlignment(Text.HAlignLeft, Text.VAlignBottom);
		text.setFontScale(0.5f);
		return text;
	}

	@Override
	public void draw() {
		rank.draw();
		profilePicture.draw();
		playerName.draw();
		score.draw();
	}
}
