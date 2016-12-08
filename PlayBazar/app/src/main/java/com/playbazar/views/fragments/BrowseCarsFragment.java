package com.playbazar.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playbazar.R;
import com.playbazar.di.components.ApplicationComponent;
import com.playbazar.logging.DefaultLogger;
import com.playbazar.models.FilterItem;
import com.playbazar.models.ManufacturerFilterItem;
import com.playbazar.models.ModelFilterItem;
import com.playbazar.models.ServerAPIResponse;
import com.playbazar.presenters.BrowseCarsPresenter;
import com.playbazar.presenters.ViewState;
import com.playbazar.views.BrowseCarsView;
import com.playbazar.views.adapters.CarsDataAdapter;
import com.wefika.flowlayout.FlowLayout;

import javax.inject.Inject;

import butterknife.Bind;

public class BrowseCarsFragment extends BaseFragment implements BrowseCarsView {

	private static final String TAG = DefaultLogger.makeLogTag(BrowseCarsFragment.class);

	@Bind(R.id.cars_data_list)
	RecyclerView mCarsDataView;

	@Bind(R.id.cars_metadata_list)
	FlowLayout mCarsMetaDataView;

	@Inject
	BrowseCarsPresenter mBrowseCarsPresenter;

	private CarsDataAdapter mAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_browse_cars, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpPresenter();
		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		mCarsDataView.setLayoutManager(layoutManager);
		mAdapter = new CarsDataAdapter(getContext(), mCarsDataView);
		mCarsDataView.setAdapter(mAdapter);
		mAdapter.enableLoadMore();
	}

	private void setUpPresenter() {
		mBrowseCarsPresenter.setView(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mBrowseCarsPresenter.onStart();
		mAdapter.loadData();
	}

	@Override
	public void onResume() {
		super.onResume();
		mBrowseCarsPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mBrowseCarsPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mBrowseCarsPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mBrowseCarsPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mCarsDataView.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(ServerAPIResponse serverAPIResponse) {
		mAdapter.addData(serverAPIResponse);
	}

	@Override
	public void onFailure(String errorMsg) {

	}

	@Override
	public void onManufacturerSelected(ManufacturerFilterItem manufacturerFilterItem) {
		addSelectedFilter(manufacturerFilterItem);

		mBrowseCarsPresenter.changeState(ViewState.MODELS);
		mAdapter.clearData();
		mAdapter.enableLoadMore();
		mAdapter.loadData();
	}

	@Override
	public void onModelSelected(ModelFilterItem modelFilterItem) {
		addSelectedFilter(modelFilterItem);
		mBrowseCarsPresenter.changeState(ViewState.YEAR);
		mAdapter.clearData();
		mAdapter.enableLoadMore();
		mAdapter.loadData();
	}

	private void addSelectedFilter(FilterItem filterItem) {
		View view = LayoutInflater.from(mCarsMetaDataView.getContext())
				.inflate(R.layout.view_selected_filter, mCarsMetaDataView, false);
		TextView filterTxtView = (TextView) view.findViewById(R.id.filter_name);
		filterTxtView.setText(filterItem.getText());
		TextView removeBtn = (TextView) view.findViewById(R.id.removeBtn);
		removeBtn.setTag(filterItem);
		removeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FilterItem filterItem = (FilterItem) view.getTag();
				switch (filterItem.getType()) {
					case MANUFACTURER: {
						mBrowseCarsPresenter.changeState(ViewState.MANUFACTURERS);
						mAdapter.clearData();
						mAdapter.enableLoadMore();
						mAdapter.loadData();
						mCarsMetaDataView.removeAllViews();
					}
					break;

					case MODEL: {
						mBrowseCarsPresenter.changeState(ViewState.MODELS);
						mAdapter.clearData();
						mAdapter.enableLoadMore();
						mAdapter.loadData();
						mCarsMetaDataView.removeViewAt(1);
					}
					break;
				}
			}
		});
		mCarsMetaDataView.addView(view);
	}
}
