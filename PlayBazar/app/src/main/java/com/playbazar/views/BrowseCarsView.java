package com.playbazar.views;

import com.playbazar.models.ManufacturerFilterItem;
import com.playbazar.models.ModelFilterItem;
import com.playbazar.models.ServerAPIResponse;

public interface BrowseCarsView {

	void onDataReceived(ServerAPIResponse serverAPIResponse);

	void onFailure(String errorMsg);

	void onManufacturerSelected(ManufacturerFilterItem manufacturerFilterItem);

	void onModelSelected(ModelFilterItem modelFilterItem);

}
