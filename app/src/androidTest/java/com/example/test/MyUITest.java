package com.example.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*;

import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.contrib.RecyclerViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
public class MyUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "Клик на дочерний элемент";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                if (v != null) {
                    v.performClick();
                }
            }
        };
    }

    @Test
    public void tc001_testSearchById() {
        onView(withId(R.id.searchText))
                .perform(typeText("683571"));
        onView(withId(R.id.searchButton)).perform(click());
    }
    @Test
    public void tc002_testBloodSearch(){
        SystemClock.sleep(1000);

        onView(withId(R.id.componentSpinner)).perform(click());
        onData(hasToString("Кровь"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.groupSpinner)).perform(click());
        onData(hasToString("O(I)"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.rhSpinner)).perform(click());
        onData(hasToString("Rh+"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.searchButton)).perform(click());
    }
    @Test
    public void tc003_testPlasmaSearch(){

        onView(withId(R.id.componentSpinner)).perform(click());
        onData(hasToString("Плазма"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());


        onView(withId(R.id.groupSpinner)).perform(click());
        onData(hasToString("O(I)"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.searchButton)).perform(click());
    }
    @Test
    public void tc004_testDonorIdUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));
        onView(withId(R.id.id)).perform(replaceText("850291"));
        SystemClock.sleep(1000);
        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc005_testDonorIdNullUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));
        onView(withId(R.id.id)).perform(replaceText(""));

        SystemClock.sleep(1000);
        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc006_testStatusUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));

        onView(withId(R.id.status)).perform(click());
        onData(anything())
                .inRoot(RootMatchers.isPlatformPopup())
                .atPosition(1)
                .perform(click());

        SystemClock.sleep(1000);
        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc007_testRhesusUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));

        onView(withId(R.id.rh)).perform(click());
        onData(hasToString("Rh+"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        SystemClock.sleep(1000);
        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc008_testComponentUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));

        onView(withId(R.id.component)).perform(click());
        onData(hasToString("Плазма"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        SystemClock.sleep(1000);
        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc009_testDonationDataUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));

        SystemClock.sleep(1000);
        onView(withText("Изменить дату ")).perform(click());

        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 5, 2));

        onView(withId(android.R.id.button1)).perform(click());


        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc010_testConserveDataUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));

        SystemClock.sleep(1000);
        onView(withText("Изменить дату")).perform(click());

        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 5, 2));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc011_testDateCheckUpdate(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));

        SystemClock.sleep(1000);
        onView(withText("Изменить дату ")).perform(click());

        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 5, 20));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(android.R.id.button1)).perform(click());

    }
    @Test
    public void tc012_testDelete(){
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(0));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.buttonEdit)));

        SystemClock.sleep(1000);
        onView(withId(android.R.id.button3)).perform(click());

    }
    @Test
    public void tc013_testAdd(){
        onView(withId(R.id.addButton)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.donorIdText))
                .perform(typeText("683572"));

        onView(withId(R.id.compSpinner)).perform(click());
        onData(hasToString("Плазма"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.grpSpinner)).perform(click());
        onData(hasToString("AB(IV)"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.rhesusSpinner)).perform(click());
        onData(hasToString("Rh+"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(android.R.id.button1)).perform(click());
    }
    @Test
    public void tc014_testAddWithNull(){
        SystemClock.sleep(1000);
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }
    @Test
    public void tc015_testNoWifi(){
        onView(withId(R.id.comp_text))
                .check(matches(withText("Отсутствует интернет")));
    }
    @Test
    public void tc016_testDarkMode(){

        onView(withId(R.id.comp_text))
                .check(matches(withText("Поиск по совместимости")));

        onView(withId(R.id.componentSpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.groupSpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.rhSpinner)).check(matches(isDisplayed()));

        onView(withId(R.id.addButton)).check(matches(isDisplayed()));

    }
}
