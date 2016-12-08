package com.playbazar.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;
import com.playbazar.PlayBazarApplication;
import com.playbazar.R;
import com.playbazar.config.Constants;
import com.playbazar.models.ServerAPIResponse;
import com.playbazar.presenters.BrowseCarsPresenter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CarsDataAdapter extends RecyclerView.Adapter<CarsDataViewHolder> {

	private static final int VIEW_TYPE_ODD = 100;
	private static final int VIEW_TYPE_EVEN = 101;

	private Context mContext;
	private Map<String, String> mDataMap;
	private List<String> mDataList;
	private RecyclerView mRecyclerView;
	private Paginate mPaginate;
	private int mMaxPages = Integer.MAX_VALUE;
	private int mLastPosition;

	@Inject
	BrowseCarsPresenter mBrowseCarsPresenter;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			//int itemPosition = mRecyclerView.getChildLayoutPosition(view);
			String item = mDataList.get(itemPosition);
			//Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
			mBrowseCarsPresenter.onDataItemClicked(item, mDataMap.get(item));
		}
	};

	public CarsDataAdapter(Context ctx, RecyclerView recyclerView) {
		PlayBazarApplication.getApplicationComponent().inject(this);
		mContext = ctx;
		mDataMap = new LinkedHashMap<>();
		mDataList = new ArrayList<>(4);
		mRecyclerView = recyclerView;
	}

	@Override
	public CarsDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view;
		if(viewType == VIEW_TYPE_EVEN)
			view = LayoutInflater.from(mContext).inflate(R.layout.view_even_car_data_item, parent, false);
		else
			view = LayoutInflater.from(mContext).inflate(R.layout.view_odd_car_data_item, parent, false);
		return new CarsDataViewHolder(view);
	}

	@Override
	public void onBindViewHolder(CarsDataViewHolder holder, int position) {
		if(position < mDataList.size()) {
			String dataValue = mDataMap.get(mDataList.get(position));
			if(!TextUtils.isEmpty(dataValue)) {
				holder.dataTxtView.setText(dataValue);
			} else {
				holder.dataTxtView.setText("");
			}
		}
		holder.itemView.setTag(position);
		holder.itemView.setOnClickListener(mOnClickListener);
		/*if(position > mLastPosition) {
			Animation animation = AnimationUtils.loadAnimation(mContext,
					R.anim.up_bottom);
			holder.itemView.startAnimation(animation);
			mLastPosition = position;
		}*/
	}

	@Override
	public int getItemCount() {
		return mDataList.size();
	}

	@Override
	public int getItemViewType(int position) {
		if(position%2 == 0) return VIEW_TYPE_EVEN;
		return VIEW_TYPE_ODD;
	}

	public void addData(ServerAPIResponse serverAPIResponse) {
		if(serverAPIResponse != null) {
			Map<String, String> serverNewData = serverAPIResponse.getWkda();
			if(serverNewData != null) {
				if(serverAPIResponse.getTotalPageCount() != null) {
					mMaxPages = serverAPIResponse.getTotalPageCount();
				} else {
					mMaxPages = 1;
				}
				mDataMap.putAll(serverNewData);
				int oldSize = mDataList.size();
				mDataList.clear();
				mDataList.addAll(mDataMap.keySet());
				int newSize = mDataList.size();
				Integer currentPage = serverAPIResponse.getPage();
				if(currentPage != null && currentPage >= 1 ) {
					notifyItemRangeInserted(oldSize, newSize - oldSize);
				} else {
					notifyDataSetChanged();
				}
			}
		}
	}

	public void enableLoadMore() {
		 mPaginate = Paginate.with(mRecyclerView, mPaginationCallback)
				.addLoadingListItem(true)
				.setLoadingListItemCreator(new CustomLoadingListItemCreator())
				.build();
	}

	private Paginate.Callbacks mPaginationCallback = new Paginate.Callbacks() {
		@Override
		public void onLoadMore() {
			// Load next page of data (e.g. network or database)
			loadData();
		}

		@Override
		public boolean isLoading() {
			// Indicate whether new page loading is in progress or not
			return mBrowseCarsPresenter.isLoading();
		}

		@Override
		public boolean hasLoadedAllItems() {
			// Indicate whether all data (pages) are loaded or not
			return isAllPagesLoaded();
		}
	};

	private class CustomLoadingListItemCreator implements LoadingListItemCreator {
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.view_loading, parent, false);
			return new LoadingViewHolder(view);
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			// Bind custom loading row if needed
		}
	}

	public void loadData() {
		if(!isAllPagesLoaded()) {
			mBrowseCarsPresenter.loadPage(getReqPageNumber());
		}
	}

	private int getReqPageNumber() {
		int nextPageNumber = mDataList.size() / Constants.NUM_ITEMS_IN_PAGE;
		if(mDataList.size()%Constants.NUM_ITEMS_IN_PAGE > 0) {
			nextPageNumber++;
		}
		return nextPageNumber;
	}

	public void clearData() {
		mDataList.clear();
		mDataMap.clear();
		if(mPaginate != null) {
			mPaginate.unbind();
		}
		mMaxPages = Integer.MAX_VALUE;
		mLastPosition = -1;
	}

	private boolean isAllPagesLoaded() {
		if(mMaxPages > 0)
			return getReqPageNumber() >= mMaxPages;
		else
			return true;
	}
}
