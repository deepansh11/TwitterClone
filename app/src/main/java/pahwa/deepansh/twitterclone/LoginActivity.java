package pahwa.deepansh.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText enterEmailLogin,enterPasswordLogin ;
    private Button signUp,logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enterEmailLogin = findViewById(R.id.enterEmailLogin);
        enterPasswordLogin = findViewById(R.id.enterPasswordLogin);

        enterPasswordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(signUp);
                }
                return false;
            }
        });


        signUp = findViewById(R.id.SignUpLogin);
        logIn = findViewById(R.id.LoginLogin);

        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.SignUpLogin:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.LoginLogin:

                if (enterEmailLogin.getText().toString().equals("") ||
                        enterPasswordLogin.getText().toString().equals("")) {

                    FancyToast.makeText(LoginActivity.this,
                            "Email, Password required!!",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                } else {

                    ParseUser.logInInBackground(enterEmailLogin.getText().toString(),
                            enterPasswordLogin.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {

                                    if (user != null && e == null) {
                                        FancyToast.makeText(LoginActivity.this,
                                                user.getUsername() + " Logged in",
                                                FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                        transitionToHomePage();

                                    } else {
                                        FancyToast.makeText(LoginActivity.this,
                                                e.getMessage(),
                                                FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                    }

                                }
                            });
                }


                break;
        }


    }


    public void rootLayOutTapped(View view) {

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transitionToHomePage(){
        {

            Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
            startActivity(intent);
            finish();
        }
    }


}


