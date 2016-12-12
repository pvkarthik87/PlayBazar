package com.playbazar;

import com.playbazar.models.RESTApiGenericResponse;
import com.playbazar.networking.ApiRepo;
import com.playbazar.presenters.BrowseCarsPresenter;
import com.playbazar.presenters.BrowseCarsPresenterImpl;
import com.playbazar.views.BrowseCarsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class BrowseCarsPresenterTest {

	@Mock
	private ApiRepo model;

	@Mock
	private BrowseCarsView view;

	private BrowseCarsPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = new BrowseCarsPresenterImpl(model);
		presenter.setView(view);
		/*
			Define the desired behaviour.

			Queuing the action in "doAnswer" for "when" is executed.
			Clear and synchronous way of setting reactions for actions (stubbing).
			*/
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				RESTApiGenericResponse RESTApiGenericResponse = new RESTApiGenericResponse();
				Map<String, String> dataMap = new HashMap<>(4);
				RESTApiGenericResponse.setWkda(dataMap);
				((ApiRepo.RESTApiCallback) presenter).onSuccess(RESTApiGenericResponse);
				return Mockito.mock(Subscription.class);
			}
		}).when(model).getManufacturerList(0, (ApiRepo.RESTApiCallback) presenter);
	}

	/**
	 Verify if model.getManufacturerList was called once.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchAll() {
		presenter.loadPage(0);
		// verify can be called only on mock objects
		verify(model, times(1)).getManufacturerList(0, (ApiRepo.RESTApiCallback) presenter);
		verify(view, times(1)).onDataReceived(any(RESTApiGenericResponse.class));
	}

}
