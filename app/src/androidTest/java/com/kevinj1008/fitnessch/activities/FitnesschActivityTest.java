package com.kevinj1008.fitnessch.activities;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.kevinj1008.fitnessch.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.kevinj1008.fitnessch.EspressoTestUtil.atPosition;

public class FitnesschActivityTest {

    @Rule
    public ActivityTestRule<FitnesschActivity> activityTestRule = new ActivityTestRule<>(FitnesschActivity.class, true, true);

    @Test
    public void onCreate() {
        SystemClock.sleep(8000);
        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.recyclerview_main))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(isRoot()).perform(pressBack());
            onView(withId(R.id.recyclerview_main))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
            onView(isRoot()).perform(pressBack());
        }

        onView(withId(R.id.recyclerview_main))
                .perform(scrollToPosition(0))
                .check(matches(atPosition(0, isDisplayed())));
    }

}