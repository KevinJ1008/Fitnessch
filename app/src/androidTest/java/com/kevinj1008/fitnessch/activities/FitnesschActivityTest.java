package com.kevinj1008.fitnessch.activities;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevinj1008.fitnessch.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

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

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}