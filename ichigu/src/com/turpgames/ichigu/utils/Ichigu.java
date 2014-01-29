package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.ISound;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.component.UIBlocker;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.ichigu.model.game.CardAttributes;

public final class Ichigu {
	private static final ISound soundError;

	private static final ISound soundSuccess;
	private static final ISound soundTimeUp;
	private static final ISound soundWait;
	private static final ISound soundFlip;
	private static final ITexture textureCardBorder;

	private static final ITexture textureCardClosed;
	private static final ITexture textureCardEmpty;
	private static final ITexture[][] textureSymbols;
	static {
		IResourceManager r = Game.getResourceManager();

		soundSuccess = r.getSound(R.game.sounds.success);
		soundError = r.getSound(R.game.sounds.error);
		soundTimeUp = r.getSound(R.game.sounds.timeUp);
		soundWait = r.getSound(R.game.sounds.wait);
		soundFlip = r.getSound(R.game.sounds.flip);

		textureCardEmpty = r.getTexture(R.game.textures.cardEmpty);
		textureCardClosed = r.getTexture(R.game.textures.cardClosed);
		textureCardBorder = r.getTexture(R.game.textures.cardBorder);
		
		textureSymbols = new ITexture[3][3];
		
		for (int i = 0, shape = 1; shape < CardAttributes.allDiff; shape = shape << 1, i++) {
			for (int j = 0, pattern = 1; pattern < CardAttributes.allDiff; pattern = pattern << 1, j++) {
				textureSymbols[i][j] =  r.getTexture("card-" + shape + pattern);
			}
		}
	}

	public static void drawTextureCardBorder(IDrawingInfo info) {
		TextureDrawer.draw(textureCardBorder, info);
	}

	public static void drawTextureCardClosed(IDrawingInfo info) {
		TextureDrawer.draw(textureCardClosed, info);
	}

	public static void drawTextureCardEmpty(IDrawingInfo info) {
		TextureDrawer.draw(textureCardEmpty, info);
	}

	public static void drawSymbol(int shape, int pattern, IDrawingInfo info) {
		TextureDrawer.draw(textureSymbols[shape/2][pattern/2], info);
	}
	
	public static String getString(String resourceKey) {
		return Game.getLanguageManager().getString(resourceKey);
	}

	public static void playSoundError() {
		soundError.play();
	}

	public static void playSoundFlip() {
		soundFlip.play();
	}

	public static void playSoundSuccess() {
		soundSuccess.play();
	}

	public static void playSoundTimeUp() {
		soundTimeUp.play();
	}

	public static void playSoundWait() {
		soundWait.play();
	}
	
	public static String getStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7)
				return Game.getParam(R.game.params.appStoreAddressOld);
			else
				return Game.getParam(R.game.params.appStoreAddressIOS7);
		}
		else {
			return Game.getParam(R.game.params.playStoreAddress);
		}
	}
	
	public static void blockUI(String message) {
		UIBlocker.instance.block(Ichigu.getString(message));
	}

	public static void unblockUI() {
		UIBlocker.instance.unblock();
	}

	public static void updateBlockMessage(String message) {
		UIBlocker.instance.setMessage(Ichigu.getString(message));
	}

	private Ichigu() {

	}
}
