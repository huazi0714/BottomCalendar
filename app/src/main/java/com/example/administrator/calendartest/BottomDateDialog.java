package com.example.administrator.calendartest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 石志华 on 2016/3/3.
 */
public class BottomDateDialog extends Dialog{

    public BottomDateDialog(Context context) {
        super(context);
    }

    public static class Builder implements View.OnClickListener, AdapterView.OnItemClickListener {
        private Activity context;
        private BottomDialog dialog;
        private int currentMonth;//记录当前显示的是哪个月份
        private BottomDateCalendarAdapter calendarAdapter;
        private int dayOfMonth;
        private int dayOfWeekInMonth;
        private TextView tv_title;
        private List<String> list;
        private int currentYear;//当前真实年份
        private int realCurrentMonth;//记录当前真实月份
        private boolean isBeforeCurrentMonth=true;
        private boolean isCurrentMonth=true;
        private OnDialogClick onDialogClick;
        private String selectedDate="";
        private int currentShowYear;

        public Builder(Activity context,String selectedDate) {
            this.context = context;
            this.selectedDate=selectedDate;
        }

        public BottomDialog create() {
            BottomDialog.Builder builder = new BottomDialog.Builder(context);
            View inflate = View.inflate(context, R.layout.bottom_date_dialog, null);
            View tv_cancel = inflate.findViewById(R.id.tv_cancel);
            View iv_left = inflate.findViewById(R.id.iv_left);
            View iv_right = inflate.findViewById(R.id.iv_right);
            tv_title = (TextView) inflate.findViewById(R.id.tv_title);
            GridView grideView = (GridView) inflate.findViewById(R.id.gridView);
            grideView.setOnItemClickListener(this);
            //获取当前日期
            Calendar instance = Calendar.getInstance();
            currentYear = instance.get(Calendar.YEAR);
            currentShowYear=currentYear;
            currentMonth = instance.get(Calendar.MONTH);
            currentMonth=currentMonth+1;
            realCurrentMonth=currentMonth;
            dayOfMonth = instance.get(Calendar.DAY_OF_MONTH);

            //设置选中日期
            if(selectedDate==null||"".equals(selectedDate)){
                isCurrentMonth=true;
            }else{
                String[] split = selectedDate.split("-");
                if(currentYear==Integer.valueOf(split[0]) && currentMonth==Integer.valueOf(split[1])){
                    isCurrentMonth=true;
                }else{
                    isCurrentMonth=false;
                }

                currentYear=Integer.valueOf(split[0]);
                currentMonth = Integer.valueOf(split[1]);
                instance.set(Calendar.YEAR,Integer.valueOf(split[0]));
                instance.set(Calendar.MONTH,Integer.valueOf(split[1])-1);
            }
            //获取当月第一天是周几
            instance.set(Calendar.DAY_OF_MONTH, 1);
            dayOfWeekInMonth = instance.get(Calendar.DAY_OF_WEEK);
            tv_title.setText(currentYear + context.getResources().getString(R.string.text_year) + currentMonth + context.getResources().getString(R.string.text_month));

            list = new ArrayList<>();
            setDays(currentYear,currentMonth);
            calendarAdapter = new BottomDateCalendarAdapter(context,list,dayOfWeekInMonth,dayOfMonth,isBeforeCurrentMonth,isCurrentMonth);
            String[] split = selectedDate.split("-");
            if(Integer.valueOf(split[0])==currentYear && Integer.valueOf(split[1])==currentMonth){
                calendarAdapter.setSelectedDay(Integer.valueOf(split[2]));
            }else{
                calendarAdapter.setSelectedDay(0);
            }
            grideView.setAdapter(calendarAdapter);

            tv_cancel.setOnClickListener(this);
            iv_left.setOnClickListener(this);
            iv_right.setOnClickListener(this);

            dialog = builder.create();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = ScreenUtils.getScreenWidth(context);
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setWindowAnimations(R.style.AnimationPreview);
            window.setGravity(Gravity.BOTTOM);
            dialog.setContentView(inflate);
            return dialog;
        }

        public void setOnDialogClick(OnDialogClick onDialogClick){
            this.onDialogClick=onDialogClick;
        }

        @NonNull
        private void setDays(int currentYear,int currentMonth) {
            list.clear();
            if(currentMonth ==1|| currentMonth ==3|| currentMonth ==5|| currentMonth ==7|| currentMonth ==8|| currentMonth ==10|| currentMonth ==12){
                setBigMonth(list);
            }else if(currentMonth ==2){
                set2Month(list, currentYear);
            }else{
                setSmallMonth(list);
            }
        }


        private void setSmallMonth(List<String> list) {
            for(int i=0;i<30;i++){
                list.add((i+1)+"");
            }
        }

        private void setBigMonth(List<String> list) {
            for(int i=0;i<31;i++){
                list.add((i+1)+"");
            }
        }

        private void set2Month(List<String> list,int currentYear) {
            if(currentYear%4==0){
                for(int i=0;i<29;i++){
                    list.add((i+1)+"");
                }
            }else{
                for(int i=0;i<28;i++){
                    list.add((i+1)+"");
                }

            }
        }

