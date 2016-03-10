package gem.com.support_client.screen.feedback.listenterprise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gem.com.support_client.R;
import gem.com.support_client.network.model.Enterprise;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class ListEnterpriseAdapter extends BaseAdapter {

    public static List<Enterprise> enterprises = new ArrayList<>();

    Context context;

    List<Enterprise> enterpriseList;

    public ListEnterpriseAdapter(Context context , List<Enterprise> list){
        this.context = context;
        this.enterpriseList = list;
        this.enterprises.addAll(list);
    }

    @Override
    public int getCount() {
        return enterpriseList.size();
    }

    @Override
    public Object getItem(int position) {
        return enterpriseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_enterprise , null);

        Enterprise enterprise = enterpriseList.get(position);

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_enterprise_name);
        TextView tvNumOfTicket = (TextView) convertView.findViewById(R.id.tv_num_of_ticket);

        tvName.setText(enterprise.getCompanyName());
        tvNumOfTicket.setText(enterprise.getNumOfTicket()+"");

        return convertView;
    }

    void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        enterpriseList.clear();
        if (charText.length() == 0) {
            enterpriseList.addAll(enterprises);
        }
        else
        {
            for(int i= 0 ; i < enterprises.size() ; i++){
                Enterprise e = enterprises.get(i);
                if (e.getCompanyName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    enterpriseList.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }
}
