package com.playbazar.models;

/**
 * Created by pvkarthik on 2016-12-06.
 */

public class ManufacturerFilterItem extends FilterItem {

	public ManufacturerFilterItem(String id, String txt) {
		super(id, txt);
	}

	@Override
	public FilterType getType() {
		return FilterType.MANUFACTURER;
	}
}
