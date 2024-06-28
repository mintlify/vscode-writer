package com.mintlify.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.mintlify.settings.ApplicationSettingsState.Companion.instance
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ApplicationSettingsPanel : Configurable {

    private val panel = JPanel()
    private val options = arrayOf("English", "Chinese", "French", "Korean", "Russian", "Spanish", "Turkish")
    private val myComboBox = ComboBox(options)
    override fun createComponent(): JComponent {
        val settings = instance
        myComboBox.selectedItem = settings.language
        val label = JLabel("Default Language:")
        panel.add(label)
        panel.add(myComboBox)
        return panel
    }

    override fun isModified(): Boolean {
        val settings = instance
        return myComboBox.selectedItem != settings.language
    }

    override fun apply() {
        val settings = instance
        settings.language = myComboBox.selectedItem as String
    }

    override fun getDisplayName(): String {
        return "Mintlify Settings"
    }
}
