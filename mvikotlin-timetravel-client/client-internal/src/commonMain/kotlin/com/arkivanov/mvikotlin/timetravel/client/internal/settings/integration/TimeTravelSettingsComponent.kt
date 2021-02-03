package com.arkivanov.mvikotlin.timetravel.client.internal.settings.integration

import androidx.compose.runtime.State
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.timetravel.client.internal.settings.SettingsConfig
import com.arkivanov.mvikotlin.timetravel.client.internal.settings.TimeTravelSettings
import com.arkivanov.mvikotlin.timetravel.client.internal.settings.integration.mappers.stateToModel
import com.arkivanov.mvikotlin.timetravel.client.internal.settings.store.TimeTravelSettingsStore.Intent
import com.arkivanov.mvikotlin.timetravel.client.internal.settings.store.TimeTravelSettingsStoreFactory
import com.arkivanov.mvikotlin.timetravel.client.internal.utils.subscribeAsState
import com.russhwolf.settings.Settings

internal class TimeTravelSettingsComponent(
    lifecycle: Lifecycle,
    storeFactory: StoreFactory,
    settingsFactory: Settings.Factory,
    settingsConfig: SettingsConfig
) : TimeTravelSettings {

    private val store =
        TimeTravelSettingsStoreFactory(
            storeFactory = storeFactory,
            settings = TimeTravelSettingsStoreSettings(
                settingsFactory = settingsFactory,
                defaults = settingsConfig.defaults
            ),
            editingConfig = settingsConfig.editing
        ).create()

    override val models: State<TimeTravelSettings.Model> = store.subscribeAsState(lifecycle, stateToModel)

    override fun onEditClicked() {
        store.accept(Intent.StartEdit)
    }

    override fun onSaveClicked() {
        store.accept(Intent.SaveEdit)
    }

    override fun onCancelClicked() {
        store.accept(Intent.CancelEdit)
    }

    override fun onHostChanged(host: String) {
        store.accept(Intent.SetHost(host = host))
    }

    override fun onPortChanged(port: String) {
        store.accept(Intent.SetPort(port = port))
    }

    override fun onConnectViaAdbChanged(connectViaAdb: Boolean) {
        store.accept(Intent.SetConnectViaAdb(connectViaAdb = connectViaAdb))
    }

    override fun onWrapEventDetailsChanged(wrapEventDetails: Boolean) {
        store.accept(Intent.SetWrapEventDetails(wrapEventDetails = wrapEventDetails))
    }

    override fun onDarkModeChanged(isDarkMode: Boolean) {
        store.accept(Intent.SetDarkMode(isDarkMode = isDarkMode))
    }
}
