package pahwa.deepansh.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<String> tUsers;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle("HOME PAGE!!");

        listView= findViewById(R.id.listView);
        tUsers = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked,tUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);

        try {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(objects.size() > 0 && e == null){

                        for (ParseUser twitterUser : objects){
                            tUsers.add(twitterUser.getUsername());
                        }

                        listView.setAdapter(adapter);

                        //will check if a user is following someone or not in backend
                        for (String twitterUser : tUsers) {

                            if (ParseUser.getCurrentUser().getList("fanOf")!= null) {

                                if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)) {
                                    listView.setItemChecked(tUsers.indexOf(twitterUser), true);
                                }
                            }
                        }
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logoutUserItem){

            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {

                    Intent intent = new Intent(HomePageActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        } else if (item.getItemId() == R.id.sendTweetItem){
            Intent intent = new Intent(HomePageActivity.this,PostTweetActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.viewTweets){

            Intent intent = new Intent(HomePageActivity.this,ViewTweetActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;

        if (checkedTextView.isChecked()){
            FancyToast.makeText(HomePageActivity.this, tUsers.get(position) + " is now followed",FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();

            //to follow a user
            ParseUser.getCurrentUser().add("fanOf",tUsers.get(position));
        } else {
            FancyToast.makeText(HomePageActivity.this, tUsers.get(position) + " is now unfollowed",FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();

           //to unfollow a user
            ParseUser.getCurrentUser().getList("fanOf").remove(tUsers.get(position));
            List currentUserFanOfList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentUserFanOfList);

        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    FancyToast.makeText(HomePageActivity.this,"Saved",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                }
            }
        });

    }
}
