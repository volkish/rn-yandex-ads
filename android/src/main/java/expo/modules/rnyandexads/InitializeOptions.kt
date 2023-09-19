package expo.modules.rnyandexads

import expo.modules.kotlin.records.Record


class InitializeOptions: Record {
    val userConsent: Boolean = false
    val locationConsent: Boolean = false
    val enableLogging: Boolean = false
    val enableDebugErrorIndicator: Boolean = false
}
