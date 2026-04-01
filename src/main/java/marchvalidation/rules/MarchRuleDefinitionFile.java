package marchvalidation.rules;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class MarchRuleDefinitionFile extends PsiFileBase {
    public MarchRuleDefinitionFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, MarchRuleDefinitionLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return MarchRuleDefinitionFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "ArchRule File";
    }
}