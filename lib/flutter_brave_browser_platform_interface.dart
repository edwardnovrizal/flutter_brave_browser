import 'package:flutter/services.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class FlutterBraveBrowserPlatform extends PlatformInterface {
  /// Constructs a FlutterBraveBrowserPlatform.
  FlutterBraveBrowserPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterBraveBrowserPlatform _instance = MethodChannelFlutterBraveBrowser();

  /// The default instance of [FlutterBraveBrowserPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterBraveBrowser].
  static FlutterBraveBrowserPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterBraveBrowserPlatform] when
  /// they register themselves.
  static set instance(FlutterBraveBrowserPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> openBrave(String url);
}

class MethodChannelFlutterBraveBrowser extends FlutterBraveBrowserPlatform {
  final MethodChannel _channel = const MethodChannel('flutter_brave_browser');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await _channel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> openBrave(String url) async {
    await _channel.invokeMethod('openBraveCustomTab', {"url": url});
  }
}
