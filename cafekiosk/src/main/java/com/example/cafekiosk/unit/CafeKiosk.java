package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Beverage;
import com.example.cafekiosk.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverageList = new ArrayList<>();
    public void add(Beverage beverage) {
        beverageList.add(beverage);
    }

    public void add(Beverage beverage, int count) {

        // 요구사항 1잔이상 주문이 가능하며 1잔 미만은 에러발생한다.
        if (count <= 0){
            throw new IllegalArgumentException("음료는 1잔이상 주문 가능합니다.");
        }

        for(int i = 0; i<count; i++){
            beverageList.add(beverage);
        }
    }

    public void delete(Beverage beverage){
        beverageList.remove(beverage);
    }

    public void remove(Beverage beverage){
        beverageList.remove(beverage);
    }

    public void clear(){
        beverageList.clear();
    }


    public int calculateTotalPrice() {
        // 1. 일단 주먹구구식으로 개발한다.
//        int totalPrice = 0;
//        for(Beverage beverage : beverageList){
//            totalPrice += beverage.getPrice();
//        }
//        return totalPrice;

        // 2. 리팩토링 한다
        return beverageList.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    public Order createOrder(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문시간이 아닙니다.");
        }
        return new Order(currentDateTime, beverageList);
    }

    // 외부에서 시간을 주입받는다
    public Order createOrder(LocalDateTime currentDateTime){
        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문시간이 아닙니다.");
        }
        return new Order(currentDateTime, beverageList);
    }
}
