package gem.com.support_client.screen.billing.companybills;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import gem.com.support_client.adapter.BillAdapter;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DialogUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.PageableResponse;
import gem.com.support_client.network.dto.SubscriptionDTO;
import gem.com.support_client.screen.main.MainActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by quanda on 07/03/2016.
 */
public class CompanyBillsPresenterImpl implements CompanyBillsPresenter {
    private final CompanyBillsView mView;
    private ArrayList<Bill> mBills;
    private BillAdapter mAdapter;
    private int mCurrentPage;
    private SubscriptionDTO mSubscription;

    public CompanyBillsPresenterImpl(CompanyBillsView mView) {
        this.mView = mView;
        mBills = new ArrayList<>();
        mAdapter = new BillAdapter(mBills, mView.getContextBase());
        mCurrentPage = 0;
    }

    @Override
    public void getAllBillsByCompanyId(String companyId) {
        EventLogger.info("Get all bills of a company");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBillsByCompanyId(companyId, 0, Constants.PAGE_SIZE, Constants.columnPaidDateDESC);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                mBills.addAll(response.body().getContent());
                mAdapter.setBills(mBills);
                mView.onGetAllBillsSuccess();
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void loadMore(String companyId) {
        mCurrentPage++;
        EventLogger.info("Load more bills of a company");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBillsByCompanyId(companyId, mCurrentPage, Constants.PAGE_SIZE, Constants.columnPaidDateDESC);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                mBills.addAll(response.body().getContent());
                mAdapter.notifyDataSetChanged();

                mView.onLoadMoreSuccess();
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                EventLogger.info("Load more failure: " + t.getMessage());
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void getCompanySubscription(String companyId) {
        EventLogger.info("Get company subscription");
        Call<SubscriptionDTO> call = ServiceBuilder.getService().getCompanySubscription(companyId);
        call.enqueue(new Callback<SubscriptionDTO>() {
            @Override
            public void onResponse(Call<SubscriptionDTO> call, Response<SubscriptionDTO> response) {
                mSubscription = response.body();
                mView.onGetSubscription();
            }

            @Override
            public void onFailure(Call<SubscriptionDTO> call, Throwable t) {
                EventLogger.info("Get subscription failure: " + t.getMessage());
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void getCompanyBillsFile(String fileFormat) {
        ServiceBuilder.getService().getCompanyBillsFile(fileFormat).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccess()) {
                    InputStream is = response.body().byteStream();

                    String dir = Environment.getExternalStorageDirectory() + File.separator + "billing";
                    //create folder
                    File folder = new File(dir); //folder name
                    folder.mkdirs();

                    //create file
                    File file = new File(dir, "billing.xls");

                    try {
                        OutputStream output = new FileOutputStream(file);

                        byte[] buffer = new byte[4 * 1024]; // or other buffer size
                        int read;

                        while ((read = is.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                        output.close();

                        mView.onDownloadFileSucces();

                    } catch (Exception e) {
                        e.printStackTrace(); // handle exception, define IOException and others
                        Toast.makeText(mView.getContextBase(), "Write file error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    DialogUtils.showErrorAlert(mView.getContextBase(), response.code() + " " + response.message());
                }

                Toast.makeText(MainActivity.thiz , "Download Success" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                DialogUtils.showErrorAlert(mView.getContextBase(), Constants.CONNECT_TO_SERVER_ERROR);
            }
        });
    }

    public ArrayList<Bill> getBills() {
        return mBills;
    }

    public BillAdapter getAdapter() {
        return mAdapter;
    }

    public SubscriptionDTO getSubscription() {
        return mSubscription;
    }
}