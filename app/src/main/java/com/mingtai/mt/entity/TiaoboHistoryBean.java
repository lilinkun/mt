package com.mingtai.mt.entity;

import java.util.ArrayList;

public class TiaoboHistoryBean {

    private int MonthMoney;
    private ArrayList<HistoryBean> HormonicList;

    public int getMonthMoney() {
        return MonthMoney;
    }

    public void setMonthMoney(int monthMoney) {
        MonthMoney = monthMoney;
    }

    public ArrayList<HistoryBean> getHormonicList() {
        return HormonicList;
    }

    public void setHormonicList(ArrayList<HistoryBean> hormonicList) {
        HormonicList = hormonicList;
    }

    public class HistoryBean{
        private String HarmonicDate;
        private int Integral;

        public String getHarmonicDate() {
            return HarmonicDate;
        }

        public void setHarmonicDate(String harmonicDate) {
            HarmonicDate = harmonicDate;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int integral) {
            Integral = integral;
        }
    }
}
