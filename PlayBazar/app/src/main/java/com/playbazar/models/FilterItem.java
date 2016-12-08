package com.playbazar.models;

/**
 * Created by pvkarthik on 2016-12-06.
 */

public abstract class FilterItem {

	public enum FilterType {
		MANUFACTURER,
		MODEL
	}

	private String mFilterId;
	private String mFilterTxt;

	public FilterItem(String id, String txt) {
		mFilterId = id;
		mFilterTxt = txt;
	}

	public String getId() {
		return mFilterId;
	}

	public String getText() {
		return mFilterTxt;
	}

	public abstract FilterType getType();
}
