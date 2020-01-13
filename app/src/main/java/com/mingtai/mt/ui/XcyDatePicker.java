package com.mingtai.mt.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.mingtai.mt.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by LG on 2020/1/13.
 */
public class XcyDatePicker extends LinearLayout {
    private static final int MIN_YEAR = 2020;
    private int MAX_YEAR = 2025;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private NumberPicker yearNp;
    private NumberPicker monthNp;
    private NumberPicker dayNp;

    public XcyDatePicker(Context context) {
        super(context);
        init(context);
    }

    public XcyDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XcyDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.pop_date, this);
        yearNp = (NumberPicker) findViewById(R.id.year_np);
        monthNp = (NumberPicker) findViewById(R.id.month_np);
        dayNp = (NumberPicker) findViewById(R.id.day_np);
        //初始化显示的日期 默认今天
        setNpMinValAndMaxVal(yearNp, MIN_YEAR, MAX_YEAR);
        setNpMinValAndMaxVal(monthNp, MIN_MONTH, MAX_MONTH);
        setDisplayValue(new Date());

        monthNp.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dayNp.setMaxValue(getDaysInMonthAndYear(yearNp.getValue(), newVal));
            }
        });

        yearNp.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dayNp.setMaxValue(getDaysInMonthAndYear(newVal, monthNp.getValue()));
            }
        });
    }

    /**
     * 获取某一年某一月的天数
     *
     * @param year
     * @param month 取值范围1-12
     * @return
     */
    private int getDaysInMonthAndYear(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DATE);

    }

    /**
     * 根据date来展示当前显示的日期
     *
     * @param date
     */
    public void setDisplayValue(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        yearNp.setValue(calendar.get(Calendar.YEAR));
        monthNp.setValue(calendar.get(Calendar.MONTH) + 1);
        setNpMinValAndMaxVal(dayNp, 1, getDaysInMonthAndYear(yearNp.getValue(), monthNp.getValue()));
        dayNp.setValue(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 根据具体的年月日展示日期
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDisplayValue(int year, int month, int day) {
        checkNumIsInRange(year, MIN_YEAR, MAX_YEAR, "请输入" + MIN_YEAR + "~~~~" + MAX_YEAR + "之间的年份");
        yearNp.setValue(year);
        checkNumIsInRange(month, MIN_MONTH, MAX_MONTH, "请输入" + MIN_MONTH + "~~~~" + MAX_MONTH + "之间的月份");
        monthNp.setValue(month);
        int maxDays = getDaysInMonthAndYear(yearNp.getValue(), monthNp.getValue());
        setNpMinValAndMaxVal(dayNp, 1, maxDays);
        checkNumIsInRange(day, 1, maxDays
                , "请输入" + 1 + "到" + maxDays + "之间的天数");
        dayNp.setValue(day);
    }

    /**
     * 检查输入的数字是否在范围内
     *
     * @param checkNum
     * @param minNum
     * @param maxNum
     * @param message
     */
    private void checkNumIsInRange(int checkNum, int minNum, int maxNum, String message) {
        if (checkNum < minNum || checkNum > maxNum) {
            throw new NumberFormatException(message);
        }
    }

    /**
     * 获取当前展示的日期 类型为Date
     *
     * @return 返回值为Date类型
     */
    public Date getDisplayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearNp.getValue(), monthNp.getValue() - 1, dayNp.getValue());
        return calendar.getTime();
    }

    /**
     * 获取指定格式的日期字符串
     *
     * @param pattern
     * @return
     */
    public String getDisPlayDateStr(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearNp.getValue(), monthNp.getValue() - 1, dayNp.getValue());
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前展示的年份
     *
     * @return 返回年份
     */
    public int getDisplayYear() {
        return yearNp.getValue();
    }

    /**
     * 获取当前展示的月份
     *
     * @return 返回月份
     */
    public int getDisplayMonth() {
        return monthNp.getValue();
    }

    /**
     * 返回当前展示的某一月的第几天
     *
     * @return 返回月份的第几天
     */
    public int getDisplayDay() {
        return dayNp.getValue();
    }

    /**
     * 设置NumberPicker的取值范围
     *
     * @param np
     * @param minVal
     * @param maxVal
     */
    private void setNpMinValAndMaxVal(NumberPicker np, int minVal, int maxVal) {
        np.setMinValue(minVal);
        np.setMaxValue(maxVal);
    }
}
