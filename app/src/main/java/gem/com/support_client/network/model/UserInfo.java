package gem.com.support_client.network.model;

import java.io.Serializable;

/**
 * Created by phuongtd on 09/03/2016.
 */
public class UserInfo implements Serializable {
    private String userId;
    private String  username;
    private String  avatar;
    private String  email;
    private String  phone;
    private String  address;
    private String companyId;
    private String companyName;

    public UserInfo() {
    }


    public UserInfo(String userId, String username, String avatar, String email, String phone, String address, String companyId, String companyName) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
