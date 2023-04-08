package ru.yandex.praktikum.model;

import net.bytebuddy.utility.RandomString;

public class UserGenerator {
    public static User getRandom() {
        String name = RandomString.make(10);
        String email = RandomString.make(10) + "@yandex.ru";
        String password = RandomString.make(10);
        return new User(name, email, password);
    }
}
