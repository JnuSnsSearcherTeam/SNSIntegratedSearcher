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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.core.FacebookSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.InstagramSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.SNSSearcherContract;
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

    FacebookSearcherPresenter facebookSearcherPresenter;
    InstagramSearcherPresenter instagramSearcherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facebookSearcherPresenter = new FacebookSearcherPresenter(this);
        instagramSearcherPresenter = new InstagramSearcherPresenter(this);

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

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                name = editText.getText().toString();

                facebookSearcherPresenter.setKeyword(name);
                instagramSearcherPresenter.setTag(name);

                facebookSearcherPresenter.loadItem(new SNSSearcherContract.LoadCompleteListner() {
                    @Override
                    public void onComplete(ArrayList<Object> result) {
                        if (viewPager != null) {
                            setupViewPager(viewPager);
                        }
                    }
                });
                instagramSearcherPresenter.loadItem(new SNSSearcherContract.LoadCompleteListner() {
                    @Override
                    public void onComplete(ArrayList<Object> result) {
                        if (viewPager != null) {
                            setupViewPager(viewPager);
                        }
                    }
                });
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FaceBookFragment(), "Facebook");
        adapter.addFragment(new TwitterFragment(), "Twitter");
        adapter.addFragment(new InstagramFragment(), "Instagram");
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
