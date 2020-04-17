package com.myapp.model;

import com.google.gson.annotations.SerializedName;

public class StockView {

    @SerializedName("證券代號")
    private String stockNo;

    @SerializedName("證券名稱")
    private String stockName;

    @SerializedName("成交股數")
    private String tradingVolume;

    @SerializedName("成交金額")
    private String transaction;

    @SerializedName("開盤價")
    private String openingPrice;

    @SerializedName("最高價")
    private String highestPrice;

    @SerializedName("最低價")
    private String lowestPrice;

    @SerializedName("收盤價")
    private String closingPrice;

    @SerializedName("漲跌價差")
    private String change;

    @SerializedName("成交筆數")
    private String numberOfTransactions;


    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(String tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(String closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(String numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }
}
