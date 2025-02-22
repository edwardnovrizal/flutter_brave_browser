// You have generated a new plugin project without specifying the `--platforms`
// flag. A plugin project with no platform support was generated. To add a
// platform, run `flutter create -t plugin --platforms <platforms> .` under the
// same directory. You can also find a detailed instruction on how to add
// platforms in the `pubspec.yaml` at
// https://flutter.dev/to/pubspec-plugin-platforms.

import 'package:flutter/services.dart';

import 'flutter_brave_browser_platform_interface.dart';

class FlutterBraveBrowser {
  static const MethodChannel _channel = MethodChannel('flutter_brave_browser');

  Future<String?> getPlatformVersion() {
    return FlutterBraveBrowserPlatform.instance.getPlatformVersion();
  }

  static Future<void> openBrave(String url) async {
    try {
      await _channel.invokeMethod('openBrave', {'url': url});
    } on PlatformException catch (e) {
      print("Failed to open Brave Browser: ${e.message}");
    }
  }
}
