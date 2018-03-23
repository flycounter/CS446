package cs446_w2018_group3.supercardgame.view.lobby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.model.network.Session;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Session> mData;
    private OnItemClickListener mItemClickListener;

    RecyclerViewAdapter(Context context, List<Session> mData) {
        if (mData == null) { mData = new ArrayList<>(); }
        this.mContext = context;
        this.mData = mData;
    }

    public void updateList(List<Session> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final Session model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.itemTxtTitle.setText(model.getPlayerName());
            genericViewHolder.itemTxtMessage.setText(String.format("%s:%s", model.getHost(), model.getPort()));
        }
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Session getItem(int position) {
        return mData.get(position);
    }
    
    public interface OnItemClickListener {
        void onItemClick(View view, int position, Session device);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;

        ViewHolder(final View itemView) {
            super(itemView);

            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.lobby_item_title);
            this.itemTxtMessage = (TextView) itemView.findViewById(R.id.lobby_item_ip);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), mData.get(getAdapterPosition()));
                }
            });
        }
    }
}

