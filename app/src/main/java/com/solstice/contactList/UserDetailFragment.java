package com.solstice.contactList;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.koushikdutta.ion.Ion;
import com.solstice.contactList.dummy.DummyContent;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.ProtectionDomain;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.text.SimpleDateFormat;

/**
 * A fragment representing a single User detail screen.
 * This fragment is either contained in a {@link UserListActivity}
 * in two-pane mode (on tablets) or a {@link UserDetailActivity}
 * on handsets.
 */
public class UserDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final String USER_KEY = "USER_Object";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private User mUser;
    private Toolbar appBarLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(USER_KEY)) {
            mUser = (User) getArguments().getSerializable(USER_KEY);

            Activity activity = this.getActivity();
            appBarLayout = (Toolbar) activity.findViewById(R.id.toolbar);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.user_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mUser != null) {
            new AsyncTask<View, Void, Void>(){
                private View view;
                private User newUser;
                @Override
                protected Void doInBackground(View... params){
                    view = params[0];
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Response resposne = client.newCall(new Request.Builder().url(mUser.getDetailsUrl()).build()).execute();
                        JSONObject obj = new JSONObject(resposne.body().string());
                        mUser.updateUser(obj);
                    }catch (Exception e){
                        e.fillInStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void result){
                    Ion.with((ImageView) view.findViewById(R.id.large_image)).load(mUser.getLargeImageURL());
                    TextView nameTextView = (TextView) view.findViewById(R.id.name_text);
                    nameTextView.setText(mUser.getName());
                    TextView copmanyTextView = (TextView) view.findViewById(R.id.company_text);
                    copmanyTextView.setText(mUser.getCompany());

                    //Second Row Phone Section
                    TextView homePhone = (TextView) view.findViewById(R.id.home_phone);
                    homePhone.setText(mUser.getPhones().getHome());

                    //Third Row Address Section
                    User.Address address = mUser.getAddress();
                    TextView street = (TextView) view.findViewById(R.id.street);
                    street.setText(address.getStreet());
                    TextView fullOuterAddress = (TextView) view.findViewById(R.id.outerAddress);
                    fullOuterAddress.setText(String.format("%1s, %2s %3s, %4s", address.getCity(), address.getState(), address.getCountry(), address.getZip()));

                    //Birthdate Section
                    TextView birthdate = (TextView) view.findViewById(R.id.birthdate);
                    Date date = mUser.getBirthDate();
                    birthdate.setText(String.format("%1s %1d, %1d", new SimpleDateFormat("MMMM").format(date), date.getDay(), date.getYear()));

                    //Email Section
                    TextView email = (TextView) view.findViewById(R.id.email);
                    email.setText(mUser.getEmail());

//                    if(mUser.getIsFavorite()){
//                        appBarLayout.setLogo(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
//
//                    }else{
//                        appBarLayout.setLogo(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
//                    }

                    rootView.setVisibility(View.VISIBLE);
                }
            }.execute(rootView);
            //First Row

        }

        return rootView;
    }
}
