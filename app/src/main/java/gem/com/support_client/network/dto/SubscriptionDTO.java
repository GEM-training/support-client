package gem.com.support_client.network.dto;

/**
 * Created by quanda on 14/03/2016.
 */
public class SubscriptionDTO {
    private String companyId;
    private String subscriptionType;
    private long startDate;
    private long expirationDate;
    private double chargedAmount;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(SubscriptionDTO subscriptionDTO) {
        this.companyId = subscriptionDTO.getCompanyId();
        this.subscriptionType = subscriptionDTO.getSubscriptionType();
        this.startDate = subscriptionDTO.getStartDate();
        this.expirationDate = subscriptionDTO.getExpirationDate();
        this.chargedAmount = subscriptionDTO.getChargedAmount();
    }

    public SubscriptionDTO(String companyId, String subscriptionType, long startDate, long expirationDate, double chargedAmount) {
        this.companyId = companyId;
        this.subscriptionType = subscriptionType;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.chargedAmount = chargedAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(double chargedAmount) {
        this.chargedAmount = chargedAmount;
    }

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
                "chargedAmount=" + chargedAmount +
                ", companyId='" + companyId + '\'' +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", startDate=" + startDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
