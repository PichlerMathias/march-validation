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
import marchvalidation.parser.MarchRuleDefinitionParser;
import marchvalidation.psi.MarchRuleDefinitionTypes;
import org.jetbrains.annotations.NotNull;

public class RuleParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(MarchRuleDefinitionLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new FlexAdapter(new marchvalidation.rules._MarchRuleDefinitionLexer());
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new MarchRuleDefinitionParser();
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
        return MarchRuleDefinitionTypes.Factory.createElement(node);
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new MarchRuleDefinitionFile(viewProvider);
    }
}