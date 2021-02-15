package net.seifhadjhassen.recyclerviewpager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ViewHolder> {

    private final List<PagerModel> list;
    private final Context context;
    private OnItemClickListener mOnItemClickListener;
    private int posAttached = 0;

    /* private float cardWidthRelativeScreen = 0.88f, cardHeightRelativeScreen = 0.56f;
     private float cardElevation = 4, cardRadius = 12;*/
    private float cardWidthRelativeScreen, cardHeightRelativeScreen;
    private int cardElevationDP, cardRadiusDP;

    public PagerAdapter(Context context, List<PagerModel> list) {
        this.list = list;
        this.context = context;
    }

    public void setCardWidthRelativeScreen(float cardWidthRelativeScreen) {
        this.cardWidthRelativeScreen = cardWidthRelativeScreen;
    }

    public void setCardHeightRelativeScreen(float cardHeightRelativeScreen) {
        this.cardHeightRelativeScreen = cardHeightRelativeScreen;
    }

    public void setCardElevationDP(int cardElevationDP) {
        this.cardElevationDP = cardElevationDP;
    }

    public void setCardRadiusDP(int cardRadiusDP) {
        this.cardRadiusDP = cardRadiusDP;
    }

    @NonNull
    @Override
    public PagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager, parent, false);

        int deviceWidth = DisplayUtils.getDisplay().getWidth(context);
        view.getLayoutParams().width = (int) (deviceWidth * cardWidthRelativeScreen);
        view.getLayoutParams().height = (int) (deviceWidth * cardHeightRelativeScreen);

        return new PagerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PagerAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getImg()).into(holder.imageView);
        holder.textView.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        posAttached = holder.getAdapterPosition();
        super.onViewAttachedToWindow(holder);
    }

    public int getPosAttached() {
        return posAttached;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;
        private final CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_header_background);
            textView = itemView.findViewById(R.id.txt_item_header);
            cardView = itemView.findViewById(R.id.card_item_header);

            cardView.setRadius(DisplayUtils.getDisplay().dpToFloat(context, cardRadiusDP));
            cardView.setCardElevation(DisplayUtils.getDisplay().dpToFloat(context, cardElevationDP));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnclickItemListener(OnItemClickListener onclickItemListener) {
        this.mOnItemClickListener = onclickItemListener;
    }
}
