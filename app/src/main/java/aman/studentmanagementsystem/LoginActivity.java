package aman.studentmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static TextView attempt;
    private static Button login_button;
    int attempt_counter=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        LoginButton();
    }

    public void LoginButton(){
        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        attempt = (TextView)findViewById(R.id.textView_attempt);
        login_button = (Button)findViewById(R.id.button_login);

        attempt.setText(Integer.toString(attempt_counter));

        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (username.getText().toString().equals("admin") &&
                                password.getText().toString().equals("admin")){
                            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Authenticating....");
                            progressDialog.show();

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent(LoginActivity.this,StudentMainActivity.class);
                                    LoginActivity.this.startActivity(mainIntent);
                                    progressDialog.show();
                                    progressDialog.setMessage("Authenticated");
                                }
                            }, 1000);
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Username and password is NOT correct",
                                    Toast.LENGTH_SHORT).show();
                            attempt_counter--;
                            attempt.setText(Integer.toString(attempt_counter));
                            if(attempt_counter==0)
                                login_button.setEnabled(false);
                        }
                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimpSlifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}