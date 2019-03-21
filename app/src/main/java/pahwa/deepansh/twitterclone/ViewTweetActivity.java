package pahwa.deepansh.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewTweetActivity extends AppCompatActivity {

    private ListView listForViewTweet;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tweet);

        listForViewTweet = findViewById(R.id.listForViewTweet);
        userName = findViewById(R.id.userName);

        userName.setText(ParseUser.getCurrentUser().getUsername() + " 's Activity");

        final ArrayList<HashMap< String , String >> tweetList = new ArrayList<>();
        final SimpleAdapter simpleAdapter = new SimpleAdapter(ViewTweetActivity.this,tweetList,
                android.R.layout.simple_list_item_2,
                new String[]{"tweetUserName","tweetValue"},
                new int[]{android.R.id.text1,android.R.id.text2});

        try{
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("MyTweet");
            parseQuery.whereContainedIn("user",ParseUser.getCurrentUser().getList("fanOf"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (objects.size() > 0 && e == null){
                        for(ParseObject tweetObject : objects){
                            HashMap<String,String> userTweet = new HashMap<>();
                            userTweet.put("tweetUserName",tweetObject.getString("user"));
                            userTweet.put("tweetValue",tweetObject.getString("tweet"));
                            tweetList.add(userTweet);
                        }

                        listForViewTweet.setAdapter(simpleAdapter);
                    }

                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
