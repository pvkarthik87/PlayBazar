package com.playbazar.networking;

import com.playbazar.config.Constants;
import com.playbazar.models.ServerAPIResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pvkarthik on 2016-12-04.
 */

public interface RestService {

	@GET("/v1/car-types/manufacturer?pageSize="+ Constants.NUM_ITEMS_IN_PAGE +"&wa_key=coding-puzzle-client-449cc9d")
	Observable<ServerAPIResponse> getManufacturerList(@Query("page") int pageNo);

	@GET("/v1/car-types/main-types?pageSize="+ Constants.NUM_ITEMS_IN_PAGE +"&wa_key=coding-puzzle-client-449cc9d")
	Observable<ServerAPIResponse> getManufacturerModelList(@Query("page") int pageNo, @Query("manufacturer") String manufacturer);

	@GET("/v1/car-types/built-dates?wa_key=coding-puzzle-client-449cc9d")
	Observable<ServerAPIResponse> getManufacturerModelYearList(@Query("manufacturer") String manufacturer, @Query("main-type") String model);

}