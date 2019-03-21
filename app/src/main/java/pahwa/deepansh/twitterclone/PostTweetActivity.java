package pahwa.deepansh.twitterclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.appcompat.app.AppCompatActivity;

public class PostTweetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText sendTweet;
    private Button sendTweetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tweet);
        sendTweet = findViewById(R.id.sendTweet);
        sendTweetButton = findViewById(R.id.sendTweetButton);

        sendTweetButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ParseObject parseObject = new ParseObject("MyTweet");
        parseObject.put("tweet",sendTweet.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                   if (e == null){

                       FancyToast.makeText(PostTweetActivity.this,ParseUser.getCurrentUser().getUsername()+ " 's Tweet has been saved",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                   } else {
                       FancyToast.makeText(PostTweetActivity.this,"Unknown Error",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();

                   }
                   progressDialog.dismiss();
            }
        });

    }
}
