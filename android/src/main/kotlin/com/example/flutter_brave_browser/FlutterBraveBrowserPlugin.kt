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

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_brave_browser")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
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
        val bravePackageName = "com.brave.browser"
        val uri = Uri.parse(url)

        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setInstantAppsEnabled(false)
            .build()

        customTabsIntent.intent.setPackage(bravePackageName)

        try {
            context?.let {
                customTabsIntent.launchUrl(it, uri)
                result.success(true)
            } ?: run {
                result.error("CONTEXT_NULL", "Context is not available", null)
            }
        } catch (e: ActivityNotFoundException) {
            result.error("FAILED_TO_OPEN", "Brave Browser is not installed", null)
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        context = null
    }
}
