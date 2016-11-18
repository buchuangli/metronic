package com.open01.logs.model;

public class SerchTerms {
private int terms_id;
private String terms_name;
private int terms_count;
private int user_id;
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public int getTerms_id() {
	return terms_id;
}
public void setTerms_id(int terms_id) {
	this.terms_id = terms_id;
}
public String getTerms_name() {
	return terms_name;
}
public void setTerms_name(String terms_name) {
	this.terms_name = terms_name;
}
public int getTerms_count() {
	return terms_count;
}
public void setTerms_count(int terms_count) {
	this.terms_count = terms_count;
}
@Override
public String toString() {
	return "SerchTerms [terms_id=" + terms_id + ", terms_name=" + terms_name + ", terms_count=" + terms_count
			+ ", user_id=" + user_id + "]";
}

}
