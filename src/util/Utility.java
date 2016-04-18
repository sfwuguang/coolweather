package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
	
	public static void handleWeatherResponse(Context context,String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);		
		}catch(JSONException ex){ex.printStackTrace();}
	}
	
	public static void saveWeatherInfo(Context context, String cityName,String weatherCode,String temp1,String temp2,String weatherDesp,String publishTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyƒÍM‘¬d»’",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code",weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}

}
