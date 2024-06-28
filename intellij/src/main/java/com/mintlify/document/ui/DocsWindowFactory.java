package com.mintlify.document.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DocsWindowFactory implements ToolWindowFactory {
  public static final String ID = "Mintlify Doc Writer";
  private static final Key<DocsWindow> MY_TOOL_WINDOW = Key.create("DocsWindow");

  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    DocsWindow docsWindow = new DocsWindow(toolWindow);
    ContentFactory contentFactory = ContentFactory.getInstance();
    Content content = contentFactory.createContent(docsWindow.getContent(), null, false);
    content.putUserData(MY_TOOL_WINDOW, docsWindow);
    toolWindow.getContentManager().addContent(content);
  }

  @Nullable
  public static DocsWindow getWindow(Project project) {
    ToolWindowManager instance = ToolWindowManager.getInstance(project);
    ToolWindow toolWindow = instance.getToolWindow(DocsWindowFactory.ID);
    if (toolWindow != null) {
      if (!toolWindow.isShowStripeButton()) {
        toolWindow.show();
      }
      Content[] contents = toolWindow.getContentManager().getContents();
      if (contents.length > 0) {
        Content content = contents[0];
        return content.getUserData(MY_TOOL_WINDOW);
      }
    }
    return null;
  }

}
