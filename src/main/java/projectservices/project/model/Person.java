package projectservices.project.model;

import java.util.List;

public class Person {

    private Integer id;

    private String name;

    private String login;

    private String password;

    private List<Person> persons;

    private Integer total;

    public Person() {}

    public Person(Integer id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {return id;}

    public List<Person> getPersons() { return persons; }

    public void setPersons(List<Person> persons) { this.persons = persons; }

    public Integer getTotal() { return total; }

    public void setTotal(Integer total) { this.total = total; }

    public void setId(Integer id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
