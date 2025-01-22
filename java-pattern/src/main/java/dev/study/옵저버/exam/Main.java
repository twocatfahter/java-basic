package dev.study.옵저버.exam;
/**
 * 주식 가격 알림 시스템 설계
 *
 * 주체(Subject): StockMarket
 *
 * 필드: String stockName, double stockPrice
 *
 * 메서드: setStockData(String name, double price)
 *
 * 옵저버(Observer): Investor
 *
 * 관심 있는 주식 이름과 가격 변동 알림을 수신.
 */


import java.util.ArrayList;
import java.util.List;

/**
 * Alice notified: AAPL is now $145.5
 * Bob notified: AAPL is now $145.5
 * Alice notified: GOOG is now $2725.6
 * Bob notified: GOOG is now $2725.6
 */

interface StockObserver {
    void update(String stockName, double stockPrice);
}

class StockMarket {
    private final List<StockObserver> observers = new ArrayList<>();
    private String stockName;
    private double stockPrice;

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(StockObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.update(stockName, stockPrice);
        }

    }

    public void setStockData(String stockName, double stockPrice) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        notifyObservers();
    }

}

class Investor implements StockObserver {
    private final String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockName, double stockPrice) {
        System.out.println(name + " notified: " + stockName + " is now $" + stockPrice);
    }
}

public class Main {
    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarket();

        StockObserver 길동 = new Investor("길동");
        StockObserver 유신 = new Investor("유신");

        stockMarket.addObserver(길동);
        stockMarket.addObserver(유신);

        stockMarket.setStockData("APPL", 145.89);
        stockMarket.setStockData("GOOG", 115.89);
    }
}
