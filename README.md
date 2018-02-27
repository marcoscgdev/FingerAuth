# FingerAuth
Fingerprint authentication made easy.

---

## Releases:

#### Current release: 1.0.0.

You can see all the library releases [here](https://github.com/marcoscgdev/FingerAuth/releases).

---

## Screenshots

|FingerAuthDialog|onSuccess|onFailure|
|:------:|:------:|:------:|
|<img src="https://raw.githubusercontent.com/marcoscgdev/FingerAuth/master/screenshots/1.jpg" width="260">|<img src="https://raw.githubusercontent.com/marcoscgdev/FingerAuth/master/screenshots/2.jpg" width="260">|<img src="https://raw.githubusercontent.com/marcoscgdev/FingerAuth/master/screenshots/3.jpg" width="260">|

Download the sample apk [here](https://github.com/marcoscgdev/FingerAuth/releases/download/1.0.0/app-debug.apk).

---

## Usage:

### Adding the depencency

Add this to your root *build.gradle* file:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Now add the dependency to your app build.gradle file:

```
compile 'com.github.marcoscgdev:FingerAuth:1.0.0'
```

### Creating a dialog

Here is a complete snippet of it usage:

```java
new FingerAuthDialog(this)
        .setTitle("Sign in")
        .setCancelable(false)
        .setPositiveButton("Use password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do something
            }
        })
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
        })
        .show();
```

Show dialog only on devices that support fingerprint auth:

```java
boolean hasFingerprintSupport = FingerAuth.hasFingerprintSupport(this);

if (hasFingerprintSupport)
    fingerAuthDialog.show();
```

---
>See the *sample project* to clarify any queries you may have.

---

## License

```
Copyright 2018 Marcos Calvo García

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

