import 'package:flutter_brave_browser/flutter_brave_browser.dart';
import 'package:flutter_brave_browser/flutter_brave_browser_method_channel.dart' as method_channel;
import 'package:flutter_brave_browser/flutter_brave_browser_platform_interface.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterBraveBrowserPlatform with MockPlatformInterfaceMixin implements FlutterBraveBrowserPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<void> openBrave(String url) {
    // TODO: implement openBrave
    throw UnimplementedError();
  }
}

void main() {
  final FlutterBraveBrowserPlatform initialPlatform = FlutterBraveBrowserPlatform.instance;
  final braveBrowser = method_channel.MethodChannelFlutterBraveBrowser();

  test('$braveBrowser is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterBraveBrowser>());
  });

  test('getPlatformVersion', () async {
    FlutterBraveBrowser flutterBraveBrowserPlugin = FlutterBraveBrowser();
    MockFlutterBraveBrowserPlatform fakePlatform = MockFlutterBraveBrowserPlatform();
    FlutterBraveBrowserPlatform.instance = fakePlatform;

    expect(await flutterBraveBrowserPlugin.getPlatformVersion(), '42');
  });
}
