package com.playbazar.presenters;

import com.playbazar.mvputils.Presenter;
import com.playbazar.views.BrowseCarsView;

/**
 * Created by pvkarthik on 2016-12-05.
 */

public interface BrowseCarsPresenter extends Presenter {

	void setView(BrowseCarsView browseCarsView);

	boolean isLoading();

	void loadPage(int pageNo);

	void onDataItemClicked(String id, String value);

	void changeState(ViewState viewState);

}
