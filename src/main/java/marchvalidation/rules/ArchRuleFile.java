package marchvalidation.rules;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class ArchRuleFile extends PsiFileBase {
    public ArchRuleFile(@NotNull FileViewProvider viewProvider) {
        // Verbindet die Datei mit deiner ArchRuleLanguage
        super(viewProvider, ArchRuleLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        // Hier musst du ArchRuleFileType.INSTANCE zurückgeben
        return ArchRuleFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "ArchRule File";
    }
}