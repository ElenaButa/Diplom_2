package data;

public class GenerateUser {
    public static String generateEmail() {
        return String.format("Bill%d", ((int) (Math.random() * (99999 - 11111) + 11111))) + "@yandex.ru";
    }
}
