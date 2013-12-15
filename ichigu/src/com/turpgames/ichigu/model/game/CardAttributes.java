package com.turpgames.ichigu.model.game;

import com.turpgames.ichigu.utils.Ichigu;
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

	public static String getColorName(int color) {
		if (color == Color_Blue)
			return "Blue";
		if (color == Color_Green)
			return "Green";
		return "Red";
	}

	public static int getCompleting(int a1, int a2) {
		return a1 == a2 ? a1 : getThird(a1, a2);
	}

	public static int getCountValue(int count) {
		if (count == Count_1)
			return 1;
		if (count == Count_2)
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
		if (pattern == Pattern_Empty)
			return "Empty";
		if (pattern == Pattern_Filled)
			return "Filled";
		return "Striped";
	}

	public static String getShapeName(int shape) {
		if (shape == Shape_Circle)
			return Ichigu.getString(R.strings.circle);
		if (shape == Shape_Square)
			return Ichigu.getString(R.strings.square);
		return Ichigu.getString(R.strings.triangle);
	}
	
	public static int getThird(int a1, int a2) {
		return AllDiff ^ a1 ^ a2;
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
		if ((a1 | a2 | a3) == AllDiff)
			return 3;
		return 0;
	}

	private static boolean isIchigu(int a1, int a2, int a3) {
		return (a1 & a2) == a3 || (a1 | a2 | a3) == AllDiff;
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
		if (count == Value1)
			s += "1 ";
		else if (count == Value2)
			s += "2 ";
		else
			s += "3 ";

		if (color == Value1)
			s += "red ";
		else if (color == Value2)
			s += "green ";
		else
			s += "blue ";
		
		if (pattern == Value1)
			s += "empty ";
		else if (pattern == Value2)
			s += "filled ";
		else
			s += "striped ";
		
		if (shape == Value1)
			s += "circle ";
		else if (shape == Value2)
			s += "square ";
		else
			s += "triangle ";
		
		return s;
	}
}