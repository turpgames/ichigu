package com.turpgames.ichigu.model.game;

import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public final class CardAttributes {

	public final static int allDiff = 7;

	private final static int value1 = 1;
	private final static int value2 = value1 << 1;
	private final static int value3 = value1 << 2;

	public final static int colorRed = value1;
	public final static int colorGreen = value2;
	public final static int colorBlue = value3;

	public final static int count1 = value1;
	public final static int count2 = value2;
	public final static int count3 = value3;

	public final static int patternEmpty = value1;
	public final static int patternFilled = value2;
	public final static int patternStriped = value3;

	public final static int shapeCircle = value1;
	public final static int shapeSquare = value2;
	public final static int shapeTriangle = value3;
	
	public static String getColorName(int color) {
		if (color == colorBlue)
			return "Blue";
		if (color == colorGreen)
			return "Green";
		return "Red";
	}

	public static int getCompleting(int a1, int a2) {
		return a1 == a2 ? a1 : getThird(a1, a2);
	}

	public static int getCountValue(int count) {
		if (count == count1)
			return 1;
		if (count == count2)
			return 2;
		return 3;
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

	public static String getPatternName(int pattern) {
		if (pattern == patternEmpty)
			return "Empty";
		if (pattern == patternFilled)
			return "Filled";
		return "Striped";
	}

	public static String getShapeName(int shape) {
		if (shape == shapeCircle)
			return Ichigu.getString(R.strings.circle);
		if (shape == shapeSquare)
			return Ichigu.getString(R.strings.square);
		return Ichigu.getString(R.strings.triangle);
	}
	
	public static int getThird(int a1, int a2) {
		return allDiff ^ a1 ^ a2;
	}

	public static CardAttributes getThirdCardAttributes(CardAttributes a1, CardAttributes a2) {
		int color = CardAttributes.getCompleting(a1.getColor(), a2.getColor());
		int shape = CardAttributes.getCompleting(a1.getShape(), a2.getShape());
		int count = CardAttributes.getCompleting(a1.getCount(), a2.getCount());
		int pattern = CardAttributes.getCompleting(a1.getPattern(), a2.getPattern());
		return new CardAttributes(color, shape, count, pattern);
	}

	public static boolean isIchigu(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return  isIchigu(a1.color, a2.color, a3.color) &&
				isIchigu(a1.shape, a2.shape, a3.shape) &&
				isIchigu(a1.count, a2.count, a3.count) &&
				isIchigu(a1.pattern, a2.pattern, a3.pattern);

	}

	public static boolean isSameColor(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.color == a2.color;
	}
	
	public static boolean isSameCount(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.count == a2.count;
	}
	
	public static boolean isSamePattern(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.pattern == a2.pattern;
	}

	public static boolean isSameShape(CardAttributes a1, CardAttributes a2, CardAttributes a3) {
		return a1.shape == a2.shape;
	}

	private static int getIchiguScore(int a1, int a2, int a3) {
		if ((a1 & a2) == a3)
			return 1;
		if ((a1 | a2 | a3) == allDiff)
			return 3;
		return 0;
	}

	private static boolean isIchigu(int a1, int a2, int a3) {
		return (a1 & a2) == a3 || (a1 | a2 | a3) == allDiff;
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

	@Override
	public CardAttributes clone() {
		return new CardAttributes(color, shape, count, pattern);
	}

	public boolean equals(int color, int shape, int count, int pattern) {
		return this.color == color && this.shape == shape && this.count == count && this.pattern == pattern;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CardAttributes))
			return false;
		CardAttributes that = (CardAttributes) obj;
		return equals(that.color, that.shape, that.count, that.pattern);
	}

	public int getColor() {
		return color;
	}

	public String getColorName() {
		return getColorName(color);
	}

	public int getCount() {
		return count;
	}

	public int getCountValue() {
		return getCountValue(count);
	}

	public int getPattern() {
		return pattern;
	}

	public String getPatternName() {
		return getPatternName(pattern);
	}

	public int getShape() {
		return shape;
	}

	public String getShapeName() {
		return getShapeName(shape);
	}

	@Override
	public String toString() {
		String s = "";
		if (count == count1)
			s += "1 ";
		else if (count == count2)
			s += "2 ";
		else
			s += "3 ";

		if (color == colorRed)
			s += "red ";
		else if (color == colorGreen)
			s += "green ";
		else
			s += "blue ";
		
		if (pattern == patternEmpty)
			s += "empty ";
		else if (pattern == patternFilled)
			s += "filled ";
		else
			s += "striped ";
		
		if (shape == shapeCircle)
			s += "circle ";
		else if (shape == shapeSquare)
			s += "square ";
		else
			s += "triangle ";
		
		return s;
	}
}