package pahwa.deepansh.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("NXiKQYWurNt2KtsGU4EQ92Ihwk9PDNxUXXDC4qxj")
                .clientKey("8XZkFZdNhUrJniqTTaD0eivJ8lbt6Tsy34TrOPxH")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}