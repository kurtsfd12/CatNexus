package com.kristianskokars.catnexus.feature.settings.presentation

sealed interface SettingsEvent {
    data object ToggleSwipeDirection : SettingsEvent
    data object ToggleDownloadNotificationsShowing : SettingsEvent
    data object ToggleCarMode : SettingsEvent
    data object TogglePictureDoubleTapFunctionality: SettingsEvent
    data object ResetToDefaultSettings : SettingsEvent
}
