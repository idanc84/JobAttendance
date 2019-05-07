package com.example.jobattendance.ui.statistics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jobattendance.R;
import com.example.jobattendance.data.model.Attendance;
import com.example.jobattendance.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsAdapter extends RecyclerView.Adapter {

    private StatisticsPresenter mPresenter;
    private Context mCtx;

    public StatisticsAdapter(Context ctx, StatisticsPresenter presenter) {
        mCtx = ctx;
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext() );
        View row = inflater.inflate( R.layout.attendance_item_1, viewGroup,false );

        return new AttendanceViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AttendanceViewHolder holder = (AttendanceViewHolder)viewHolder;

        mPresenter.bindAttendanceItemView(i,holder);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getItemsSize();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder implements StatisticsContract.AttendanceItem{

        @BindView(R.id.textView_date)
        TextView textView_date;
        @BindView(R.id.textView_totaltime)
        TextView textView_totalTime;

        public AttendanceViewHolder( View view ) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void bind(Attendance attendance) {
            textView_date.setText(attendance.getDate());
            textView_totalTime.setText(TimeUtils.msToString(attendance.getTotalTime()));
        }
    }
}
