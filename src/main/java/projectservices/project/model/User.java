package projectservices.project.model;

import javax.persistence.*;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 10)
    private String login;

    @Column(nullable = false, length = 10)
    private String password;

    public User() {}

    public User(Integer id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
