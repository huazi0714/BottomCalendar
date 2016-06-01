package com.example.administrator.calendartest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
public class BottomDateCalendarAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int dayOfWeek;
    private int dayOfMonth;
    private boolean isBeforeCurrentMonth=false;
    private boolean isCurrentMonth=false;
    private final int color_333333;
    private final int color_55c0ea;
    private int color_cccccc;
    private final int color_999999;
    private final int color_ff6d00;
    private final int color_ffffff;
    private int selectedDay;

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setIsBeforeCurrentMonth(boolean isBeforeCurrentMonth) {
        this.isBeforeCurrentMonth = isBeforeCurrentMonth;
    }

    public void setIsCurrentMonth(boolean isCurrentMonth) {
        this.isCurrentMonth = isCurrentMonth;
    }

    public BottomDateCalendarAdapter(Context context, List<String> list, int dayOfWeek, int dayOfMonth, boolean isBeforeCurrentMonth, boolean isCurrentMonth){
        this.context=context;
        this.dayOfMonth=dayOfMonth;
        this.dayOfWeek=dayOfWeek;
        this.list=list;
        this.isBeforeCurrentMonth=isBeforeCurrentMonth;
        this.isCurrentMonth=isCurrentMonth;
        color_333333 = context.getResources().getColor(R.color.font_333333);
        color_55c0ea = context.getResources().getColor(R.color.blue_55c0ea);
        color_cccccc = context.getResources().getColor(R.color.position_bookup_bg);
        color_ffffff = context.getResources().getColor(R.color.white);
        color_999999 = context.getResources().getColor(R.color.font_999999);
        color_ff6d00 = context.getResources().getColor(R.color.color_coach_item_book);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size() + (dayOfWeek - 1);
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_grideview_calendar, null);
            holder = new ViewHolder();
            holder.tv_day = (TextView) convertView.findViewById(R.id.tv_day);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_price.setVisibility(View.GONE);

        if(isBeforeCurrentMonth){//在当月或者当月之前
            //设置日期颜色
            if (position % 7 == 0 || (position - 6) % 7 == 0) {
                holder.tv_day.setTextColor(color_55c0ea);
                holder.ll_item.setBackgroundResource(R.color.white);
            } else {
                holder.tv_day.setTextColor(color_333333);
                holder.ll_item.setBackgroundResource(R.color.white);
            }

            if(position>(dayOfWeek-1) && isCurrentMonth){
                if(Integer.valueOf(list.get(position-(dayOfWeek-1)))>dayOfMonth){
                    holder.tv_day.setTextColor(color_cccccc);
                }

            }

            if(position>=(dayOfWeek-1)){

                if( selectedDay!=0&&selectedDay == Integer.valueOf(list.get(position - (dayOfWeek - 1)))){
                    holder.tv_day.setTextColor(color_ffffff);
                    holder.ll_item.setBackgroundResource(R.drawable.round_55c0ea_no_border);
                }
            }

        }else{

            holder.tv_day.setTextColor(color_cccccc);
            holder.ll_item.setBackgroundResource(R.color.white);
        }

        switchMonth(list,holder,position);


        return convertView;
    }

    private void switchMonth(List<String> list, ViewHolder holder, int position) {
        switch (dayOfWeek) {
            case 1://周日
                if (list.get(position).equals(dayOfMonth + "") && isCurrentMonth) {
                    holder.tv_day.setText(context.getResources().getString(R.string.text_today));
                }else if(list.get(position).equals((dayOfMonth +1)+ "")&& isCurrentMonth){
                    holder.tv_day.setText(context.getResources().getString(R.string.text_tomorrow));
                }else {
                    holder.tv_day.setText(list.get(position));
                }
                break;
            case 2://周一
                if (position < 1) {
                    holder.tv_day.setText("");
                } else {
                    setText(list, position, holder, 1);
                }
                break;
            case 3://周二
                if (position < 2) {
                    holder.tv_day.setText("");
                } else {
                    setText(list, position, holder, 2);
                }
                break;
            case 4://周三
                if (position < 3) {
                    holder.tv_day.setText("");
                } else {
                    setText(list, position, holder, 3);
                }
                break;
            case 5://周四
                if (position < 4) {
                    holder.tv_day.setText("");
                } else {
                    setText(list, position, holder, 4);
                }
                break;
            case 6://周五
                if (position < 5) {
                    holder.tv_day.setText("");
                } else {
                    setText(list, position, holder, 5);
                }
                break;
            case 7://周六
                if (position < 6) {
                    holder.tv_day.setText("");
                } else {
                    setText(list, position, holder, 6);
                }
                break;
        }
    }

    private void setText(List<String> list, int position, ViewHolder holder, int index) {
        if (list.get(position - index).equals(dayOfMonth + "") && isCurrentMonth) {
            holder.tv_day.setText(context.getResources().getString(R.string.text_today));
        }else if(list.get(position - index).equals((dayOfMonth+1) + "") && isCurrentMonth){
            holder.tv_day.setText(context.getResources().getString(R.string.text_tomorrow));
        }else {
            holder.tv_day.setText(list.get(position - index));
        }


    }

    private class ViewHolder {
        private TextView tv_day;
        private TextView tv_price;
        private LinearLayout ll_item;
    }
}
