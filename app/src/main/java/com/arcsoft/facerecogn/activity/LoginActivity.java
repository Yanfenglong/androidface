package com.arcsoft.facerecogn.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arcsoft.facerecogn.R;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsersname;
    EditText edtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsersname = findViewById(R.id.edtUsername);
        edtpassword = findViewById(R.id.edtpassword);
    }

    public void btnLoginClick(View view) {
        String usersname = edtUsersname.getText().toString();
        String password = edtpassword.getText().toString();

        if (TextUtils.isEmpty(usersname)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }

        if (!TextUtils.isEmpty(usersname) && !TextUtils.isEmpty(password)) {
            if (usersname.equals("admin") && password.equals("123456")) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                new AlertDialog.Builder(this).setTitle("Error!").setMessage("用户名或密码错误！")
                        .setNegativeButton("OK", null)
                        .show();
            }
        }
    }
}
