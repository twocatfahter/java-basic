package dev.study.옵저버.basic2;

import java.util.ArrayList;
import java.util.List;

/**
 * 날씨 정보 알림
 * - 날씨 데이터를 관리하는 WeatherStation (주체)이 구독자(옵저버)에게 날씨정보를 알려주는겁니다.
 *
 * WeatherObserver -> update(float temperature, float humidity)
 * , WeatherStation, addObserver, deleteObserver, notifyObservers, setWeatherData, MobileDevice
 */
interface WeatherObserver {
    void update(float temperature, float humidity);
}

class WeatherStation {
    private final List<WeatherObserver> observers = new ArrayList<>();
    private float temperature;
    private float humidity;

    public void addObserver(WeatherObserver weatherObserver) {
        observers.add(weatherObserver);
    }

    public void deleteObserver(WeatherObserver weatherObserver) {
        observers.remove(weatherObserver);
    }

    public void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(temperature, humidity);
        }
    }

    // 상태 변경 메서드
    public void setWeatherData(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        notifyObservers();
    }
}

class MobileDevice implements WeatherObserver {
    private final String name;

    public MobileDevice(String name) {
        this.name = name;
    }

    @Override
    public void update(float temperature, float humidity) {
        System.out.println(name + " received weather:  Temperature " + temperature + " Humidity " + humidity);
    }
}

public class Main {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        WeatherObserver device1 = new MobileDevice("device1");
        WeatherObserver device2 = new MobileDevice("device2");

        weatherStation.addObserver(device1);
        weatherStation.addObserver(device2);

        weatherStation.setWeatherData(25.6f, 78.9f);
        weatherStation.setWeatherData(22.6f, 58.9f);
    }
}
