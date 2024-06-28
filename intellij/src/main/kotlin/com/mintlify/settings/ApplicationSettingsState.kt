package com.mintlify.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "MintlifySettings", storages = [Storage("mintlify.xml")])
class ApplicationSettingsState : PersistentStateComponent<ApplicationSettingsState?> {
    var language = "English"
    override fun getState(): ApplicationSettingsState? {
        return this
    }

    override fun loadState(state: ApplicationSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: ApplicationSettingsState
            get() = ApplicationManager.getApplication().getService(
                ApplicationSettingsState::class.java
            )
    }
}
