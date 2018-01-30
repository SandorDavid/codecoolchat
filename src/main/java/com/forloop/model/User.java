package com.forloop.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "[user]")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String password;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date regDate;

    @ManyToMany(mappedBy = "userList")
    private List<Channel> channels;

    private Integer reputation;

    public User() {}


    public User(String name, String password, String email, List<Channel> channels, Integer reputation) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.regDate = new Date();
        this.channels = channels;
        this.reputation = reputation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }
}
