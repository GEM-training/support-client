package gem.com.support_client.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quanda on 11/03/2016.
 */
public class Income {
    @SerializedName("companyId")
    private String companyId;
    @SerializedName("from")
    private long fromDate;
    @SerializedName("to")
    private long toDate;
    @SerializedName("numOfUser")
    private int totalUser;
    private int userIncrement;
    @SerializedName("totalRevenue")
    private double totalIncome;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int user) {
        this.totalUser = totalUser;
    }

    public int getUserIncrement() {
        return userIncrement;
    }

    public void setUserIncrement(int userIncrement) {
        this.userIncrement = userIncrement;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "Income{" +
                "companyId='" + companyId + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", totalUser=" + totalUser +
                ", userIncrement=" + userIncrement +
                ", totalIncome=" + totalIncome +
                '}';
    }
}
