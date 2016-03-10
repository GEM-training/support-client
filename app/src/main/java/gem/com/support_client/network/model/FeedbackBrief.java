package gem.com.support_client.network.model;

import java.io.Serializable;

/**
 * Created by phuongtd on 10/03/2016.
 */
public class FeedbackBrief implements Serializable{
    private String id;
    private String userId;
    private String username;
    private String company;
    private String subContent;
    private String time;

    public FeedbackBrief(String id, String userId, String username, String company, String subContent, String time) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.company = company;
        this.subContent = subContent;
        this.time = time;
    }

    public FeedbackBrief() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
