package com.mintlify.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Supports storing the application settings in a persistent way.
 * The [com.intellij.openapi.components.State] and [Storage] annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@com.intellij.openapi.components.State(
    name = "org.intellij.sdk.settings.AppSettingsState",
    storages = [Storage("SdkSettingsPlugin.xml")]
)
class State : PersistentStateComponent<State?> {
    var userId = "John Q. Public"
    var ideaStatus = false
    override fun getState(): State? {
        return this
    }

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: State
            get() = ApplicationManager.getApplication().getService(
                State::class.java
            )
    }
}
