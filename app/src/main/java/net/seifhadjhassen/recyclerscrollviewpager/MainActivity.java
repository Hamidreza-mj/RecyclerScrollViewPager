package net.seifhadjhassen.recyclerscrollviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.seifhadjhassen.recyclerviewpager.PagerAdapter;
import net.seifhadjhassen.recyclerviewpager.PagerModel;
import net.seifhadjhassen.recyclerviewpager.RecyclerViewPager;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewPager recyclerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPager = findViewById(R.id.pager);

        recyclerViewPager.setCardElevationDP(4);
        recyclerViewPager.setCardRadiusDP(12);
        recyclerViewPager.setCardWidthRatio(0.88f);
        recyclerViewPager.setCardHeightRatio(0.5f);

        // add item from resource
        recyclerViewPager.addItem(new PagerModel(R.drawable.cover1, "Vikings", this));
        recyclerViewPager.addItem(new PagerModel(R.drawable.cover3, "Flash", this));
        recyclerViewPager.addItem(new PagerModel(R.drawable.cover2, "The nutcracker and the four realms", this));
        recyclerViewPager.addItem(new PagerModel(R.drawable.cover1, "The nutcracker and the four realms", this));

        //add item from url
        //recyclerViewPager.addItem(new PagerModel("http://i.imgur.com/DvpvklR.png","Vikings"));

        //recyclerViewPager.setAutoRun(true);
        recyclerViewPager.start();


        recyclerViewPager.setOnItemClickListener(new PagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "click pos " + position, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
