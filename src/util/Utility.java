package util;

import android.text.TextUtils;
import model.City;
import model.CoolWeatherDB;
import model.County;
import model.Province;

public class Utility {
	
	public synchronized static boolean handleProvinceResponse(CoolWeatherDB coolWeather,String response){
		
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
		    if(allProvinces != null&&allProvinces.length>0){
		    	for(String singleProvince : allProvinces){
					String[] provinceInfo = singleProvince.split("\\|");
					Province province = new Province();
					province.setProvinceCode(provinceInfo[0]);
					province.setProvinceName(provinceInfo[1]);
					coolWeather.saveProvince(province);
				}
		    	return true;
		    }
		}
		return false;
	}
	
	public synchronized static boolean handleCityResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
		
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities != null&&allCities.length>0){
				for(String singleCity : allCities){
					String[] cityInfo = singleCity.split("\\|");
					City city = new City();
					city.setCityCode(cityInfo[0]);
					city.setCityName(cityInfo[1]);
					city.setProvinceId(provinceId);
					coolWeatherDB.saveCity(city);
				}
			return true;
			}
		}
		return false;
	}
	
	public synchronized static boolean handleCountyResponse(CoolWeatherDB coolWeather,String response,int cityId){
		
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			if(allCounties!=null&&allCounties.length>0){
				for(String singleCounty : allCounties){
					String[] countyInfo = singleCounty.split("\\|");
					County county = new County();
					county.setCountyCode(countyInfo[0]);
					county.setCountyName(countyInfo[1]);
					county.setCityId(cityId);
					coolWeather.saveCounty(county);
				}
			return true;
			}
		}
		return false;
	}

}
