package com.playbazar.views;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * View interface which notifies presenter to perform some operations.
 */

import com.playbazar.models.ManufacturerFilterItem;
import com.playbazar.models.ModelFilterItem;
import com.playbazar.models.RESTApiGenericResponse;

public interface BrowseCarsView {

	void onDataReceived(RESTApiGenericResponse RESTApiGenericResponse);

	void onFailure(String errorMsg);

	void onManufacturerSelected(ManufacturerFilterItem manufacturerFilterItem);

	void onModelSelected(ModelFilterItem modelFilterItem);

}
