package com.open01.logs.model;

public class Geoip {
	public String ip, country_code, country_code1, country_name, continent_code2, region_name, city_name, latitude,
			longitude, timezone, real_region_name, location;

	@Override
	public String toString() {
		return "Geoip [ip=" + ip + ", country_code=" + country_code + ", country_code1=" + country_code1
				+ ", country_name=" + country_name + ", continent_code2=" + continent_code2 + ", region_name="
				+ region_name + ", city_name=" + city_name + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", timezone=" + timezone + ", real_region_name=" + real_region_name + ", location=" + location + "]";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_code1() {
		return country_code1;
	}

	public void setCountry_code1(String country_code1) {
		this.country_code1 = country_code1;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getContinent_code2() {
		return continent_code2;
	}

	public void setContinent_code2(String continent_code2) {
		this.continent_code2 = continent_code2;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getReal_region_name() {
		return real_region_name;
	}

	public void setReal_region_name(String real_region_name) {
		this.real_region_name = real_region_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
