package com.example.flutter_brave_browser
 
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class FlutterBraveBrowserPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private var context: android.content.Context? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_brave_browser")
    channel.setMethodCallHandler(this)
    applicationContext = flutterPluginBinding.applicationContext // ✅ Simpan Context dengan benar
}

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method == "openBrave") {
            val url: String? = call.argument("url")
            if (url != null) {
                openBrave(url, result)
            } else {
                result.error("INVALID_URL", "URL is required", null)
            }
        } else {
            result.notImplemented()
        }
    }

    private fun openBrave(url: String, result: MethodChannel.Result) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.setPackage("com.brave.browser")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // ✅ Tambahkan flag ini

    try {
        applicationContext.startActivity(intent) // ✅ Panggil dengan applicationContext yang benar
        result.success(true)
    } catch (e: Exception) {
        result.error("FAILED_TO_OPEN", "Brave Browser not installed or cannot be opened", null)
    }
}

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        context = null
    }
}
