
package com.asim.retrofit;
 
import android.content.Context;
import android.content.Intent;
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
 
 
public class RegisterActivity extends AppCompatActivity {
    EditText username,email,password,cpassword;
    TextView login;
    Button btnRegister;
    Vibrator v;
    //change to your register url
    final String registerUrl = "http://192.168.43.254/school/register.php";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
 
        username = (EditText) findViewById(R.id.registerName);
        email = (EditText) findViewById(R.id.registerEmail);
        password = (EditText) findViewById(R.id.confirmpassword);
        cpassword = (EditText) findViewById(R.id.confirmpassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        login = (TextView) findViewById(R.id.login);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
 
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
 
    }
 
    private void validateUserData() {
 
        //find values
        final String reg_name = username.getText().toString();
        final String reg_email = email.getText().toString();
        final String reg_password = cpassword.getText().toString();
        final String reg_cpassword = cpassword.getText().toString();
 
 
//        checking if username is empty
        if (TextUtils.isEmpty(reg_name)) {
            username.setError("Please enter username");
            username.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if email is empty
        if (TextUtils.isEmpty(reg_email)) {
            email.setError("Please enter email");
            email.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(reg_password)) {
            password.setError("Please enter password");
            password.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if password matches
        if (!reg_password.equals(reg_cpassword)) {
            password.setError("Password Does not Match");
            password.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
 
        //After Validating we register User
        registerUser(reg_name,reg_email,reg_password);
 
    }
 
    private void registerUser(String user_name, String user_mail, String user_pass) {
 
 
        final String reg_username = username.getText().toString();
        final String reg_email = email.getText().toString();
        final String reg_password = cpassword.getText().toString();
 
        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<model> login = api.register(user_name,user_mail,user_pass);
 
        login.enqueue(new Callback<model>() {
            @Override
            public void onResponse(Call<model> call, Response<model> response) {
 
                if(response.body().getIsSuccess() == 1){
                    //get username
                    String user = response.body().getUsername();
 
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
 
            }
 
            @Override
            public void onFailure(Call<model> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
 
            }
        });
 
 
 
 
 
    }
 
}
