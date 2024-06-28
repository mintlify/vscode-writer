package com.mintlify.document.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.actionSystem.ActionPlaces;

import com.intellij.openapi.wm.ToolWindow;
import com.mintlify.settings.ApplicationSettingsState;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static java.lang.System.out;

public class DocsWindow {

  public static final String URL = "https://discord.gg/6W7GuYuxra";
  private JPanel myToolWindowContent;
  private JComboBox<String> docFormatSelector;
  private JButton generateDocsButton;
  private JButton joinCommunityLabel;
  private JComboBox<String> languageSelector;

  public DocsWindow(ToolWindow toolWindow) {
    fillDocFormatSelector();

    generateDocsButton.addActionListener(e -> {
      ActionManager actionManager = ActionManager.getInstance();
      AnAction action = actionManager.getAction("org.intellij.sdk.action.PopupDialogAction");
      ActionUtil.invokeAction(action, toolWindow.getComponent(), ActionPlaces.TOOLWINDOW_CONTENT, null, null);
    });
    fillLangSelector();
    try {
      final URI joinDiscordUri = new URI(URL);
      joinCommunityLabel.setBorderPainted(false);
      joinCommunityLabel.setOpaque(false);
      joinCommunityLabel.addActionListener(e -> {
        if (Desktop.isDesktopSupported()) {
          try {
            Desktop.getDesktop().browse(joinDiscordUri);
          } catch (IOException err) {
            /* TODO: error handling */
            out.println("Error when open the URL" + err);
          }
        } else {
          /* TODO: error handling */
          out.println("Desktop is not supported");
        }
      });
    } catch (URISyntaxException err) {
      /* TODO: error handling */
      out.println("Error when create an URL" + err);
    }
  }

  private void fillLangSelector() {
    final ApplicationSettingsState instance = ApplicationSettingsState.Companion.getInstance();
    String[] supportedLanguages = {"English", "Chinese", "French", "Korean", "Russian", "Spanish", "Turkish"};
    Arrays.stream(supportedLanguages)
                    .forEach(language -> languageSelector.addItem(language));
    languageSelector.setSelectedItem(instance.getLanguage());
    languageSelector.setEditable(false);
  }

  private void fillDocFormatSelector() {
    String[] supportedDocFormats = {"Auto-detect", "Javadoc", "Google", "JSDoc", "reST", "NumPy", "DocBlock", "Doxygen", "XML", "GoDoc", "RustDoc"};
    Arrays.stream(supportedDocFormats)
                    .forEach(docFormat -> docFormatSelector.addItem(docFormat));
    docFormatSelector.setEditable(false);
  }

  public String getSelectedDocFormat() {
    return (String) docFormatSelector.getSelectedItem();
  }

  public String getSelectedLanguage() {
    return (String) languageSelector.getSelectedItem();
  }

  public JPanel getContent() {
    return myToolWindowContent;
  }
}
