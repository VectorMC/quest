package net.avicus.quest.example;

import lombok.Getter;
import lombok.ToString;
import net.avicus.quest.model.Model;
import net.avicus.quest.annotation.Column;
import net.avicus.quest.annotation.Id;

@ToString
public class User extends Model {
    @Getter
    @Id
    @Column
    private int id;

    @Getter
    @Column(name = "username", unique = true)
    private String name;

    public User() {

    }

    public User(String name) {
        this.name = name;
    }
}
