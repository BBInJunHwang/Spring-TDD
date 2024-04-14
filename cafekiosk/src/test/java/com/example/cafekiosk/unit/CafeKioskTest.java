package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Americano;
import com.example.cafekiosk.unit.beverage.Latte;
import com.example.cafekiosk.unit.order.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CafeKioskTest {

    // 수동 테스트다
    // 뭘 검증할려는지 알 수 가없다.
    // 무조건 참인 테스트다
    @DisplayName("수동 테스트")
    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">> 담긴 음료 수 : " + cafeKiosk.getBeverageList().size());
        System.out.println(">> 담긴 음료 수 : " + cafeKiosk.getBeverageList().get(0).getName());
    }

    @DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
    @Test
    void add(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("경계값 테스트 + 해피케이스(무조건 참) 로직")
    @Test
    void addServeralBeverage(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverageList().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverageList().get(1)).isEqualTo(americano);
    }

    @DisplayName("경계값 테스트 + 예외케이스 로직")
    @Test
    void addZeroBeverage(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        Assertions.assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔이상 주문 가능합니다.");

    }

    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(1);

        cafeKiosk.remove(americano);
        Assertions.assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @Test
    void clear(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        Assertions.assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(2);

        cafeKiosk.clear();
        Assertions.assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    @Test
    void calculateTotalPrice(){
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        // when 보통 한줄이다. 수행하는 경우이기 때문
        int totalPrice = cafeKiosk.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(8500);
    }



    @DisplayName("현재 시간을 함수안에서 계산해서 실행하기 떄문에 실행 시간마다 결과가 성공,실패가 왔다갔다 한다.")
    @Test
    void createOrder(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();
        Assertions.assertThat(order.getBeverageList()).size().isEqualTo(1);
        assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");

    }

    @DisplayName("주문시간을 외부에서 주입받는다. 업무시간")
    @Test
    void createOrderWithCurrentTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024,3,26,10,0));
        Assertions.assertThat(order.getBeverageList()).size().isEqualTo(1);
        assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");

    }

    @DisplayName("주문시간을 외부에서 주입받는다. 오픈시간 이전")
    @Test
    void createOrderOutsideOpenTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Assertions.assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2024,3,26,9,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문시간이 아닙니다.");

    }
}