package editor.util;

public interface Constants {
	String DEFAULT = "default";
	String ASPEFT_FILE = "/Aspects.bpaspect";
	String YES = "yes";

	// Query Language Specific Constants
	String OR = "or";
	String AND = "and";
	String LOGICAL_OR = " or ";
	String LOGICAL_AND = " and ";
	String QL_KIND = "kind=";
	String QL_TYPE = "type=";
	String QL_NAME = "name=";
	String QL_DESC = "desc=";

	// XML Elements
	String ELE_ASPECT = "aspect";
	String ELE_POINTCUT = "pointcut";
	String ELE_QUERY = "query";
	String ELE_ADVICE = "advice";
	String ELE_DOCU = "documentation";
	String ELE_ACTIVITY = "activity";
	String ELE_IMPL = "ImplementationJava";

	// XML Attributes
	String ATT_ORDER = "order";
	String ATT_TNS = "targetNamespace";
	String ATT_XNS = "xmlns";
	String ATT_XSI = "xmlns:xsi";
	String ATT_SCHEMA_LOCATION = "xsi:schemaLocation";
	String ATT_NAME = "name";
	String ATT_QUERY_LANG = "queryLanguage";
	String ATT_POINTCUT = "pointcut";
	String ATT_WHERE = "where";
	String ATT_CLASS_NAME = "className";

	// XML Attribute Values
	String VAL_TNS = "http://example.org/One";
	String VAL_XNS = "/schema/";
	String VAL_XSI = "http://www.w3.org/2001/XMLSchema-instance";
	String VAL_SCHEMA_LOCATION = "xsd/bp/aspect.xsd";
	String VAL_QUERY_LANG = "/bp/lang/pointCutSelectionLanguage";
}
