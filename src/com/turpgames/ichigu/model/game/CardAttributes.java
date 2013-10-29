package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public final class CardAttributes {
	public static final int AllDiff = 7;

	public static final int Value1 = 1;
	public static final int Value2 = Value1 << 1;
	public static final int Value3 = Value1 << 2;

	public static final int Color_Red = Value1;
	public static final int Color_Green = Value2;
	public static final int Color_Blue = Value3;

	public static final int Count_1 = Value1;
	public static final int Count_2 = Value2;
	public static final int Count_3 = Value3;

	public static final int Pattern_Empty = Value1;
	public static final int Pattern_Filled = Value2;
	public static final int Pattern_Striped = Value3;

	public static final int Shape_Circle = Value1;
	public static final int Shape_Square = Value2;
	public static final int Shape_Triangle = Value3;

	private static boolean isIchigu(int a1, int a2, int a3) {
		return (a1 & a2) == a3 || (a1 | a2 | a3) == AllDiff;
	}

	private static int getIchiguScore(int a1, int a2, int a3) {
		if ((a1 & a2) == a3)
			return 1;
		if ((a1 | a2 | a3) == AllDiff)
			return 3;
		return 0;
	}

	public static boolean isSameColor(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.color == a2.color;
	}
	
	public static boolean isSameShape(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.shape == a2.shape;
	}

	public static boolean isSameCount(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.count == a2.count;
	}

	public static boolean isSamePattern(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.pattern == a2.pattern;
	}
	
	public static boolean isIchigu(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return  isIchigu(a1.color, a2.color, a3.color) &&
				isIchigu(a1.shape, a2.shape, a3.shape) &&
				isIchigu(a1.count, a2.count, a3.count) &&
				isIchigu(a1.pattern, a2.pattern, a3.pattern);

	}

	public static int getIchiguScore(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		int colorScore = getIchiguScore(a1.color, a2.color, a3.color);
		int shapeScore = getIchiguScore(a1.shape, a2.shape, a3.shape);
		int countScore = getIchiguScore(a1.count, a2.count, a3.count);
		int patternScore = getIchiguScore(a1.pattern, a2.pattern, a3.pattern);

		if (colorScore > 0 && shapeScore > 0 && countScore > 0 && patternScore > 0)
			return colorScore + shapeScore + countScore + patternScore;
		return 0;
	}

	public static int getCompleting(int a1, int a2) {
		return a1 == a2 ? a1 : getThird(a1, a2);
	}

	public static int getThird(int a1, int a2) {
		return AllDiff ^ a1 ^ a2;
	}

	public static String getColorName(int color) {
		if (color == Color_Blue)
			return "Blue";
		if (color == Color_Green)
			return "Green";
		return "Red";
	}

	public static int getCountValue(int count) {
		if (count == Count_1)
			return 1;
		if (count == Count_2)
			return 2;
		return 3;
	}

	public static String getPatternName(int pattern) {
		if (pattern == Pattern_Empty)
			return "Empty";
		if (pattern == Pattern_Filled)
			return "Filled";
		return "Striped";
	}

	public static String getShapeName(int shape) {
		if (shape == Shape_Circle)
			return Game.getLanguageManager().getString(R.strings.circle);
		if (shape == Shape_Square)
			return Game.getLanguageManager().getString(R.strings.square);
		return Game.getLanguageManager().getString(R.strings.triangle);
	}

	private int color;
	private int count;
	private int pattern;
	private int shape;

	public CardAttributes(int color, int shape, int count, int pattern) {
		this.color = color;
		this.shape = shape;
		this.count = count;
		this.pattern = pattern;
	}

	public CardAttributes clone() {
		return new CardAttributes(color, shape, count, pattern);
	}

	public int getColor() {
		return color;
	}

	public int getCount() {
		return count;
	}

	public int getPattern() {
		return pattern;
	}

	public int getShape() {
		return shape;
	}

	public String getColorName() {
		return getColorName(color);
	}

	public int getCountValue() {
		return getCountValue(count);
	}

	public String getPatternName() {
		return getPatternName(pattern);
	}

	public String getShapeName() {
		return getShapeName(shape);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CardAttributes))
			return false;
		CardAttributes that = (CardAttributes) obj;
		return equals(that.color, that.shape, that.count, that.pattern);
	}

	@Override
	public String toString() {
		return "" + color + shape + count + pattern;
	}

	public boolean equals(int color, int shape, int count, int pattern) {
		return this.color == color && this.shape == shape && this.count == count && this.pattern == pattern;
	}
}