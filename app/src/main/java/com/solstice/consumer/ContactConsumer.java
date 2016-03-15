package com.solstice.consumer;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.solstice.contactList.User;
import com.solstice.contactList.UserListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* Esta clase se ocupa de conectarse con el WebService y de Bindear la data que obtiene*/

public class ContactConsumer extends AsyncTask<RecyclerView, Void, Void> {

    private ArrayList<User> users = new ArrayList<User>();
    private RecyclerView recyclerView;


    @Override
    protected Void doInBackground(RecyclerView... params){
        recyclerView = params[0];
        try{
            Response response = getContacts();
            String rawData = response.body().string().replace("\n", "");
            try{
                JSONArray jsonData = new JSONArray(rawData);
                Log.e("REPONSE", response.message());
                users = parseData(jsonData);
            }catch(JSONException e){
                e.fillInStackTrace();
            }
        }catch(Exception e){
            e.fillInStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        recyclerView.setAdapter(new UserListActivity.SimpleItemRecyclerViewAdapter(users));
    }


    //Connects to server
    private Response getContacts() throws IOException {
        OkHttpClient client = new OkHttpClient();
        return client.newCall(new Request.Builder().url("https://solstice.applauncher.com/external/contacts.json").build()).execute();

    }

    // Parse Server data to usable data. Creates a ContactList User for each JSONObject.
    private ArrayList<User> parseData(JSONArray data) throws JSONException{
        for (int i = 0; i < data.length(); i++){
            JSONObject obj = data.getJSONObject(i);
            User user = new User(obj);
            users.add(new User(obj));
        }
        return users;
    }
}

