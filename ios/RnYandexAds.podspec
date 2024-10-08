require 'json'

package = JSON.parse(File.read(File.join(__dir__, '..', 'package.json')))

Pod::Spec.new do |s|
  s.name           = 'RnYandexAds'
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.license        = package['license']
  s.author         = package['author']
  s.homepage       = package['homepage']
  s.platform       = :ios, '13.0'
  s.swift_version  = '5.4'
  s.source         = { git: 'https://github.com/volkish/rn-yandex-ads' }
  s.static_framework = true

  s.dependency 'ExpoModulesCore'
  s.dependency 'YandexMobileAds', '7.5.1'
  s.dependency 'YandexMobileAdsInstream', '0.38.0'
  s.dependency 'MyTargetYandexMobileAdsAdapters', '5.21.7.2'

  # Swift/Objective-C compatibility
  s.pod_target_xcconfig = {
    'DEFINES_MODULE' => 'YES',
    'SWIFT_COMPILATION_MODE' => 'wholemodule'
  }

  s.source_files = "**/*.{h,m,swift}"
end
