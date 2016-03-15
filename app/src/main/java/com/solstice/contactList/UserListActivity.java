package com.solstice.contactList;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.koushikdutta.ion.Ion;
import com.solstice.consumer.ContactConsumer;

import java.awt.font.TextAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Users. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link UserDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class UserListActivity extends AppCompatActivity {

    private static boolean mTwoPane;
    private static android.support.v4.app.FragmentManager fragmentManager;
    private static String USER_KEY = "USER_Object";
    private static String GET_DATA = "CHECKDATA";
    ContactConsumer consumer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ButterKnife.bind(this);

        View recyclerView = findViewById(R.id.user_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.user_detail_container) != null) {
            mTwoPane = true;
        }

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()== R.id.favorite){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        // Open Shared Preferences
//        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
//        String serializedData = prefs.getString(GET_DATA, null);
//        if(serializedData == null){
            new ContactConsumer().execute(recyclerView);
//        }else{
//            recyclerView.setAdapter(new UserListActivity.SimpleItemRecyclerViewAdapter((ArrayList<User>) deserialize(serializedData)));
//        }
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<User> mValues;

        public SimpleItemRecyclerViewAdapter(ArrayList<User> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.name.setText(mValues.get(position).getName());
            holder.mPhone.setText(mValues.get(position).getPhones().getHome());
            holder.mName.setText(mValues.get(position).getName());
            Ion.with(holder.mImageView)
            .load(mValues.get(position).getImageUrl());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(UserDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
                        arguments.putSerializable(USER_KEY, holder.mItem);
                        UserDetailFragment fragment = new UserDetailFragment();
                        fragment.setArguments(arguments);
                        fragmentManager.beginTransaction()
                                .replace(R.id.user_detail_container, fragment)
                                .commit();
                    } else {
                        try{
                            Context context = v.getContext();
                            Intent intent = new Intent(context, UserDetailActivity.class);
                            intent.putExtra(UserDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(USER_KEY, holder.mItem);
                            intent.putExtra(USER_KEY, bundle);
                            context.startActivity(intent);
                        }catch (Exception e){
                            e.fillInStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.userName) TextView name;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mPhone;
            public final TextView mName;
            public User mItem;
            //public final TextView mIdView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image);
                mPhone = (TextView) view.findViewById(R.id.phone);
                mName = (TextView) view.findViewById(R.id.userName);
                ButterKnife.bind(this, view);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        }
    }
}
