
package com.azizulhakim.todotogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.azizulhakim.todotogether.fragment.TaskDoingFragment;
import com.azizulhakim.todotogether.fragment.TaskDoneFragment;
import com.azizulhakim.todotogether.fragment.TaskToDoFragment;
import com.azizulhakim.todotogether.models.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TaskListPage extends InterfaceActivity {

    private static final String TAG = "TaskListPage";

    public static final String GROUP_KEY = "post_key";
    private String groupKey;
    private String groupName;
    private TextView groupNameView;

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page__tasks_of_group);

        groupKey = getIntent().getStringExtra(GROUP_KEY);
        FirebaseUtil.setCurrentGroup(groupKey);

        DatabaseReference groupRef = FirebaseUtil.getGroupReference();
        DatabaseReference curGroupRef = groupRef.child(groupKey);
        groupNameView = (TextView) findViewById(R.id.group_name_fragment);
        curGroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Group group = dataSnapshot.getValue(Group.class);
                groupName = group.groupname;
                groupNameView.setText(groupName);;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new TaskToDoFragment(),
                    new TaskDoingFragment(),
                    new TaskDoneFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    "To Do",
                    "Doing",
                    "Done"
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskListPage.this, CreateNewPostPage.class));
            }
        });
        findViewById(R.id.fab_group_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "group button clicked.");
                startActivity(new Intent(TaskListPage.this, GroupDetailsPage.class));
            }
        });

    }

    public void onBackPressed() {
        //this.moveTaskToBack(true);
        startActivity(new Intent(TaskListPage.this, HomePage.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInPage.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}