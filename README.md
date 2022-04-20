# Mukpo2

포장(예약, 주간 예약), 현장 자리 예약 제공 서비스

## 적용 기술

Java11, SpringBoot 2.6.6, JPA, Spring Security, OAuth2, JWT, AWS

## 도메인

- President(사장)
- Coustomer(회원)
- Store(가게)
- StoreStatus(가게상태)
- Reservation(예약)
  * WeeklyReservation(주간 예약)
  * PackingReservation(포장 예약)
  * OnSiteReservation(현장 예약)
- Payment(결제)
    * AutomaticPayment(자동결제)
    * PrePayment(선결제)
    * OnSitePayment(현장 결제)
- Menu(메뉴)
- visit(방문)
- MenuOption(메뉴 옵션)
- DiscountPolicy(할인)
- DiscountCondition(할인 조건)

![도메인](https://user-images.githubusercontent.com/32383284/164258638-b8cb392d-ce83-4cb4-9b79-3bd7b0da2253.PNG)

## ERD