        @Override
        public void onClick(View v) {
            int year=0;
            switch (v.getId()){
                case R.id.tv_cancel:
                    dialog.dismiss();
                    break;
                case R.id.iv_left:
                    year = setDate(-1,currentShowYear);
                    setDays(year,currentMonth);
                    tv_title.setText(year + context.getResources().getString(R.string.text_year) + currentMonth + context.getResources().getString(R.string.text_month));
                    calendarAdapter.notifyDataSetChanged();
                    break;
                case R.id.iv_right:
                    year = setDate(1,currentShowYear);
                    setDays(year,currentMonth);
                    tv_title.setText(year + context.getResources().getString(R.string.text_year) + currentMonth + context.getResources().getString(R.string.text_month));
                    calendarAdapter.notifyDataSetChanged();
                    break;

            }
        }


        public int setDate(int addOrDown,int year){
            Calendar instance = Calendar.getInstance();
            currentMonth = currentMonth+addOrDown;
            if(currentMonth>12){
                instance.set(Calendar.YEAR,year+1);
                instance.set(Calendar.MONTH,0);
                currentMonth=1;
            }else if(currentMonth<1){
                instance.set(Calendar.YEAR,year-1);
                instance.set(Calendar.MONTH,11);
                currentMonth=12;
            }else{
                instance.set(Calendar.YEAR,year);
                instance.set(Calendar.MONTH,currentMonth-1);
            }

            //当前显示年份在真是年份之后
            if(instance.get(Calendar.YEAR)>currentYear){
                isBeforeCurrentMonth=false;
                isCurrentMonth=false;
                //当前显示年份就是真实年份
            }else if(instance.get(Calendar.YEAR)==currentYear){
                if(currentMonth>realCurrentMonth){
                    isBeforeCurrentMonth=false;
                    isCurrentMonth=false;
                }else if(currentMonth==realCurrentMonth){
                    isBeforeCurrentMonth=true;
                    isCurrentMonth=true;
                }else{
                    isBeforeCurrentMonth=true;
                    isCurrentMonth=false;
                }
                //当前显示年份在真实年份之前
            }else{
                isBeforeCurrentMonth=true;
                isCurrentMonth=false;
            }

            calendarAdapter.setIsCurrentMonth(isCurrentMonth);
            calendarAdapter.setIsBeforeCurrentMonth(isBeforeCurrentMonth);

            //获取当月第一天周几
            instance.set(Calendar.DAY_OF_MONTH, 1);
            dayOfWeekInMonth=instance.get(Calendar.DAY_OF_WEEK);
            calendarAdapter.setDayOfWeek(dayOfWeekInMonth);

            String[] split = selectedDate.split("-");
            if(Integer.valueOf(split[0])==instance.get(Calendar.YEAR) && Integer.valueOf(split[1])==currentMonth){
                calendarAdapter.setSelectedDay(Integer.valueOf(split[2]));
            }else{
                calendarAdapter.setSelectedDay(0);
            }

            currentShowYear=instance.get(Calendar.YEAR);
            return currentShowYear;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView tv_day = (TextView) view.findViewById(R.id.tv_day);
            if((tv_day.getCurrentTextColor()==context.getResources().getColor(R.color.font_333333)||tv_day.getCurrentTextColor()==context.getResources().getColor(R.color.white))||
                    tv_day.getCurrentTextColor()==context.getResources().getColor(R.color.blue_55c0ea)){
                if(position>=dayOfWeekInMonth-1){
                    String trim = tv_title.getText().toString().trim();
                    if(trim.length()==7){//单月
                        //月份和日必须是两位数
                        if(list.get(position-(dayOfWeekInMonth-1)).length()==1){
                            selectedDate=trim.substring(0,4)+"-0"+trim.substring(5,6)+"-0"+list.get(position-(dayOfWeekInMonth-1));
                        }else{
                            selectedDate=trim.substring(0,4)+"-0"+trim.substring(5,6)+"-"+list.get(position-(dayOfWeekInMonth-1));
                        }
                    }else if(trim.length()==8){//双月
                        if(list.get(position - (dayOfWeekInMonth-1)).length()==1){
                            selectedDate=trim.substring(0,4)+"-"+trim.substring(5,7)+"-0"+list.get(position - (dayOfWeekInMonth-1));
                        }else{
                            selectedDate=trim.substring(0,4)+"-"+trim.substring(5,7)+"-"+list.get(position - (dayOfWeekInMonth-1));
                        }
                    }

                    calendarAdapter.setSelectedDay(Integer.valueOf(list.get(position - (dayOfWeekInMonth - 1))));
                    calendarAdapter.notifyDataSetChanged();
                    if(onDialogClick!=null){
                        onDialogClick.onClick(selectedDate);
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    },300);
                }else{
                    return;
                }
            }
        }


        public interface OnDialogClick{
            void onClick(String date);
        }
    }
}
