package com.playbazar.networking;

import com.playbazar.logging.DefaultLogger;
import com.playbazar.models.RESTApiGenericResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pvkarthik on 2016-12-04.
 *
 * REST Client which communicates to server to perform some operations
 */

public class ApiRepo {

	private static final String TAG = DefaultLogger.makeLogTag(ApiRepo.class);

	private final RestService mRestService;

	public ApiRepo(RestService restService) {
		this.mRestService = restService;
	}

	public Subscription getManufacturerList(int pageNo, final RESTApiCallback callback) {
		return mRestService.getManufacturerList(pageNo)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends RESTApiGenericResponse>>() {
					@Override
					public Observable<? extends RESTApiGenericResponse> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<RESTApiGenericResponse>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						DefaultLogger.d(TAG, "onError");
						callback.onError(new NetworkError(e));
					}

					@Override
					public void onNext(RESTApiGenericResponse RESTApiGenericResponse) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(RESTApiGenericResponse);
					}
				});
	}

	public Subscription getModelList(int pageNo, String manufacturer, final RESTApiCallback callback) {
		return mRestService.getManufacturerModelList(pageNo, manufacturer)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends RESTApiGenericResponse>>() {
					@Override
					public Observable<? extends RESTApiGenericResponse> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<RESTApiGenericResponse>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						DefaultLogger.d(TAG, "onError");
						callback.onError(new NetworkError(e));
					}

					@Override
					public void onNext(RESTApiGenericResponse RESTApiGenericResponse) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(RESTApiGenericResponse);
					}
				});
	}

	public Subscription getModelYearList(String manufacturer, String model, final RESTApiCallback callback) {
		return mRestService.getManufacturerModelYearList(manufacturer, model)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends RESTApiGenericResponse>>() {
					@Override
					public Observable<? extends RESTApiGenericResponse> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<RESTApiGenericResponse>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						DefaultLogger.d(TAG, "onError");
						callback.onError(new NetworkError(e));
					}

					@Override
					public void onNext(RESTApiGenericResponse RESTApiGenericResponse) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(RESTApiGenericResponse);
					}
				});
	}

	public interface RESTApiCallback {
		void onSuccess(RESTApiGenericResponse response);

		void onError(NetworkError networkError);
	}

}
