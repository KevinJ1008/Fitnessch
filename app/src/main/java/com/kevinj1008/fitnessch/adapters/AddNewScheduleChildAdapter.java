package com.kevinj1008.fitnessch.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinj1008.fitnessch.Fitnessch;
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
    private Context mContext;
    private Map<String, List<Schedule>> mStringListMap;
    private AddNewScheduleChildContract.Presenter mPresenter;

    public AddNewScheduleChildAdapter(List<Schedule> schedule, Context context) {
        this.mSchedules = schedule;
        mStringListMap = new HashMap<>();
        this.mContext = context;
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
        if (holder instanceof ScheduleTitleItemViewHolder) {
            if (position == 0) {
                ((ScheduleTitleItemViewHolder) holder).mSeparator.setVisibility(View.INVISIBLE);
            } else {
                ((ScheduleTitleItemViewHolder) holder).mSeparator.setVisibility(View.VISIBLE);
            }
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
         if ("CONTENT".equals(type)) {
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

    private class ScheduleContentItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView mScheduleWeight;
        private TextView mScheduleReps;
        private ConstraintLayout mScheduleContentContainer;

        public ScheduleContentItemViewHolder(View itemView) {
            super(itemView);
            mScheduleWeight = itemView.findViewById(R.id.addnew_weight_content);
            mScheduleReps = itemView.findViewById(R.id.addnew_reps_content);
            mScheduleContentContainer = itemView.findViewById(R.id.add_new_schedule_container);

            mScheduleContentContainer.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            String type = mSchedules.get(getAdapterPosition()).getType();
            if (view.getId() == R.id.add_new_schedule_container) {
                if ("CONTENT".equals(type)) {
                    deleteDialog(getAdapterPosition());
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    private void removeItem(int position) {
        String title = mSchedules.get(position).getScheduleTitle();
        if (mStringListMap.get(title).size() == 2) {
            mStringListMap.remove(title);
            for (int i = position; i >= position - 1; i--) {
                mSchedules.remove(i);
            }
        } else {
            mStringListMap.get(title).remove(mSchedules.get(position));
            mSchedules.remove(position);
        }
        notifyDataSetChanged();
    }

    private void deleteDialog(final int position) {
        AlertDialog.Builder dialogAlert = new AlertDialog.Builder(mContext);
        dialogAlert.setTitle("確定刪除？");
        dialogAlert.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position);
            }
        });
        dialogAlert.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialogAlert.show();
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
            Schedule titleItem = new Schedule();
            titleItem.setScheduleTitle(schedules.getScheduleTitle());
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
        Log.d(Constants.TAG, "Check Schedule size " + mSchedules.size());
        notifyDataSetChanged();
    }


    public List<Schedule> getScheduleList() {
        return mSchedules;
    }

    public Map<String, List<Schedule>> getScheduleMap() {
        return mStringListMap;
    }

    public List<Schedule> getNewScheduleList() {
        Iterator iterator = mStringListMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            for (int i = 0 ; i < mStringListMap.get(key).size() ; ++i) {
                mSchedules.add(mStringListMap.get(key).get(i));
            }
        }
        return mSchedules;
    }

    public void clearData() {
//        int size = mSchedules.size();
        mSchedules.clear();
        mStringListMap.clear();
//        notifyItemRangeRemoved(0, size);
        notifyDataSetChanged();
    }

}
