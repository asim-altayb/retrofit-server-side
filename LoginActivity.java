package com.asim.retrofit;
 
  import android.content.Context;
  import android.content.Intent;
  import android.content.SharedPreferences;
  import android.os.Vibrator;
  import android.support.v7.app.AppCompatActivity;
  import android.os.Bundle;
  import android.text.TextUtils;
  import android.util.Log;
  import android.view.View;
  import android.widget.Button;
  import android.widget.EditText;
  import android.widget.TextView;
  import android.widget.Toast;
  import org.json.JSONException;
  import org.json.JSONObject;
  import java.util.HashMap;
  import java.util.Map;
 
  import retrofit2.Call;
  import retrofit2.Callback;
  import retrofit2.Response;
 
 
public class MainActivity extends AppCompatActivity {
    EditText username_input,password_input;
    TextView register;
    Button btnLogin;
    Vibrator v;
 
    //change this to match your url
    final String loginURL = "http://192.168.43.254/school/login.php";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_input = findViewById(R.id.userName);
        password_input = findViewById(R.id.loginPassword);
        register = findViewById(R.id.register);
        btnLogin = findViewById(R.id.btnLogin);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
 
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData();
            }
        });
 
        //when someone clicks on login
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
 
    }
    private void validateUserData() {
 
        //first getting the values
        final String username = username_input.getText().toString();
        final String password = password_input.getText().toString();
 
        //checking if username is empty
        if (TextUtils.isEmpty(username)) {
            username_input.setError("Please enter your username");
            username_input.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            btnLogin.setEnabled(true);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password)) {
            password_input.setError("Please enter your password");
            password_input.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            btnLogin.setEnabled(true);
            return;
        }
 
 
 
        //Login User if everything is fine
        loginUser(username,password);
 
 
    }
 
    private void loginUser(String username, String password) {
 
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<model> login = api.login(username,password);
 
        login.enqueue(new Callback<model>() {
            @Override
            public void onResponse(Call<model> call, Response<model> response) {
 
                if(response.body().getIsSuccess() == 1){
                    //get username
                    String user = response.body().getUsername();
 
                    //storing the user in shared preferences
                    SharedPref.getInstance(MainActivity.this).storeUserName(user);
//                    Toast.makeText(MainActivity.this,response.body().getUsername(),Toast.LENGTH_LONG).show();
 
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
 
 
 
 
            }
 
            @Override
            public void onFailure(Call<model> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
 
            }
        });
 
 
            }
 
}
