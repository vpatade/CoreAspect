/**
 * Class whihc will validate the query correctness
 */
package editor.logic;

public class ValidateQuery {

	public enum error {
		incompleteQuote, valid, errorInTokenNTKD, errorInTokenEquals, errorInTokenQuote, errorInTokenAndOR, incorrectEnd
	};

	public static error e;

	public static void main(String args[]) {
		System.out.println((int) 'A' + 32);
		String q = "  NAmE   = \"   Sleep-1\" or   name=\"Timer\" or nAme   =\"Timer\"   ";

		q = "type=\"f\"asdf";
		String q1 = fixQuery(q);
		System.out.println(q1);
		System.out.println(validateQuery(q1));

	}

	public static String validateQuery(String qry) {
		StringBuilder sb = new StringBuilder();
		e = error.valid;
		Boolean and_or = true;

		if (qry == null || qry.equals("")) {
			e = error.valid;
		}

		else {
			int i = 0;
			while (i < qry.length()) {
				sb = new StringBuilder();

				// name type kind desc
				if (and_or) {
					try {

						for (int j = 0; (qry.charAt(i) >= 'a' && qry.charAt(i) <= 'z'); j++) {
							sb.append(qry.charAt(i++));
						}
					} catch (StringIndexOutOfBoundsException ex) {
						e = error.errorInTokenNTKD;
						break;
					}
					if (!(sb.toString().equals("name")
							|| sb.toString().equals("type")
							|| sb.toString().equals("kind") || sb.toString()
							.equals("desc")) || sb.length() != 4) {
						e = error.errorInTokenNTKD;
						break;
					}

					// check '='
					try {
						if (qry.charAt(i++) != '=') {
							e = error.errorInTokenEquals;
							break;
						}
					} catch (StringIndexOutOfBoundsException ex) {
						e = error.errorInTokenEquals;
						break;
					}

					// check ""

					try {
						if (qry.charAt(i++) != '"') {
							e = error.errorInTokenQuote;
							break;
						} else {
							try {
								while (qry.charAt(i++) != '"')
									;
							} catch (StringIndexOutOfBoundsException ex) {
								e = error.incompleteQuote;
								break;
							}
						}
					} catch (StringIndexOutOfBoundsException ex) {
						e = error.errorInTokenQuote;
						break;
					}
					and_or = false;
				}

				// check and/or

				if (i < qry.length() && qry.charAt(i) == ' ') {
					and_or = true;
					try {
						sb = new StringBuilder();
						sb.append(qry.charAt(i++));
						while (qry.charAt(i) != ' ') {
							sb.append(qry.charAt(i++));
						}
						sb.append(qry.charAt(i++));
					} catch (StringIndexOutOfBoundsException ex) {
						e = error.errorInTokenAndOR;
						break;
					}
					if (!(sb.toString().equals(" and ") || sb.toString()
							.equals(" or "))) {
						e = error.errorInTokenAndOR;
						break;
					}
				}
				if (!and_or && i < qry.length()) {
					e = error.incorrectEnd;
					break;
				}
			}
		}

		String errorMessage = "Valid";
		switch (e) {
		case incompleteQuote:
			errorMessage = "Query Error: The quotes are not complete";
			break;
		case incorrectEnd:
			errorMessage = "Query Error: The query is ended incorrectly";
			break;
		case valid:
			errorMessage = "Valid";
			break;
		case errorInTokenNTKD:
			errorMessage = "Query Error: Error in token name/type/kind/desc";
			break;
		case errorInTokenEquals:
			errorMessage = "Query Error: Error in token =";
			break;
		case errorInTokenQuote:
			errorMessage = "Query Error: Error in token \"";
			break;
		case errorInTokenAndOR:
			errorMessage = "Query Error: Error in token and/or";
			break;
		}

		return errorMessage;

	}

	public static String fixQuery(String query) {
		boolean isInsideQuote = false, isSpace = false;
		StringBuilder q = new StringBuilder();
		int space = 0;

		if (!(query == null || query.equals(""))) {
			query = query.trim();
			for (int i = 0; i < query.length(); i++) {
				if (query.charAt(i) == '"') {
					isInsideQuote = !isInsideQuote;
					q.append(query.charAt(i));
					// i++;
					continue;
				}

				if (!isInsideQuote) {

					if (query.charAt(i) == ' ')
						space++;

					else
						space = 0;

					if (space > 1)
						continue;

					if (query.charAt(i) >= 'A' && query.charAt(i) <= 'Z') {
						q.append((char) (query.charAt(i) + 32));
					} else if (query.charAt(i) == '=') {
						if (i > 0 && query.charAt(i - 1) == ' ') {
							q.deleteCharAt(q.length() - 1);
						}
						q.append(query.charAt(i));

						if (i < (query.length() - 1)
								&& query.charAt(i + 1) == ' ') {
							while (i < (query.length() - 1)
									&& query.charAt(i + 1) == ' ')
								i++;
						}
					} else {
						q.append(query.charAt(i));
					}

				} else
					q.append(query.charAt(i));
			}
		}

		return q.toString();
	}
}