package Models;

import Authentication.UserAuth;
import Database.UsersDB;

import java.time.LocalDate;
import java.time.Period;

public class User {
    private String role, username, password, firstName, lastName;
    private LocalDate birthday;
    private int age;

    public User(String role, String username, String password, String firstName, String lastName, String birthday) {
        if ((UserAuth.isAdmin(username) && !UserAuth.isDuplicate(username)) || UserAuth.canSignUp(username)) {
            setRole(role);
            setUsername(username);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
            setBirthday(birthday);
            setAge();
            UsersDB.addToDB(this);
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) { // input format: "YYYY-MM-DD"
        int birthYear = Integer.parseInt(birthday.substring(0, 4));
        int birthMonth = Integer.parseInt(birthday.substring(5, 7));
        int birthDay = Integer.parseInt(birthday.substring(8));
        this.birthday = LocalDate.of(birthYear, birthMonth, birthDay);
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        Period difference = Period.between(birthday, LocalDate.now());
        this.age = difference.getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }
}
