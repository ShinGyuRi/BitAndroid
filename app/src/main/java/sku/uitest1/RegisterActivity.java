package sku.uitest1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qualcomm.vuforia.samples.Books.R;

import sku.dbconn.RegisterAsync;


/**
 * Created by 618 on 2015-09-11.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    public EditText etId, etPassword, etName, etPhone, etPost1, etPost2, etAddress, etDetailAddress, etEmail;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bRegister = (Button) findViewById(R.id.bRegister);

        etId = (EditText) findViewById(R.id.etID);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPost1 = (EditText) findViewById(R.id.etPost1);
        etPost2 = (EditText) findViewById(R.id.etPost2);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etDetailAddress = (EditText) findViewById(R.id.etDetailAddress);
        etEmail = (EditText) findViewById(R.id.etEmail);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String id = etId.getText().toString();
        String password = etPassword.getText().toString();
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String post1 = etPost1.getText().toString();
        String post2 = etPost1.getText().toString();
        String address = etAddress.getText().toString();
        String detail_address = etDetailAddress.getText().toString();
        String email = etEmail.getText().toString();

        RegisterAsync registerAsync = new RegisterAsync();

        switch (v.getId()){
            case R.id.bRegister:

                registerAsync.execute(id,password,name,phone,post1,post2,address,detail_address,email);

                finish();



                break;
        }

    }


}
