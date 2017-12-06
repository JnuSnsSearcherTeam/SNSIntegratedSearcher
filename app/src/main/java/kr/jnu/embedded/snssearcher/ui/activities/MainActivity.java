package kr.jnu.embedded.snssearcher.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.core.FacebookSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.InstagramSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.TwitterSearcherPresenter;
import kr.jnu.embedded.snssearcher.ui.fragments.FaceBookFragment;
import kr.jnu.embedded.snssearcher.ui.fragments.InstagramFragment;
import kr.jnu.embedded.snssearcher.ui.fragments.TwitterFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionMenu fab;
    private FloatingActionButton fab1;
    EditText editText;
    ImageButton imageButton;
    String name;

    ViewPagerAdapter adapter;

    FacebookSearcherPresenter facebookSearcherPresenter;
    InstagramSearcherPresenter instagramSearcherPresenter;
    TwitterSearcherPresenter twitterSearcherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facebookSearcherPresenter = new FacebookSearcherPresenter();
        instagramSearcherPresenter = new InstagramSearcherPresenter();
        twitterSearcherPresenter = new TwitterSearcherPresenter();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab1 =(FloatingActionButton) findViewById(R.id.fab1);
        fab1.setLabelText("계정설정");
        fab1.setOnClickListener(clickListener);
        fab.setClosedOnTouchOutside(true);



        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        editText = (EditText) findViewById(R.id.edittext1);
        imageButton = (ImageButton) findViewById(R.id.imageButton1);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                name = editText.getText().toString();

                facebookSearcherPresenter.setKeyword(name);
                instagramSearcherPresenter.setTag(name);
                twitterSearcherPresenter.setKeyword(name);

                facebookSearcherPresenter.loadItem();
                twitterSearcherPresenter.loadItem();
                instagramSearcherPresenter.loadItem();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        FaceBookFragment fb = new FaceBookFragment();
        fb.setPresenter(facebookSearcherPresenter);

        TwitterFragment tf = new TwitterFragment();
        tf.setPresenter(twitterSearcherPresenter);

        InstagramFragment instagramFragment = new InstagramFragment();
        instagramFragment.setPresenter(instagramSearcherPresenter);

        adapter.addFragment(fb, "Facebook");
        adapter.addFragment(tf, "Twitter");
        adapter.addFragment(instagramFragment, "Instagram");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab1:
                    startActivity(new Intent(MainActivity.this,FacebookLoginActivity.class));
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    break;

            }
        }
    };
}
