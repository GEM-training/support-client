package gem.com.support_client.network.model;

import java.io.Serializable;

/**
 * Created by phuongtd on 09/03/2016.
 */
public class UserInfo implements Serializable {
    private String username;
    private String avatar;
    private String email;
    private String company;

    public UserInfo() {
    }

    public UserInfo(String username, String avatar, String email, String company) {
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
