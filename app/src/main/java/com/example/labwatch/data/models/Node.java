package com.example.labwatch.data.models;

public class Node {
    private final String id; // Приходит node-01 и тд

    public Node(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    // Изменение отображаемого имени узла
    public String getDisplayName() {
        String number = id.replace("node-", "");
        return "Сервер " + number;
    }
}
