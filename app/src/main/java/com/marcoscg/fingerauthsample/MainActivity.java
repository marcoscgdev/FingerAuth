package com.marcoscg.fingerauthsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.marcoscg.fingerauth.FingerAuth;
import com.marcoscg.fingerauth.FingerAuthDialog;

public class MainActivity extends AppCompatActivity {

    private FingerAuthDialog fingerAuthDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean hasFingerprintSupport = FingerAuth.hasFingerprintSupport(this);

        findViewById(R.id.openDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerAuthDialog = null;
                if (hasFingerprintSupport)
                    createAndShowDialog();
                else Toast.makeText(MainActivity.this, "Your device does not support fingerprint authentication", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAndShowDialog() {
        fingerAuthDialog = new FingerAuthDialog(this)
                .setTitle("Sign in")
                .setCancelable(false)
                .setPositiveButton("Use password", null)
                .setNegativeButton("Cancel", null)
                .setOnFingerAuthListener(new FingerAuth.OnFingerAuthListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
                    }
                });
        fingerAuthDialog.show();
    }
}
