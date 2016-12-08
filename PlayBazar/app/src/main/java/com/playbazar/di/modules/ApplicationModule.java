package com.playbazar.di.modules;

import android.content.Context;

import com.playbazar.PlayBazarApplication;
import com.playbazar.networking.ApiRepo;
import com.playbazar.presenters.BrowseCarsPresenter;
import com.playbazar.presenters.BrowseCarsPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
	private final PlayBazarApplication application;

	public ApplicationModule(PlayBazarApplication application) {
		this.application = application;
	}

	@Provides @Singleton
	Context provideApplicationContext() {
		return this.application;
	}

	@Provides @Singleton
	BrowseCarsPresenter provideGifsGalleryPresenter(ApiRepo apiRepo) {
		return new BrowseCarsPresenterImpl(apiRepo);
	}
}
