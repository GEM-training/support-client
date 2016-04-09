package gem.com.support_client.network.dto;

import java.io.Serializable;

/**
 * Created by quanda on 04/03/2016.
 */
public class Company implements Serializable {
    private long id;
    private String logo;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String fax;

    public Company(long id, String logo, String name, String address, String phone, String email, String fax) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.fax = fax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
