package com.kevinj1008.fitnessch;

import android.support.v4.app.FragmentManager;

import com.kevinj1008.fitnessch.activities.FitnesschActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FitnesschTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPresenter() {
        FitnesschActivity activity = mock(FitnesschActivity.class);
        FragmentManager fragmentManager = mock(FragmentManager.class);
        FitnesschPresenter presenter = new FitnesschPresenter(activity, fragmentManager);
        presenter.refreshAllUi();

        verify(activity, times(1)).setPresenter(presenter);
    }
}
