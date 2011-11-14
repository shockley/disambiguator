package index;

import org.apache.lucene.document.Field;

/**
 * Fields we need to index
 * @author Shockley
 */
public class Fields {
	/**
	 * Names of these fields
	 */	
	public static String ALL = "all";
	
	public static String SF_AWARDS = "sf_awards";
	public static String SF_SUMMARY = "sf_summary";
	public static String SF_INTENDED_AUDIENCE = "sf_intended_audience";
	public static String SF_OPERATING_SYSTEM = "sf_operating_system";
	public static String SF_PROGRAMMING_LANGUAGE = "sf_programming_language";
	public static String SF_TRANSLATIONS = "sf_translations";
	public static String SF_LICENSE = "sf_license";
	public static String SF_TOPIC = "sf_topic";
	public static String SF_USER_INTERFACE = "sf_user_interface";
	
	
	public static String OW2_SUMMARY = "ow2_summary";
	public static String OW2_INTENDED_AUDIENCE = "ow2_intended_audience";
	public static String OW2_OPERATING_SYSTEM = "ow2_operating_system";
	public static String OW2_PROGRAMMING_LANGUAGE = "ow2_programming_language";
	public static String OW2_ENVIRONMENT = "ow2_environment";
	public static String OW2_LICENSE = "ow2_license";
	public static String OW2_TOPIC = "ow2_topic";
	public static String OW2_USER_INTERFACE = "ow2_user_interface";
	
	public static String FM_SUMMARY = "fm_summary";
	public static String FM_OPERATING_SYSTEM = "fm_operating_system";
	public static String FM_PROGRAMMING_LANGUAGE = "fm_programming_language";
	public static String FM_LICENSE = "fm_license";
	public static String FM_TOPIC = "fm_topic";

	
	public static String PROJECT_ID = "project_id";
	public static String PROJECT_REAL_NAME = "project_real_name";
	public static String PROJECT_FORGE = "project_forge";
	/**
	 * static methods to return new lucene Field Objects
	 * @param value, the String content of each field
	 * @return
	 */
	public static Field ALL(String value) {
		return new Field(ALL, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field PROJECT_ID(String value) {
		return new Field(PROJECT_ID, value, Field.Store.YES,Field.Index.NOT_ANALYZED);
	}
	public static Field PROJECT_REAL_NAME(String value) {
		if(value==null)
			value = "";
		return new Field(PROJECT_REAL_NAME, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field PROJECT_FORGE(String value) {
		if(value==null)
			value = "";
		return new Field(PROJECT_FORGE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	
	public static Field SF_AWARDS(String value) {
		if(value==null)
			value = "";
		return new Field(SF_AWARDS, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_SUMMARY(String value) {
		if(value==null)
			value = "";
		return new Field(SF_SUMMARY, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_INTENDED_AUDIENCE(String value) {
		if(value==null)
			value = "";
		return new Field(SF_INTENDED_AUDIENCE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_OPERATING_SYSTEM(String value) {
		if(value==null)
			value = "";
		return new Field(SF_OPERATING_SYSTEM, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_PROGRAMMING_LANGUAGE(String value) {
		if(value==null)
			value = "";
		return new Field(SF_PROGRAMMING_LANGUAGE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_TRANSLATIONS(String value) {
		if(value==null)
			value = "";
		return new Field(SF_TRANSLATIONS, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_LICENSE(String value) {
		if(value==null)
			value = "";
		return new Field(SF_LICENSE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_TOPIC(String value) {
		if(value==null)
			value = "";
		return new Field(SF_TOPIC, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field SF_USER_INTERFACE(String value) {
		if(value==null)
			value = "";
		return new Field(SF_USER_INTERFACE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	
	public static Field OW2_SUMMARY(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_SUMMARY, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field OW2_INTENDED_AUDIENCE(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_INTENDED_AUDIENCE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field OW2_OPERATING_SYSTEM(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_OPERATING_SYSTEM, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field OW2_PROGRAMMING_LANGUAGE(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_PROGRAMMING_LANGUAGE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field OW2_ENVIRONMENT(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_ENVIRONMENT, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field OW2_LICENSE(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_LICENSE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field OW2_TOPIC(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_TOPIC, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field OW2_USER_INTERFACE(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_USER_INTERFACE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	
	public static Field FM_SUMMARY(String value) {
		if(value==null)
			value = "";
		return new Field(OW2_SUMMARY, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field FM_OPERATING_SYSTEM(String value) {
		if(value==null)
			value = "";
		return new Field(FM_OPERATING_SYSTEM, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field FM_PROGRAMMING_LANGUAGE(String value) {
		if(value==null)
			value = "";
		return new Field(FM_PROGRAMMING_LANGUAGE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field FM_LICENSE(String value) {
		if(value==null)
			value = "";
		return new Field(FM_LICENSE, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	public static Field FM_TOPIC(String value) {
		if(value==null)
			value = "";
		return new Field(FM_TOPIC, value, Field.Store.YES,Field.Index.ANALYZED);
	}
	
	
}
