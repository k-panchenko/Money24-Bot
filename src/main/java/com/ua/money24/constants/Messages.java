package com.ua.money24.constants;

public interface Messages {
    String START = """
            Вітаємо!
            Курс у цьому боті моніториться по обміннику [Money24](%s)
            """;

    String RATE_TEMPLATE = """
            %s
                        
            Купівля: %s
            Продаж: %s
            """;
    String RATE_CHANGED = "Курс змінився❗️";

    String SUBSCRIBE_CURRENCIES = "Виберіть валюту для підписки:";

    String CURRENT_RATE = "Поточний курс \uD83D\uDCB2";
    String SUBSCRIBE = "Підписатися \uD83D\uDD14";

    String FINISH = "Завершити";
}
