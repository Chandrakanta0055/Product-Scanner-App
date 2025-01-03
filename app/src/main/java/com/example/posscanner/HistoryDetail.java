package com.example.posscanner;

public class HistoryDetail {
    String date;
    String totalShopping;

    public HistoryDetail(String date, String totalShopping) {
        this.date = date;
        this.totalShopping = totalShopping;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalShopping() {
        return totalShopping;
    }

    public void setTotalShopping(String totalShopping) {
        this.totalShopping = totalShopping;
    }
}
