package com.playbazar.networking;

/**
 * Created by pvkarthik on 2016-12-04.
 *
 * Dagger module which is responsible for providing objects injected variables.
 */

import com.playbazar.BuildConfig;
import com.playbazar.PlayBazarApplication;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetworkModule {
	private final PlayBazarApplication application;

	public NetworkModule(PlayBazarApplication application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Retrofit provideCall() {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.readTimeout(5, TimeUnit.SECONDS)
				.connectTimeout(5, TimeUnit.SECONDS)
				.build();

		return new Retrofit.Builder()
				.baseUrl(BuildConfig.BASEURL)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}

	@Provides
	@Singleton
	@SuppressWarnings("unused")
	public RestService providesRestService(
			Retrofit retrofit) {
		return retrofit.create(RestService.class);
	}

	@Provides
	@Singleton
	@SuppressWarnings("unused")
	public ApiRepo providesService(
			RestService networkService) {
		return new ApiRepo(networkService);
	}

}

