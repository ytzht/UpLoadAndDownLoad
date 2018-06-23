package vip.mae.uploadanddownload;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout til_account, til_pwd;
    private EditText et_account, et_pwd;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initData();

        initClick();
    }

    private void initClick() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_account.getText().toString().equals("123456")){
                    til_account.setError("账户不存在");
                }else {
                    til_account.setErrorEnabled(false);
                    if (!et_pwd.getText().toString().equals("123456")){
                        til_pwd.setError("密码错误");
                    }else {
                        til_pwd.setErrorEnabled(false);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            }
        });
    }

    private void initData() {
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() < 6) {
//                    til_account.setError("账户至少6个字符");
//                } else {
//                    til_account.setErrorEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                til_account.setErrorEnabled(false);
            }
        });

        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() < 6) {
//                    til_pwd.setError("密码至少6个字符");
//                } else {
//                    til_pwd.setErrorEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                til_pwd.setErrorEnabled(false);
            }
        });
    }

    private void initView() {
        til_account = findViewById(R.id.til_account);
        til_pwd = findViewById(R.id.til_pwd);
        et_account = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
    }







}
