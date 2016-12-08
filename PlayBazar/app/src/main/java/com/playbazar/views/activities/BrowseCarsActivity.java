package com.playbazar.views.activities;

import android.os.Bundle;

import com.playbazar.R;
import com.playbazar.di.HasComponent;
import com.playbazar.di.components.ApplicationComponent;

public class BrowseCarsActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_cars);
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
