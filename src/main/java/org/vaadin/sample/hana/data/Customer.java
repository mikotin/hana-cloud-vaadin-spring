package org.vaadin.sample.hana.data;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "CUST_UUID", unique = true)
    private String id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "IMG_URL")
    private String imgUrl;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Safe way to get image url: if url is null, then use "no-image" -image
     * @return
     */
    @Transient
    public String getImgUrlSafe() {
        // Using random public domain image here. A real "no image" would have been better, but got lazy :D
        return (this.imgUrl == null || this.imgUrl.isEmpty() ? "https://c1.staticflickr.com/4/3770/11126293325_448a8bb0f9_c.jpg" : this.imgUrl);
    }

    @Override
    public String toString() {
        return String.format("Person[id=%d, firstName='%s', lastName='%s']", id,
                firstName, lastName);
    }
}