package com.myapp.model;


import com.google.gson.annotations.SerializedName;

public class StockBasicInfo {

	@SerializedName("出表日期")
	private String reportDate;

	@SerializedName("資料年月")
	private String dateYM;

	@SerializedName("公司代號")
	private String stockNo;

	@SerializedName("公司名稱")
	private String stockName;

	@SerializedName("產業別")
	private String industry;

	@SerializedName("營業收入-當月營收")
	private String revenue;

	@SerializedName("營業收入-上月營收")
	private String revenueMoM;

	@SerializedName("營業收入-去年當月營收")
	private String revenueYoY;

	@SerializedName("營業收入-上月比較增減(%)")
	private String mom;

	@SerializedName("營業收入-去年同月增減(%)")
	private String yoy;

	@SerializedName("累計營業收入-當月累計營收")
	private String cumulativeMoM;

	@SerializedName("累計營業收入-去年累計營收")
	private String cumulativeYoY;

	@SerializedName("累計營業收入-前期比較增減(%)")
	private String cumulative;

	@SerializedName("備註")
	private String remark;


	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getDateYM() {
		return dateYM;
	}

	public void setDateYM(String dateYM) {
		this.dateYM = dateYM;
	}

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

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getRevenueMoM() {
		return revenueMoM;
	}

	public void setRevenueMoM(String revenueMoM) {
		this.revenueMoM = revenueMoM;
	}

	public String getRevenueYoY() {
		return revenueYoY;
	}

	public void setRevenueYoY(String revenueYoY) {
		this.revenueYoY = revenueYoY;
	}

	public String getMom() {
		return mom;
	}

	public void setMom(String mom) {
		this.mom = mom;
	}

	public String getYoy() {
		return yoy;
	}

	public void setYoy(String yoy) {
		this.yoy = yoy;
	}

	public String getCumulativeMoM() {
		return cumulativeMoM;
	}

	public void setCumulativeMoM(String cumulativeMoM) {
		this.cumulativeMoM = cumulativeMoM;
	}

	public String getCumulativeYoY() {
		return cumulativeYoY;
	}

	public void setCumulativeYoY(String cumulativeYoY) {
		this.cumulativeYoY = cumulativeYoY;
	}

	public String getCumulative() {
		return cumulative;
	}

	public void setCumulative(String cumulative) {
		this.cumulative = cumulative;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
