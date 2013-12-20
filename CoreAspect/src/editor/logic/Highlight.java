/**
 * Class responsible for Highlighting the pointcut which 
 * satisfies the pointcut selection query
 */
package editor.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.viewers.StructuredSelection;

import editor.ui.plugin.EditorEditor;

public class Highlight {

	public static List<Integer> proc_coeff;

	/**
	 * Parse the query and 
	 * */
	public static void parseQuery(String qry) {

		proc_coeff = new ArrayList<Integer>();
	
		List<String> activityNames = new ArrayList<String>();
		List<String> activityTypes = new ArrayList<String>();
		List<String> processNames = new ArrayList<String>();

		String act, proc, and_or = "or";
		int j, k;

		// get substrings
		act = "";

		if (qry.contains("activity(")) {

			int s = qry.indexOf("activity(") + 9;
			if (qry.contains("process(") && qry.startsWith("activity(")) {
				String onlyact = qry.substring(0, qry.indexOf("process("));
				act = onlyact.substring(s, onlyact.lastIndexOf(")"));
			} else {
				act = qry.substring(s, qry.lastIndexOf(")"));
			}
			
			System.out.println(act);
			String act1 = act;

			for (int i = 0; i < act1.length(); i++) {
				if ((act1.indexOf("name=\"", i) < act1.length()) && act1.substring(i, i + 6).equals("name=\"")) {

					j = act1.indexOf("name=\"", i) + 6;
					k = act1.indexOf("\"", j);
					activityNames.add(act1.substring(j, k));
					i = k;
				} else if ((act1.indexOf("type=\"", i) < act1.length()) && act1.substring(i, i + 6).equals("type=\"")) {
					j = act1.indexOf("type=\"", i) + 6;
					k = act1.indexOf("\"", j);
					activityTypes.add(act1.substring(j, k));
					i = k;
				}
			}

		}

		if (qry.contains("process(")) {

			int s = (qry.indexOf("process(") + 8);

			if (qry.contains("activity(") && qry.startsWith("process(")) {
				String onlyproc = qry.substring(0, qry.indexOf("activity("));
				proc = onlyproc.substring(s, onlyproc.lastIndexOf(")"));

			} else {
				proc = qry.substring(s, qry.lastIndexOf(")"));
			}

			System.out.println(proc);
			String proc1 = proc;

			for (int i = 0; i < proc1.length(); i++) {
				if ((proc1.indexOf("name=\"", i) < proc1.length()) && proc1.substring(i, i + 6).equals("name=\"")) {

					j = proc1.indexOf("name=\"", i) + 6;
					k = proc1.indexOf("\"", j);
					processNames.add(proc1.substring(j, k));
					i = k;
				}

			}

		}

		if (qry.contains("process(") && qry.contains("activity(")) {

			if (qry.startsWith("activity(")) {
				and_or = qry.substring(qry.indexOf(")", qry.indexOf("activity(")) + 1, qry.indexOf("process("));
			} else {
				and_or = qry.substring(qry.indexOf(")", qry.indexOf("process(")) + 1, qry.indexOf("activity("));

			}
			and_or = and_or.trim();
			System.out.println("\\" + and_or + "\\");
		}

		int i;
		List<Object> selection = new ArrayList<Object>();

		if (and_or.equals("and")) {

			for (i = 0; i < processNames.size(); i++) {
				selection.addAll(highlight(processNames.get(i), true, true));
			}
			for (i = 0; i < activityNames.size(); i++) {
				selection.addAll(highlight(activityNames.get(i), true, true));
			}
			for (i = 0; i < activityTypes.size(); i++) {
				selection.addAll(highlight(activityTypes.get(i), false, true));
			}

		} else {

			for (i = 0; i < processNames.size(); i++) {
				selection.addAll(highlight(processNames.get(i), true, false));
			}
			for (i = 0; i < activityTypes.size(); i++) {
				selection.addAll(highlight(activityTypes.get(i), false, false));
			}
			for (i = 0; i < activityNames.size(); i++) {
				selection.addAll(highlight(activityNames.get(i), true, false));
			}

		}

		EditorEditor.statSelectionViewer.setSelection(new StructuredSelection(selection), true);

	}
	
	/**
	 * Highlight the pointcuts
	 */

	public static List<Object> highlight(String value, Boolean isName, Boolean ProcAndAct) {
		int i, j;
		String temp[] = new String[3];
		List<String> coefficient = new ArrayList<String>();
		List<Integer> v = new ArrayList<Integer>();
		List<Object> selection = new ArrayList<Object>();

		for (i = 0; i < EditorSwitch.name.size(); i++) {

			if (isName) {
				if (EditorSwitch.name.elementAt(i).equals(value))
					v.add(i);
			} else {
				if (EditorSwitch.type.elementAt(i).equals(value))
					v.add(i);
			}
		}

		for (i = 0; i < v.size(); i++) {

			coefficient.add(EditorSwitch.coeff.elementAt(v.get(i)));
		}

		for (int k = 0; k < coefficient.size(); k++) {
			if (coefficient.get(k).contains(".")) {
				temp = coefficient.get(k).split("\\.");
				i = Integer.parseInt(temp[0]);
				j = Integer.parseInt(temp[1]);
				
				boolean flag = false;

				for (int m = 0; m < proc_coeff.size(); m++) {
					if (proc_coeff.get(m) == i)
						flag = true;
				}

				if ((ProcAndAct && flag) || !ProcAndAct) {
					Object s1 = new StructuredSelection();

					s1 = EditorEditor.statEditingDomain.getResourceSet().getResources().get(0).getContents().get(0)
							.eContents().get(i).eContents().get(j);

					if (temp.length != 3)
						selection.add(s1);

					if (temp.length == 3) {
						Object s2 = new StructuredSelection();

						s2 = EditorEditor.statEditingDomain.getResourceSet().getResources().get(0).getContents().get(0)
								.eContents().get(i).eContents().get(j).eContents().get(Integer.parseInt(temp[2]));

						selection.add(s2);

					}
				}
				/*
				 * else if(!ProcAndAct) { Object s1 = new StructuredSelection();
				 * 
				 * s1 = EditorEditor.statEditingDomain.getResourceSet()
				 * .getResources().get(0).getContents().get(0).eContents()
				 * .get(i).eContents().get(j);
				 * 
				 * selection.add(s1);
				 * 
				 * }
				 */
			} else {
				i = Integer.parseInt(coefficient.get(k));
				if (ProcAndAct)
					proc_coeff.add(i);
				Object s2 = new StructuredSelection();

				s2 = EditorEditor.statEditingDomain.getResourceSet().getResources().get(0).getContents().get(0)
						.eContents().get(i);

				selection.add(s2);

			}
		}

		return selection;

	}

}
