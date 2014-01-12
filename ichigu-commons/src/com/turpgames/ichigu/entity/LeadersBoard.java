package com.turpgames.ichigu.entity;

public class LeadersBoard {
	private int scoreMode;
	private int days;
	private int playerId;
	private Score[] scores;
	private Player[] players;

	public int getScoreMode() {
		return scoreMode;
	}

	public void setScoreMode(int scoreMode) {
		this.scoreMode = scoreMode;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public Score[] getScores() {
		return scores;
	}

	public void setScores(Score[] scores) {
		this.scores = scores;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public Player getPlayer(int id) {
		for (Player p : players)
			if (p.getId() == id)
				return p;
		return null;
	}
}
