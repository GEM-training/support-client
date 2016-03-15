package gem.com.support_client.network.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quanda on 04/03/2016.
 */
public class Bill {
    @SerializedName("id")
    private String id;
    private String companyId;
    private int numOfUser;
    private double feePerUser;
    private long issuedDate;
    private long paymentDate;
    private int userIncrement;

    public Bill(String id, String companyId, int numOfuser, double feePerUser, long issuedDate, int userIncrement) {
        this.id = id;
        this.companyId = companyId;
        this.numOfUser = numOfuser;
        this.feePerUser = feePerUser;
        this.issuedDate = issuedDate;
        this.userIncrement = userIncrement;
    }

    public int getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(int numOfUser) {
        this.numOfUser = numOfUser;
    }

    public double getFeePerUser() {
        return feePerUser;
    }

    public void setFeePerUser(double feePerUser) {
        this.feePerUser = feePerUser;
    }

    public long getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(long issuedDate) {
        this.issuedDate = issuedDate;
    }

    public int getUserIncrement() {
        return userIncrement;
    }

    public void setUserIncrement(int userIncrement) {
        this.userIncrement = userIncrement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(long paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", companyId='" + companyId + '\'' +
                ", numOfuser=" + numOfUser +
                ", feePerUser=" + feePerUser +
                ", issuedDate=" + issuedDate +
                ", paymentDate=" + paymentDate +
                ", userIncrement=" + userIncrement +
                '}';
    }
}
