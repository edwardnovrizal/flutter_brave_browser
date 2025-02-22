package com.example.flutter_brave_browser

import android.content.Context
import android.net.Uri
import androidx.annotation.NonNull
import androidx.browser.customtabs.CustomTabsIntent
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class FlutterBraveBrowserPlugin: FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_brave_browser")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method == "openBraveCustomTab") {
            val url: String? = call.argument("url")
            if (url != null) {
                openBraveCustomTab(url, result)
            } else {
                result.error("INVALID_URL", "URL is required", null)
            }
        } else {
            result.notImplemented()
        }
    }

    private fun openBraveCustomTab(url: String, result: MethodChannel.Result) {
        try {
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true) // Menampilkan judul di tab
                .build()

            // Gunakan Brave sebagai browser default untuk Custom Tabs
            customTabsIntent.intent.setPackage("com.brave.browser")

            customTabsIntent.launchUrl(context, Uri.parse(url))
            result.success(true)
        } catch (e: Exception) {
            result.error("FAILED_TO_OPEN", "Brave Browser not installed or cannot be opened", null)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
