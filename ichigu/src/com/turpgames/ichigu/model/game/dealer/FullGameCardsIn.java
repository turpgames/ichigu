package com.turpgames.ichigu.model.game.dealer;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ichigu.model.game.Card;

@SuppressWarnings("serial")
public class FullGameCardsIn extends ArrayList<Card> {
	private List<Card> extras;
	private List<Card> others;
	
	public FullGameCardsIn() {
		this.extras = new ArrayList<Card>();
		this.others = new ArrayList<Card>();
	}
	
	public void addOther(Card card) {
		others.add(card);
		this.add(card);
	}
	
	public void addExtra(Card card) {
		extras.add(card);
		this.add(card);
	}
	
	public void removeExtra(Card card) {
		extras.remove(card);
		this.remove(card);
	}
	
	public void addAllOthers(List<Card> list) {
		others.addAll(list);
		this.addAll(list);
	}
	
	public void removeAllOthers(List<Card> list) {
		others.removeAll(list);
		this.removeAll(list);
	}
	
	@Override
	public void clear() {
		extras.clear();
		others.clear();
		super.clear();
	}
	
	public List<Card> getOthers() {
		return others;
	}
	
	public List<Card> getExtras() {
		return extras;
	}
}
