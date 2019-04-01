package pkg.pojo;

import java.util.Date;

public class Person {
//    private ObjectId id;
    private String id;
    private String name;
    private int age;
    private Address address;
    private Date timestamp;

    public Person() {
    }

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(final ObjectId id) {
//        this.id = id;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", timestamp=" + timestamp +
                '}';
    }
}
