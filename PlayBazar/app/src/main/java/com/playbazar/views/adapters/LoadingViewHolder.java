package com.playbazar.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.playbazar.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {

	public TextView dataTxtView;

	public LoadingViewHolder(View itemView) {
		super(itemView);
		dataTxtView = (TextView) itemView.findViewById(R.id.data);
	}

}
