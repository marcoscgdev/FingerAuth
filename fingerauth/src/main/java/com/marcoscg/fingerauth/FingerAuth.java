package com.marcoscg.fingerauth;

import android.app.KeyguardManager;
import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

/**
 * Created by @MarcosCGdev on 25/02/2018.
 */

public class FingerAuth {

    private OnFingerAuthListener onFingerAuthListener;

    private FingerprintManagerCompat fingerprintManagerCompat;
    private CancellationSignal cancellationSignal;
    private FingerprintManagerCompat.AuthenticationCallback authenticationCallback;
    private int failedCount = 0, maxFailedCount = 3;

    public interface OnFingerAuthListener {
        void onSuccess();
        void onFailure();
        void onError();
    }

    public FingerAuth(Context context) {
        init(context);
    }

    public FingerAuth setOnFingerAuthListener(OnFingerAuthListener onFingerAuthListener) {
        this.onFingerAuthListener = onFingerAuthListener;
        return this;
    }

    public FingerAuth setMaxFailedCount(int maxFailedCount) {
        this.maxFailedCount = maxFailedCount;
        return this;
    }

    private void init(Context context) {
        failedCount = 0;

        authenticationCallback = new FingerprintManagerCompat.AuthenticationCallback() {
            @Override
            public void onAuthenticationFailed() {
                failedCount++;
                if (failedCount == maxFailedCount) {
                    failedCount = 0;
                    if (onFingerAuthListener != null) {
                        cancelSignal();
                        onFingerAuthListener.onError();
                    }
                } else {
                    onFingerAuthListener.onFailure();
                }
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                if (onFingerAuthListener != null) {
                    cancelSignal();
                    onFingerAuthListener.onSuccess();
                }
            }
        };

        fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        cancellationSignal = new CancellationSignal();

        fingerprintManagerCompat.authenticate(null, 0, cancellationSignal, authenticationCallback, null);
    }

    public void cancelSignal(){
        if (cancellationSignal!=null)
            cancellationSignal.cancel();
    }

    public static boolean hasFingerprintSupport(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        boolean hardwareSupport = fingerprintManager.isHardwareDetected();

        boolean secureSupport = true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager!=null)
                secureSupport = keyguardManager.isKeyguardSecure();
        }

        boolean hasPwd = fingerprintManager.hasEnrolledFingerprints();
        return hardwareSupport && secureSupport && hasPwd;
    }

}
