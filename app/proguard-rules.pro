# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Preserve model classes to prevent removal or obfuscation
-keepclassmembers class com.example.myapp.models.** {
    *;
}

# Preserve Parcelable classes to prevent removal of the CREATOR field
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Preserve Retrofit interfaces and annotations for HTTP requests
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit.** { *; }
-keep interface retrofit.** { *; }
-keepclassmembers class ** {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Preserve Gson or Moshi serialization classes
-keepclassmembers class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# If you're using Moshi
-keepclassmembers class * {
  @com.squareup.moshi.Json <fields>;
}

# Preserve WebView JavaScript interface methods (if used)
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Keep debugging line numbers (Optional for crash reports)
-keepattributes SourceFile,LineNumberTable

# Keep any native libraries (Optional, if you're using native code)
-keep class com.example.mylibrary.** { *; }

# Additional optimization for Android
-optimizationpasses 5
-dontwarn android.support.**
-dontwarn org.codehaus.**
-dontwarn javax.annotation.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.picasso.**

# Preserve resources for libraries like Glide or Picasso
-keep class com.bumptech.glide.** { *; }
-keep class com.squareup.picasso.** { *; }

# Preserve View binding (Optional, if you use view binding or data binding)
-keep class **.databinding.** { *; }
-keep class **.BR { *; }

# Obfuscate everything else
-allowaccessmodification
-renamesourcefileattribute SourceFile
