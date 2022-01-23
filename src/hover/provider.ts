import axios from 'axios';
import { HoverProvider, Hover, window, env, ProviderResult, MarkdownString, Uri, TextEditor } from 'vscode';
import { getHighlightedText } from '../helpers/utils';
import { DOCS_PREVIEW } from '../helpers/api';

const formatHoverContent = (content: string) => {
  return content.replace(/\n/g, '\n\n');
};

export default class LanguagesHoverProvider implements HoverProvider {
  provideHover(): ProviderResult<Hover> {
    return new Promise(async resolve => {
      const editor = window.activeTextEditor;
      if (editor == null) {return resolve(null);}
      
      const { highlighted } = getHighlightedText(editor);
      if (!highlighted) {return resolve(null);}

      const { data: { preview, position, docstring } } = await axios.post(DOCS_PREVIEW,
        {
          code: highlighted,
          languageId: editor.document.languageId,
          commented: true,
          userId: env.machineId,
          docStyle: 'Auto-detect',
          source: 'vscode',
          context: editor.document.getText(),
        });

        const insertCommentsArgs = [{ position, content: docstring }];
        const insertCommandUri = Uri.parse(
          `command:docs.insert?${encodeURIComponent(JSON.stringify(insertCommentsArgs))}`
        );

      const markdownDocstring = new MarkdownString(
        `<img src="https://res.cloudinary.com/mintlify/image/upload/v1642834133/docwriter-smol_hg8sxd.png" height="28" /> *Generated by AI Doc Writer* <div>${formatHoverContent(preview)}</div>`
      );
      markdownDocstring.supportHtml = true;
      const footer = new MarkdownString(`[$(pencil) Add as comments (⌘ + .)](${insertCommandUri})`, true);
      footer.isTrusted = true;
      resolve(new Hover([markdownDocstring, footer]));
    });
  }
}