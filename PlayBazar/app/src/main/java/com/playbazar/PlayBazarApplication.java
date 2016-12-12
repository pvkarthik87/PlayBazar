package com.playbazar;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Application which will initialize DI graph.
 */

import android.app.Application;

import com.playbazar.di.components.ApplicationComponent;
import com.playbazar.di.components.DaggerApplicationComponent;
import com.playbazar.di.modules.ApplicationModule;
import com.playbazar.networking.NetworkModule;

public class PlayBazarApplication extends Application {

	private static ApplicationComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeInjector();
	}

	private void initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.networkModule(new NetworkModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public static ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}
}

