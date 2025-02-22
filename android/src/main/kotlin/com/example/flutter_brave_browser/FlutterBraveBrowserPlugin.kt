package com.example.flutter_brave_browser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class FlutterBraveBrowserPlugin: FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private lateinit var applicationContext: android.content.Context  // Tambahkan ini

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        applicationContext = flutterPluginBinding.applicationContext // Simpan context
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_brave_browser")
        channel.setMethodCallHandler(this)
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
        intent.setPackage("com.brave.browser") // Paket Brave Browser
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            applicationContext.startActivity(intent) // Perbaiki di sini
            result.success(true)
        } catch (e: Exception) {
            result.error("FAILED_TO_OPEN", "Brave Browser not installed", null)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
