package gem.com.support_client.network.dto;

import gem.com.support_client.network.model.Enterprise;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class ListEnterpriseDTO{
    private Enterprise[] enterprises;

    public ListEnterpriseDTO(Enterprise[] enterprises) {
        this.enterprises = enterprises;
    }

    public Enterprise[] getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(Enterprise[] enterprises) {
        this.enterprises = enterprises;
    }
}
