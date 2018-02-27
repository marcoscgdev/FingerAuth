package com.marcoscg.fingerauth;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by @MarcosCGdev on 25/02/2018.
 */

public class FingerAuthDialog {

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private FingerAuth.OnFingerAuthListener onFingerAuthListener;

    private AppCompatImageView imageView;
    private TextView textView;

    private FingerAuth fingerAuth;

    private int successDelay = 1000;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public FingerAuthDialog(Activity activity) {
        builder = new AlertDialog.Builder(activity, R.style.FingerAuthDialogStyle);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (fingerAuth!=null)
                    fingerAuth.cancelSignal();
            }
        });

        View dialogView = activity.getLayoutInflater().inflate(R.layout.fingerauth_dialog_content, null);
        imageView = (AppCompatImageView) dialogView.findViewById(R.id.fingerauth_dialog_icon);
        textView = (TextView) dialogView.findViewById(R.id.fingerauth_dialog_status);
        builder.setView(dialogView);
        init(activity);
    }

    public FingerAuthDialog setTitle(CharSequence title) {
        builder.setTitle(title);
        return this;
    }

    public FingerAuthDialog setCancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
        return this;
    }

    public FingerAuthDialog setPositiveButton(CharSequence text, DialogInterface.OnClickListener onClickListener) {
        builder.setPositiveButton(text, onClickListener);
        return this;
    }

    public FingerAuthDialog setPositiveButton(@StringRes int text, DialogInterface.OnClickListener onClickListener) {
        builder.setPositiveButton(text, onClickListener);
        return this;
    }

    public FingerAuthDialog setNegativeButton(CharSequence text, DialogInterface.OnClickListener onClickListener) {
        builder.setNegativeButton(text, onClickListener);
        return this;
    }

    public FingerAuthDialog setNegativeButton(@StringRes int text, DialogInterface.OnClickListener onClickListener) {
        builder.setNegativeButton(text, onClickListener);
        return this;
    }

    public FingerAuthDialog setMaxFailedCount(int maxFailedCount) {
        if (fingerAuth!=null)
            fingerAuth.setMaxFailedCount(maxFailedCount);
        return this;
    }

    public FingerAuthDialog setSuccessDelay(int successDelayMillis) {
        successDelay = successDelayMillis;
        return this;
    }

    public FingerAuthDialog setOnFingerAuthListener(FingerAuth.OnFingerAuthListener onFingerAuthListener) {
        this.onFingerAuthListener = onFingerAuthListener;
        return this;
    }

    public void show() {
        if (alertDialog == null)
            alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismiss() {
        if (alertDialog!=null)
            alertDialog.dismiss();
    }

    private void init(final Context context) {
        fingerAuth = new FingerAuth(context);
        fingerAuth.setOnFingerAuthListener(new FingerAuth.OnFingerAuthListener() {
            @Override
            public void onFailure() {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_fingerprint_error, null));
                textView.setText(context.getResources().getString(R.string.fingerauth_dialog_not_recognized));
                textView.setTextColor(context.getResources().getColor(R.color.fingerauth_dialog_color_error));
                onFingerAuthListener.onFailure();
            }

            @Override
            public void onError() {
                onFingerAuthListener.onError();
                dismiss();
            }

            @Override
            public void onSuccess() {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_fingerprint_success, null));
                textView.setText(context.getResources().getString(R.string.fingerauth_dialog_success));
                textView.setTextColor(context.getResources().getColor(R.color.fingerauth_dialog_color_accent));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onFingerAuthListener.onSuccess();
                        dismiss();
                    }
                }, successDelay);
            }
        });
    }

}
