package net.seifhadjhassen.recyclerviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerViewPager extends RecyclerView {

    private int count;
    private Timer timer = new Timer();
    private PagerAdapter adapter;
    private final List<PagerModel> pagerModels = new ArrayList<>();

    private boolean runAuto = false;
    private int colorActiveIndicator = 0xDE000000;
    private int colorInactiveIndicator = 0x33000000;
    private final String TAG = "RecyclerViewPager";
    private int millis = 2000;

    private float cardWidthRatio = 0.88f, cardHeightRatio = 0.56f;

    private int cardElevationDP = 4, cardRadiusDP = 12;

    public float getCardWidthRatio() {
        return cardWidthRatio;
    }

    public void setCardWidthRatio(float cardWidthRatio) {
        this.cardWidthRatio = cardWidthRatio;
    }

    public float getCardHeightRatio() {
        return cardHeightRatio;
    }

    public void setCardHeightRatio(float cardHeightRatio) {
        this.cardHeightRatio = cardHeightRatio;
    }

    public int getCardElevationDP() {
        return cardElevationDP;
    }

    public void setCardElevationDP(int cardElevationDP) {
        this.cardElevationDP = cardElevationDP;
    }

    public int getCardRadiusDP() {
        return cardRadiusDP;
    }

    public void setCardRadiusDP(int cardRadiusDP) {
        this.cardRadiusDP = cardRadiusDP;
    }

    public RecyclerViewPager(@NonNull Context context) {
        super(context);
        Log.e(TAG, "RecyclerViewPager(context)");
        initState();
    }


    public RecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "RecyclerViewPager(context,attrs)");
        initState(context, attrs);
    }

    public RecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.e(TAG, "RecyclerViewPager(context,attrs,defStyle)");
        initState(context, attrs);
    }

    private void initState() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(lm);

        setClipToPadding(false);
        setPaddingRelative((int) DisplayUtils.getDisplay().convertDpToPixel(12, getContext()), 0, (int) DisplayUtils.getDisplay().convertDpToPixel(12, getContext()), 0);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);
    }

    private void initState(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle") final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewPager);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(lm);

        setClipToPadding(false);
        setPaddingRelative((int) DisplayUtils.getDisplay().convertDpToPixel(12, getContext()), 0, (int) DisplayUtils.getDisplay().convertDpToPixel(12, getContext()), 0);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);
        runAuto = a.getBoolean(R.styleable.RecyclerViewPager_svp_runAuto, false);
        colorActiveIndicator = a.getColor(R.styleable.RecyclerViewPager_svp_colorActiveIndicator, 0xDE000000);
        colorInactiveIndicator = a.getColor(R.styleable.RecyclerViewPager_svp_colorInactiveIndicator, 0x33000000);
        millis = a.getInt(R.styleable.RecyclerViewPager_svp_timeMillis, 2000);
    }

    private void scrollToNext() {
        if (count >= getAdapter().getItemCount() - 1)
            count = -1;

        smoothScrollToPosition(count + 1);
    }

    public void addItem(PagerModel pagerModel) {
        this.pagerModels.add(pagerModel);
    }

    public void start() {
        adapter = new PagerAdapter(getContext(), pagerModels);

        adapter.setCardWidthRelativeScreen(cardWidthRatio);
        adapter.setCardHeightRelativeScreen(cardHeightRatio);
        adapter.setCardElevationDP(cardElevationDP);
        adapter.setCardRadiusDP(cardRadiusDP);

        setAdapter(adapter);

        if (runAuto) {
            if (timer == null)
                timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    scrollToNext();
                }
            }, millis, millis);
        } else {
            timer = null;
        }

        addItemDecoration(new Indicator(colorActiveIndicator, colorInactiveIndicator));
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) getLayoutManager());

                if (layoutManager != null) count = layoutManager.findFirstVisibleItemPosition();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    public void setAutoRun(boolean auto) {
        this.runAuto = auto;
    }

    public void setColorActiveIndicator(int color) {
        this.colorActiveIndicator = color;
    }

    public void setColorInactiveIndicator(int color) {
        this.colorInactiveIndicator = color;
    }

    public void setTime(int timeMillis) {
        this.millis = millis;
    }

    public void setOnItemClickListener(PagerAdapter.OnItemClickListener itemClickListener) {
        try {
            ((PagerAdapter) getAdapter()).setOnclickItemListener(itemClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
