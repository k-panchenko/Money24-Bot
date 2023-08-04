package com.ua.money24.constants;

public interface Messages {
    String START = """
            Вітаємо!
            Курс у цьому боті моніториться по обміннику [Money24](%s)
            """;

    String RATE_CHANGED_TEMPLATE = """
            %s Курс змінився❗️
                        
            Купівля: %s
            Продаж: %s
            """;
}
