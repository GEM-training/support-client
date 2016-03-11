package gem.com.support_client.screen.feedback.listfeedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.malinskiy.superrecyclerview.swipe.SwipeLayout;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import gem.com.support_client.R;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.network.model.FeedbackDetail;
import gem.com.support_client.screen.feedback.feedbackdetail.FeedbackDetailActivity;

public class FeedbackAdapter extends BaseSwipeAdapter<FeedbackAdapter.ViewHolder> {

    private List<FeedbackBrief> mData;

    public FeedbackAdapter(List<FeedbackBrief> mData) {
        this.mData = new ArrayList<>(mData);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.item_feedback, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        final SwipeLayout swipeLayout = viewHolder.swipeLayout;
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(viewHolder.getPosition());
                Toast.makeText(v.getContext(), "Deleted " + viewHolder.getPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close(true);
            }
        });
        return viewHolder;
    }

    public void setData(List<FeedbackBrief> data){
        mData = new ArrayList<>(data);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.tvName.setText(mData.get(position).getUsername());
        if(mData.get(position).getSubContent().length() < 50){
            holder.tvSubContent.setText(mData.get(position).getSubContent());
        } else {
            holder.tvSubContent.setText(mData.get(position).getSubContent().substring(0, 50));
        }

        holder.tvEnterprise.setText(mData.get(position).getCompanyName());

        java.sql.Date date = new java.sql.Date(Long.decode(mData.get(position).getTime()));
        java.util.Date utilDate = new java.util.Date();
        Date now = new Date(utilDate.getTime());

        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm aa");
        String userDateDay = formatDay.format(date);
        String userDateTime = formatTime.format(date);

        String nowDay = formatDay.format(now);

        if(nowDay.equals(userDateDay)){
            holder.tvTime.setText(userDateTime);
        } else {
            holder.tvTime.setText(userDateDay);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(FeedbackBrief feedbackDetail ) {
        insert(feedbackDetail , mData.size());
    }

    public void insert(FeedbackBrief feedbackDetail, int position) {
        closeAllExcept(null);

        mData.add(position, feedbackDetail);

        notifyItemInserted(position);
    }

    public void remove(int position) {
        mData.remove(position);

        closeItem(position);

        notifyItemRemoved(position);
    }

    public static class ViewHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder {
        CircleImageView imgUser;
        TextView tvName;
        TextView tvTime;
        TextView tvEnterprise;
        TextView tvSubContent;
        Button btnDelete;
        Button btnUndo;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imgUser = (CircleImageView) itemView.findViewById(R.id.avt_user);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvEnterprise = (TextView) itemView.findViewById(R.id.tv_enterprise);
            tvSubContent = (TextView) itemView.findViewById(R.id.tv_subcontent);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
            btnUndo = (Button) itemView.findViewById(R.id.btn_undo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null){
                        mListener.onRecyclerViewClick(view, getPosition());
                    }
                }
            });
        }
    }

    private static RecyclerViewClickListener mListener;

    public  interface RecyclerViewClickListener{
         void onRecyclerViewClick(View v, int position);
    }

    public void setOnRecyclerViewClickListener( RecyclerViewClickListener recyclerViewClickListener){
        mListener = recyclerViewClickListener;
    }

    public void animateTo(List<FeedbackBrief> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<FeedbackBrief> newModels) {
        for (int i = mData.size() - 1; i >= 0; i--) {
            final FeedbackBrief model = mData.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<FeedbackBrief> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final FeedbackBrief model = newModels.get(i);
            if (!mData.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<FeedbackBrief> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final FeedbackBrief model = newModels.get(toPosition);
            final int fromPosition = mData.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public FeedbackBrief removeItem(int position) {
        final FeedbackBrief model = mData.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, FeedbackBrief model) {
        mData.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final FeedbackBrief model = mData.remove(fromPosition);
        mData.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    
}
