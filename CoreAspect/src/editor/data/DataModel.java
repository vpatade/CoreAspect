/**
 *  Class holds all the data
 * 
 * */
package editor.data;

import java.util.ArrayList;
import java.util.List;

public class DataModel {
	public static String projectName = null;

	public static List<String> pointCutNames = new ArrayList<String>();
	public static List<String> pointCutQueries = new ArrayList<String>();

	public static List<String> advicePointCutNames = new ArrayList<String>();
	public static List<String> adviceNames = new ArrayList<String>();
	public static List<String> adviceWhen = new ArrayList<String>();
	public static List<String> adviceImplementations = new ArrayList<String>();
	public static List<String> adviceDocumentations = new ArrayList<String>();

	public static List<String> activityNames = new ArrayList<String>();
	public static List<String> activityTypes = new ArrayList<String>();

	public static List<String> processNames = new ArrayList<String>();
	public static List<String> processTNSs = new ArrayList<String>();

	public static String selectedtext = " ";

	// Clears all the collection objects. Datamodel will be empty
	public static void removeAll() {
		pointCutNames.clear();
		pointCutQueries.clear();

		advicePointCutNames.clear();
		adviceNames.clear();
		adviceDocumentations.clear();
		adviceWhen.clear();
		adviceImplementations.clear();

		activityNames.clear();
		activityTypes.clear();

		processNames.clear();
		processTNSs.clear();

		EditorSwitch.activityname.removeAllElements();
		EditorSwitch.activitytype.removeAllElements();
		EditorSwitch.processname.removeAllElements();
		EditorSwitch.processtns.removeAllElements();

		EditorSwitch.classname.removeAllElements();
		EditorSwitch.index.removeAllElements();
		EditorSwitch.num.removeAllElements();

		EditorSwitch.coeff.removeAllElements();
		EditorSwitch.name.removeAllElements();
		EditorSwitch.type.removeAllElements();

		EditorSwitch.pronum = 0;

		projectName = null;

	}

	/**
	 * Removes aspect and it's details from data model
	 * */
	public static void removeAspect() {
		pointCutNames.clear();
		pointCutQueries.clear();

		advicePointCutNames.clear();
		adviceNames.clear();
		adviceDocumentations.clear();
		adviceWhen.clear();
		adviceImplementations.clear();
	}

	public static String getQuery(String pc) {
		for (int i = 0; i < pointCutNames.size(); i++)
			if (pointCutNames.get(i).equalsIgnoreCase(pc))
				return pointCutQueries.get(i);
		return "";
	}

	public static boolean hasPC(String pc) {
		for (int i = 0; i < pointCutNames.size(); i++) {
			if (pointCutNames.get(i).equalsIgnoreCase(pc))
				return true;
		}
		return false;
	}

	public static int getADIndex(String ad) {
		for (int i = 0; i < adviceNames.size(); i++) {
			if (adviceNames.get(i).equalsIgnoreCase(ad))
				return i;
		}
		return -1;
	}

	public static boolean hasAdvice(String advice) {
		for (int i = 0; i < adviceNames.size(); i++) {
			if (adviceNames.get(i).equalsIgnoreCase(advice))
				return true;
		}
		return false;
	}

	public static void swapad(int ind1, int ind2) {
		// add to temp
		String pc, ad, adim, adwe, addo;
		pc = advicePointCutNames.get(ind1);
		ad = adviceNames.get(ind1);
		adim = adviceImplementations.get(ind1);
		adwe = adviceWhen.get(ind1);
		addo = adviceWhen.get(ind1);

		// add to ind2 pos
		advicePointCutNames.add(ind1, advicePointCutNames.get(ind2));
		adviceNames.add(ind1, adviceNames.get(ind2));
		adviceImplementations.add(ind1, adviceImplementations.get(ind2));
		adviceWhen.add(ind1, adviceWhen.get(ind2));
		adviceDocumentations.add(ind1, adviceDocumentations.get(ind2));

		// add to ind1 pos
		advicePointCutNames.add(ind2, pc);
		adviceNames.add(ind2, ad);
		adviceImplementations.add(ind2, adim);
		adviceWhen.add(ind2, adwe);
		adviceDocumentations.add(ind2, addo);
	}

	public static void deletePC(String pc) {
		int i;
		for (i = 0; i < pointCutNames.size(); i++) {
			if (pointCutNames.get(i).equalsIgnoreCase(pc)) {
				pointCutNames.remove(i);
				pointCutQueries.remove(i);
			}
		}
		for (i = 0; i < advicePointCutNames.size(); i++) {
			if (advicePointCutNames.get(i).equalsIgnoreCase(pc)) {
				advicePointCutNames.remove(i);
				adviceNames.remove(i);
				adviceImplementations.remove(i);
				adviceWhen.remove(i);
				adviceDocumentations.remove(i);
			}
		}
	}

	public static void deleteAdvice(String ad) {
		int ind = getADIndex(ad);
		advicePointCutNames.remove(ind);
		adviceNames.remove(ind);
		adviceWhen.remove(ind);
		adviceImplementations.remove(ind);
		adviceDocumentations.remove(ind);
	}
}
