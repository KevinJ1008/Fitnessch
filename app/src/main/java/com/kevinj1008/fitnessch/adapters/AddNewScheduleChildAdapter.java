package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.addnewschedulechild.AddNewScheduleChildContract;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddNewScheduleChildAdapter extends RecyclerView.Adapter {

    private List<Schedule> mSchedules;
    private Map<String, List<Schedule>> mStringListMap;
    private AddNewScheduleChildContract.Presenter mPresenter;

    public AddNewScheduleChildAdapter(List<Schedule> schedule) {
        this.mSchedules = schedule;
        mStringListMap = new HashMap<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEWTYPE_ADDNEW_SCHEDULE_TITLE) {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnewschedule_title, parent, false);
            return new ScheduleTitleItemViewHolder(view);
        } else {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnewschedule_content, parent, false);
            return new ScheduleContentItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((ScheduleTitleItemViewHolder) holder).mSeparator.setVisibility(View.INVISIBLE);
        }
        if (holder instanceof ScheduleTitleItemViewHolder) {
            ((ScheduleTitleItemViewHolder) holder).mScheduleTitle.setText(mSchedules.get(position).getScheduleTitle());
        } else if (holder instanceof ScheduleContentItemViewHolder){
            ((ScheduleContentItemViewHolder) holder).mScheduleWeight.setText(mSchedules.get(position).getScheduleWeight());
            ((ScheduleContentItemViewHolder) holder).mScheduleReps.setText(mSchedules.get(position).getScheduleReps());
        }

    }

    @Override
    public int getItemCount() {
        return (mSchedules.isEmpty()) ? 0 : mSchedules.size();
    }

    @Override
    public int getItemViewType(int position) {
        String type = mSchedules.get(position).getType();
         if (type.equals("CONTENT")) {
             return Constants.VIEWTYPE_ADDNEW_SCHEDULE_CONTENT;
        } else {
             return Constants.VIEWTYPE_ADDNEW_SCHEDULE_TITLE;
        }
    }

    private class ScheduleTitleItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mScheduleTitle;
        private View mSeparator;

        public ScheduleTitleItemViewHolder(View itemView) {
            super(itemView);

            mScheduleTitle = itemView.findViewById(R.id.addnew_schedule_item_title);
            mSeparator = itemView.findViewById(R.id.schedule_separator);
        }
    }

    private class ScheduleContentItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mScheduleWeight;
        private TextView mScheduleReps;

        public ScheduleContentItemViewHolder(View itemView) {
            super(itemView);
            mScheduleWeight = itemView.findViewById(R.id.addnew_weight_content);
            mScheduleReps = itemView.findViewById(R.id.addnew_reps_content);
        }
    }

    public void updateData(Schedule schedules) {
//        mSchedules = new ArrayList<>();
        mSchedules.clear();

        String title = schedules.getScheduleTitle();
        if (mStringListMap.containsKey(title)) {
            List<Schedule> content = mStringListMap.get(title);
            content.add(schedules);
            mStringListMap.put(title, content);
        } else {
            List<Schedule> titleList = new ArrayList<>();
            Schedule titleItem = new Schedule(schedules.getScheduleTitle(), "", "");
            titleItem.setType("TITLE");

            titleList.add(titleItem);
            titleList.add(schedules);

            mStringListMap.put(title, titleList);
        }
        Iterator iterator = mStringListMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            for (int i = 0 ; i < mStringListMap.get(key).size() ; ++i) {
                mSchedules.add(mStringListMap.get(key).get(i));
            }
        }
        notifyDataSetChanged();
    }


    public List<Schedule> getScheduleList() {
        return mSchedules;
    }

    public void clearData() {
//        int size = mSchedules.size();
        mSchedules.clear();
//        notifyItemRangeRemoved(0, size);
        notifyDataSetChanged();
    }

}
