package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.domain.MenuItem;
import christmas.domain.Order;
import christmas.domain.OrderItem;
import christmas.domain.enums.EventBadge;
import christmas.domain.enums.Type;
import christmas.domain.event.Event;
import christmas.exception.InvalidInputException;
import christmas.service.Planner;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void 모든_타이틀_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                "<주문 메뉴>",
                "<할인 전 총주문 금액>",
                "<증정 메뉴>",
                "<혜택 내역>",
                "<총혜택 금액>",
                "<할인 후 예상 결제 금액>",
                "<12월 이벤트 배지>"
            );
        });
    }

    @Test
    void 혜택_내역_없음_출력() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });
    }

    @Test
    void 날짜_예외_테스트() {
        assertSimpleTest(() -> {
            runException("a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });

        assertSimpleTest(() -> {
            runException("40");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });

        assertSimpleTest(() -> {
            runException("-1");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 주문_예외_테스트() {
        assertSimpleTest(() -> {
            runException("3", "제로콜라-a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });

        assertSimpleTest(() -> {
            runException("3", "제로콜라");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });

        assertSimpleTest(() -> {
            runException("3", "a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });

        assertSimpleTest(() -> {
            runException("3", "바비큐립1");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });

        assertSimpleTest(() -> {
            runException("3", "떡볶이-1");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });

        assertSimpleTest(() -> {
            runException("3", "시저샐러드-1,시저샐러드-1");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });

    }


    @Test
    void 이벤트_적용_테스트() {
        // Given
        Order order = new Order();
        MenuItem menuItem1 = new MenuItem("Menu1", 2000, Type.DRINK);
        MenuItem menuItem2 = new MenuItem("Menu2", 7000, Type.MAIN);

        // When
        order.addOrderItem(new OrderItem(menuItem1, 1));
        order.addOrderItem(new OrderItem(menuItem2, 1));

        assertEquals(0, order.getTotalBenefitAmount());
    }
    @Test
    void 주문_개수_초과_테스트() {
        // Given
        Order order = new Order();
        MenuItem menuItem1 = new MenuItem("Menu1", 5000, Type.MAIN);

        // When
        order.addOrderItem(new OrderItem(menuItem1, 30));

        assertThrows(IllegalArgumentException.class, () -> {
            // This block should throw an IllegalArgumentException
            InvalidInputException.validateOrderQuantity(order.getOrderItems().get(0), 0);
        });
    }

    @Test
    void 음료만_주문_테스트() {
        // Given
        Order order = new Order();
        MenuItem menuItem1 = new MenuItem("Menu1", 5000, Type.DRINK);
        MenuItem menuItem2 = new MenuItem("Menu2", 7000, Type.DRINK);

        // When
        order.addOrderItem(new OrderItem(menuItem1, 3));
        order.addOrderItem(new OrderItem(menuItem2, 2));

        assertThrows(IllegalArgumentException.class, () -> {
            // This block should throw an IllegalArgumentException
            InvalidInputException.validateDrinkOnly(order.getOrderItems());
        });
    }
    @Test
    void 중복_주문_테스트() {
        // Given
        Order order = new Order();
        MenuItem menuItem1 = new MenuItem("Menu1", 5000, Type.DRINK);

        // When
        order.addOrderItem(new OrderItem(menuItem1, 3));
        order.addOrderItem(new OrderItem(menuItem1, 2));

        List<OrderItem> orderItemsList = order.getOrderedItems();
        Set<String> menuItemNamesSet = orderItemsList.stream()
                .map(orderItem -> orderItem.getMenuItem().getName())
                .collect(Collectors.toSet());
        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // This block should throw an IllegalArgumentException
            InvalidInputException.validateDuplicates(menuItemNamesSet, menuItem1.getName());
        });
    }

    @Test
    public void testAddOrderItem() {
        // Given
        Order order = new Order();
        MenuItem menuItem = new MenuItem("TestMenu", 10000, Type.MAIN);

        // When
        order.addOrderItem(new OrderItem(menuItem, 2));

        // Then
        List<OrderItem> orderItems = order.getOrderItems();
        assertEquals(1, orderItems.size());
        assertEquals("TestMenu", orderItems.get(0).getMenuItem().getName());
        assertEquals(2, orderItems.get(0).getQuantity());
    }

    @Test
    public void testTotalAmountCalculation() {
        // Given
        Order order = new Order();
        MenuItem menuItem1 = new MenuItem("Menu1", 5000, Type.MAIN);
        MenuItem menuItem2 = new MenuItem("Menu2", 7000, Type.DESSERT);

        // When
        order.addOrderItem(new OrderItem(menuItem1, 3));
        order.addOrderItem(new OrderItem(menuItem2, 2));

        // Then
        assertEquals(29000, order.getTotalAmount());
    }

    @Test
    void calculateTotalBenefitAmount_NoDiscount() {
        // Given
        Order order = new Order();
        // Assume that order has no discounts applied

        // When
        int totalBenefitAmount = order.getTotalBenefitAmount();

        // Then
        assertEquals(0, totalBenefitAmount);
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
