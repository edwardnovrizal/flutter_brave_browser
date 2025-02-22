import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_brave_browser_platform_interface.dart';

/// An implementation of [FlutterBraveBrowserPlatform] that uses method channels.
class MethodChannelFlutterBraveBrowser extends FlutterBraveBrowserPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_brave_browser');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> openBrave(String url) async {
    await methodChannel.invokeMethod('openBraveCustomTab', {"url": url});
  }
}
