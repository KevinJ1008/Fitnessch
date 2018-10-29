package com.kevinj1008.fitnessch.adapters;

import com.kevinj1008.fitnessch.objects.Schedule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddNewScheduleChildAdapterTest {

    @Mock
    private ArrayList mMockList;

    private Map<String, List<Schedule>> mStringListMap;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mStringListMap = new HashMap<>();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void updateData() {
        mMockList.clear();
        Schedule schedule = mock(Schedule.class);
        Schedule titleItem = mock(Schedule.class);
        when(schedule.getScheduleTitle()).thenReturn("槓鈴握推");
        String title = schedule.getScheduleTitle();

        if (mStringListMap.containsKey(title)) {
            List<Schedule> content = mStringListMap.get(title);
            content.add(schedule);
            mStringListMap.put(title, content);
        } else {
            List<Schedule> titleList = new ArrayList<>();
            titleItem.setScheduleTitle(title);
            titleItem.setType("TITLE");

            titleList.add(titleItem);
            titleList.add(schedule);
            mStringListMap.put(title, titleList);
        }
        Iterator iterator = mStringListMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            for (int i = 0; i < mStringListMap.get(key).size(); i++) {
                mMockList.add(mStringListMap.get(key).get(i));
            }
        }

        verify(titleItem).setScheduleTitle(title);
        verify(mMockList).clear();
        verify(schedule).getScheduleTitle();
        Assert.assertEquals("槓鈴握推", title);

    }

    @Test
    public void getNewScheduleList() {
    }
}