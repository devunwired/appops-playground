AppOps Playground
=================

This application is a standalone wrapper for the AppOpsSummary UI that was present in Android 4.3 JellyBean, and then commented out in Android 4.4 KitKat.  Additionally, it provides examples of using the features which are public in the AppOpsManager system service, beginning with Android 4.4 KitKat.

Usage
--------------
This application requires some system-level permissions that can only be obtained by signing the application with the same key as the platform image or installing this APK in the `/system/priv-app` directory of a rooted device (or emulator).

The prebuilt APK is signed with the platform keys from AOSP.  This is also the key used to sign most of the emulator instances.  Consequently, the prebuilt APK can be installed directly onto an official SDK emulator and the necessary permissions will be granted.

In other cases, the APK must be installed in `/system/priv-app` and the device restarted for installation to take place.  On a non-official emulator, this requires repackaging and replacing the system image once the new APK is copied into place.

Building
--------

This application requires access to a number of framework classes (or pieces of them) that are not part of the public SDK.  Therefore, in order to successfully build this application from source, it must be compiled inside an AOSP tree using the Android make build system.

```
~/aosp $ make framework
~/aosp/<app_source_directory> $ mm
```