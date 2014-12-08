package com.ls.bean;

public class WeatherEntity {
	public WeatherEntity() {

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getTemper() {
		return temper;
	}

	public void setTemper(String temper) {
		this.temper = temper;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getMoisture() {
		return moisture;
	}

	public void setMoisture(String moisture) {
		this.moisture = moisture;
	}

	public String getDressing() {
		return dressing;
	}

	public void setDressing(String dressing) {
		this.dressing = dressing;
	}

	public String getSports() {
		return sports;
	}

	public void setSports(String sports) {
		this.sports = sports;
	}

	public String getOneDay() {
		return oneDay;
	}

	public void setOneDay(String oneDay) {
		this.oneDay = oneDay;
	}

	public String getTwoDay() {
		return twoDay;
	}

	public void setTwoDay(String twoDay) {
		this.twoDay = twoDay;
	}

	public String getThreeDay() {
		return threeDay;
	}

	public void setThreeDay(String threeDay) {
		this.threeDay = threeDay;
	}

	public String getFourDay() {
		return fourDay;
	}

	public void setFourDay(String fourDay) {
		this.fourDay = fourDay;
	}

	public String getFiveDay() {
		return fiveDay;
	}

	public void setFiveDay(String fiveDay) {
		this.fiveDay = fiveDay;
	}

	public WeatherEntity(String city, String updateTime, String temper,
			String wind, String moisture, String dressing, String sports,
			String oneDay, String twoDay, String threeDay, String fourDay,
			String fiveDay) {
		super();
		this.city = city;
		this.updateTime = updateTime;
		this.temper = temper;
		this.wind = wind;
		this.moisture = moisture;
		this.dressing = dressing;
		this.sports = sports;
		this.oneDay = oneDay;
		this.twoDay = twoDay;
		this.threeDay = threeDay;
		this.fourDay = fourDay;
		this.fiveDay = fiveDay;
	}

	@Override
	public String toString() {
		return "WeatherEntity [city=" + city + ", updateTime=" + updateTime
				+ ", temper=" + temper + ", wind=" + wind + ", moisture="
				+ moisture + ", dressing=" + dressing + ", sports=" + sports
				+ ", oneDay=" + oneDay + ", twoDay=" + twoDay + ", threeDay="
				+ threeDay + ", fourDay=" + fourDay + ", fiveDay=" + fiveDay
				+ "]";
	}

	private String city;// ����
	private String updateTime;// ����ʱ��
	private String temper;// �¶�
	private String wind;// ����
	private String moisture;// ʪ��
	private String dressing;// ����ָ��
	private String sports;// �˶�ָ��
	private String oneDay;// ��������
	private String twoDay;// �ڶ���
	private String threeDay;// ������
	private String fourDay;// ������
	private String fiveDay;// ������

}
