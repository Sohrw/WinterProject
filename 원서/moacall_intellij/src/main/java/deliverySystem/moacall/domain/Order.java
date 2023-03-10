package deliverySystem.moacall.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalPrice;

    private int deliveryPrice;

    private String memo;

    @Embedded
    @Column(name = "client_address")
    private Address clientAddress;

    private double distance;

    private String foodAddress;

    private LocalDateTime orderDateTime;

    private LocalDateTime acceptDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public static Order createOrder(Member member,String memo, String clientCity, String clientStreet, String clientZipcode, String clientDetailAddress, int clientPrice, int deliveryPrice, PaymentType paymentType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setMember(member);
        order.setDistance(1.11);
        order.setFoodAddress(member.getFoodAddress().toString());
        order.setOrderStatus(OrderStatus.COOKING);
        order.setOrderDateTime(LocalDateTime.now());
        Address address = new Address(clientCity, clientStreet, clientZipcode, clientDetailAddress);
        order.setClientAddress(address);
        order.setTotalPrice(clientPrice);
        order.setDeliveryPrice(deliveryPrice);
        order.setPaymentType(paymentType);
        order.setMemo(memo);
        return order;
    }


}
