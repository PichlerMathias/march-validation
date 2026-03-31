package marchvalidation.rules;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import marchvalidation.parser.ArchRuleParser;
import marchvalidation.psi.ArchRuleTypes;
import org.jetbrains.annotations.NotNull;

public class RuleParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(ArchRuleLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        // Hier wird dein generierter JFlex-Lexer eingebunden
        return new FlexAdapter(new marchvalidation.rules._ArchRuleLexer());
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new ArchRuleParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return ArchRuleTypes.Factory.createElement(node);
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        // Du musst noch eine ArchRuleFile Klasse erstellen (siehe unten)
        return new ArchRuleFile(viewProvider);
    }
}