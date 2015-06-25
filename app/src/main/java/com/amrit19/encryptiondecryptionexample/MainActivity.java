package com.amrit19.encryptiondecryptionexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static String seedValue = "I AM UNBREAKABLE";
    public static String MESSAGE = "No one can read this message without decrypting me.";

    private Button btnDecode;
    private Button btnEncrypt;

    private EditText etSeedKey;
    private EditText etOriginalMessage;
    private EditText etEncryptedMessage;
    private EditText etDecodeMessage;
    private TextView tvClientKeyfromServer;
    private TextView tvClientEncryptedMsgFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEncrypt = (Button) findViewById(R.id.btnEncrypt);
        btnDecode = (Button) findViewById(R.id.btnDecode);

        etSeedKey = (EditText) findViewById(R.id.etSeedKey);
        etOriginalMessage = (EditText) findViewById(R.id.etOriginalMessage);
        etDecodeMessage = (EditText) findViewById(R.id.etDecodeMessage);
        etEncryptedMessage = (EditText) findViewById(R.id.etEncryptedMessage);

        tvClientKeyfromServer = (TextView) findViewById(R.id.tvClientKeyfromServer);
        tvClientEncryptedMsgFromServer = (TextView) findViewById(R.id.tvClientEncryptedMsgFromServer);

        btnEncrypt.setOnClickListener(this);
        btnDecode.setOnClickListener(this);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEncrypt:

                String seedKey = etSeedKey.getText().toString();
                String originalMsg = etOriginalMessage.getText().toString();
                String encryptedData = null;
                String errorMsg = "";

                Log.d("EncryptDecrypt", "serverKey = " + seedKey + ", originalMsg = " + originalMsg);

                if(seedKey == null || seedKey.trim().length() != 16){
                    Toast.makeText(MainActivity.this, "Seed key must be 16 char long!!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(originalMsg == null || originalMsg.trim().equals("")){
                    Toast.makeText(MainActivity.this, "Enter message to encrypt!!", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    encryptedData = AESHelper.encrypt(seedKey, originalMsg);
                    Log.i("EncryptDecrypt", "Encoded String " + encryptedData);

                } catch (Exception e) {
                    errorMsg = "Some error occured.. Try again!!";
                    e.printStackTrace();
                }

                if (encryptedData != null) {
                    etEncryptedMessage.setText(encryptedData);
                    tvClientKeyfromServer.setText(seedKey);
                    tvClientEncryptedMsgFromServer.setText(encryptedData);
                }
                else {
                   etEncryptedMessage.setText(errorMsg);
                   tvClientKeyfromServer.setText(errorMsg);
                    tvClientEncryptedMsgFromServer.setText(errorMsg);
                }
                break;

            case R.id.btnDecode:

                String seedValue = tvClientKeyfromServer.getText().toString();
                String encryptedDataMessage = etEncryptedMessage.getText().toString();

                Log.d("EncryptDecrypt", "seedValue = " + seedValue + ", encryptedDataMessage = " +encryptedDataMessage);

                try {

                    if (seedValue != null && encryptedDataMessage != null && !encryptedDataMessage.equals("")) {
                        String decryptedData = AESHelper.decrypt(seedValue, encryptedDataMessage);
                        Log.i("EncryptDecrypt", "Decoded String " + decryptedData);

                        if (decryptedData != null) {
                            etDecodeMessage.setText(decryptedData);
                        } else {
                            etDecodeMessage.setText("Some error occured.. Try again!!");
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Encrypt data first!!", Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }

    }
}
