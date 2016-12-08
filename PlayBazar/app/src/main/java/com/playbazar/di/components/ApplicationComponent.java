package com.playbazar.di.components;

import android.content.Context;

import com.playbazar.PlayBazarApplication;
import com.playbazar.networking.NetworkModule;
import com.playbazar.views.activities.BaseActivity;
import com.playbazar.views.activities.BrowseCarsActivity;
import com.playbazar.di.modules.ApplicationModule;
import com.playbazar.views.adapters.CarsDataAdapter;
import com.playbazar.views.fragments.BrowseCarsFragment;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

	void inject(PlayBazarApplication playBazarApplication);

	void inject(BaseActivity baseActivity);

	void inject(BrowseCarsActivity browseCarActivity);

	void inject(BrowseCarsFragment browseCarsFragment);

	void inject(CarsDataAdapter carsDataAdapter);
	
	//Exposed to sub-graphs.
	Context context();
}
