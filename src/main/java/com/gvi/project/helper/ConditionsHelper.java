package com.gvi.project.helper;

import com.gvi.project.GamePanel;
import com.gvi.project.models.data_objects.ConditionObject;
import com.gvi.project.models.data_objects.ConditionsObject;


public class ConditionsHelper {

	public static boolean conditionsAreMet(GamePanel gp, ConditionsObject conditions) {

		if ("and".equals(conditions.and_or)) {
			for (ConditionObject cObj : conditions.conditionObjects) {
				if (!checkCondition(gp, cObj)) {
					return false; // sofort abbrechen
				}
			}
			return true;
		}

		if ("or".equals(conditions.and_or)) {
			for (ConditionObject cObj : conditions.conditionObjects) {
				if (checkCondition(gp, cObj)) {
					return true; // sofort erfüllt
				}
			}
			return false;
		}

		return false;
	}

	private static boolean checkCondition(GamePanel gp, ConditionObject cObj) {

		return switch (cObj.type) {
			case "item" -> checkForItems(gp, cObj);
			case "statistic" -> checkForStatistic(gp, cObj);
			default -> false;
		};
	}

	private static boolean checkForItems(GamePanel gp, ConditionObject cObj) {

		int playerValue = gp.player.playerItems.getOrDefault(cObj.compareWith, 0);

		boolean result = compareValues(playerValue, cObj.value, cObj.comparator);

		if (!result) {
			gp.ui.openMessage("Missing Key".formatted(cObj.value, cObj.compareWith));
		}

		return result;
	}

	private static boolean checkForStatistic(GamePanel gp, ConditionObject cObj) {

		return switch (cObj.compareWith) {
			case "score" -> {
				boolean result = compareValues(gp.player.score, cObj.value, cObj.comparator);
				if (!result) {
					gp.ui.openMessage("Needs a score of %s or higher".formatted(cObj.value));
				}
				yield result;
			}
			default -> false;
		};
	}

	private static boolean compareValues(int val1, int val2, String comparator) {
		return switch (comparator) {
			case "==" -> val1 == val2;
			case ">=" -> val1 >= val2;
			case "<=" -> val1 <= val2;
			case "<" -> val1 < val2;
			case ">" -> val1 > val2;
			default -> false;
		};
	}
}
