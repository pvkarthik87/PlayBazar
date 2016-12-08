package com.playbazar.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.playbazar.PlayBazarApplication;
import com.playbazar.di.components.ApplicationComponent;
import com.playbazar.di.modules.ActivityModule;
import com.playbazar.logging.DefaultLogger;

import butterknife.ButterKnife;

/**
 * BaseActivity will be extended by every activity in the app, and it hides
 * common logic for concrete activities, like initial dependency and view injections
 * <p/>
 */
public abstract class BaseActivity extends AppCompatActivity {

	protected boolean isActivityVisible;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationComponent().inject(this);
		injectComponent(getApplicationComponent());
	}

	protected void onResume() {
		super.onResume();
		isActivityVisible = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isActivityVisible = false;
	}

	@Override
	public void setContentView(int layoutResID) {
		View view = getLayoutInflater().inflate(layoutResID, null);
		super.setContentView(view);
		bindViews(view);
	}

	/**
	 * Get the Main Application component for dependency injection.
	 *
	 */
	protected ApplicationComponent getApplicationComponent() {
		return ((PlayBazarApplication) getApplication()).getApplicationComponent();
	}

	/**
	 * Get an Activity module for dependency injection.
	 *
	 */
	protected ActivityModule getActivityModule() {
		return new ActivityModule(this);
	}

	protected abstract void injectComponent(ApplicationComponent component);

	@Override
	protected void onDestroy() {
		unBindViews();
		super.onDestroy();
	}

	public void showDialog(DialogFragment fragment, String tag) {
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		Fragment prev = fragmentManager.findFragmentByTag(tag);
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		try {
			fragment.show(ft, tag);
			DefaultLogger.d("", "showDialog - " + tag);
		} catch (IllegalStateException e) {
			DefaultLogger.e("", "showDialog - could not show dialog - " + tag + ": ", e);
		}
	}

	private void bindViews(View view) {
		ButterKnife.bind(this, view);
	}

	private void unBindViews() {
		ButterKnife.unbind(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}
}
