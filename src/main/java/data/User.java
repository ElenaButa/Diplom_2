package data;

public class User {
    private String email;
    private String password;
    private String name;

    public User() {
        this.email = String.format("Bill%d", ((int) (Math.random() * (99999 - 11111) + 11111))) + "@yandex.ru";
        this.password = "12345678";
        this.name = "Bill";
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
