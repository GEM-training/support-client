package gem.com.support_client.network.model;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class Enterprise  {

    private String companyName;

    private int numOfTicket;

    public Enterprise() {
    }

    public Enterprise(String companyName, int numOfTicket) {
        this.companyName = companyName;
        this.numOfTicket = numOfTicket;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getNumOfTicket() {
        return numOfTicket;
    }

    public void setNumOfTicket(int numOfTicket) {
        this.numOfTicket = numOfTicket;
    }
}
