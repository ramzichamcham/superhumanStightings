package com.sg.superhumanSightings.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Organization {
    private int id;

    @NotBlank(message="Name must not be empty.")
    @Size(max = 100, message = "Name must be less than 100 characters.")
    private String name;

    private String description;

    @NotBlank(message="Address must not be empty.")
    @Size(max = 200, message = "Address must be less than 200 characters.")
    private String address;

    @NotBlank(message="Phone Number must not be empty.")
    @Size(max = 15, message = "Phone number must be less than 15 characters.")
    private String phoneNumber;

    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //description can be null
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id == that.id &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                address.equals(that.address) &&
                phoneNumber.equals(that.phoneNumber) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, phoneNumber, email);
    }
}
