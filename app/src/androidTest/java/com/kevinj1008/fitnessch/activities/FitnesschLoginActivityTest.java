package com.kevinj1008.fitnessch.activities;

import android.support.test.rule.ActivityTestRule;

import com.kevinj1008.fitnessch.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class FitnesschLoginActivityTest {

    @Rule
    public ActivityTestRule<FitnesschLoginActivity> loginActivityTestRule = new ActivityTestRule<>(FitnesschLoginActivity.class, true, true);

    @Test
    public void onCreate() {
        onView(withId(R.id.google_login_btn))
                .perform(click());
    }
}