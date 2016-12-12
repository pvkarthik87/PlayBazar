package com.playbazar.presenters;

import com.playbazar.models.ManufacturerFilterItem;
import com.playbazar.models.ModelFilterItem;
import com.playbazar.models.RESTApiGenericResponse;
import com.playbazar.networking.ApiRepo;
import com.playbazar.networking.NetworkError;
import com.playbazar.views.BrowseCarsView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Presenter implementation which handles core features.
 */

public class BrowseCarsPresenterImpl implements BrowseCarsPresenter, ApiRepo.RESTApiCallback {

	private static BrowseCarsPresenterImpl mInstance;
	private BrowseCarsView mView;

	@Inject
	ApiRepo mApiRepo;

	private boolean mIsLoading;
	private ViewState mViewState = ViewState.MANUFACTURERS;
	private CompositeSubscription subscriptions;
	private String mSelectedManufacturer;
	private String mSelectedModel;

	@Inject
	public BrowseCarsPresenterImpl(ApiRepo apiRepo) {
		mApiRepo = apiRepo;
		this.subscriptions = new CompositeSubscription();
	}

	@Override
	public void setView(BrowseCarsView browseCarsView) {
		mView = browseCarsView;
		subscriptions = new CompositeSubscription();
		mViewState = ViewState.MANUFACTURERS;
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
		if(subscriptions != null) {
			subscriptions.unsubscribe();
		}
	}

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	@Override
	public void loadPage(int pageNo) {
		mIsLoading = true;
		Subscription subscription = null;
		switch (mViewState) {
			case MANUFACTURERS: {
				subscription = mApiRepo.getManufacturerList(pageNo, this);
			}
			break;

			case MODELS: {
				subscription = mApiRepo.getModelList(pageNo, mSelectedManufacturer, this);

			}
			break;

			case YEAR: {
				subscription = mApiRepo.getModelYearList(mSelectedManufacturer, mSelectedModel, this);
			}
			break;
		}
		subscriptions.add(subscription);
	}

	@Override
	public void onDataItemClicked(String id, String value) {
		if(mView != null) {
			switch (mViewState) {
				case MANUFACTURERS: {
					mSelectedManufacturer = id;
					mView.onManufacturerSelected(new ManufacturerFilterItem(id, value));
				}
				break;

				case MODELS: {
					mSelectedModel = id;
					mView.onModelSelected(new ModelFilterItem(id, value));
				}
				break;

				case YEAR: {

				}
				break;
			}
		}
	}

	@Override
	public void changeState(ViewState viewState) {
		mViewState = viewState;
	}

	@Override
	public void onSuccess(RESTApiGenericResponse RESTApiGenericResponse) {
		mIsLoading = false;
		if (mView != null) {
			mView.onDataReceived(RESTApiGenericResponse);
		}
	}

	@Override
	public void onError(NetworkError networkError) {
		mIsLoading = false;
		if (mView != null) {
			mView.onFailure(networkError.getAppErrorMessage());
		}
	}
}
