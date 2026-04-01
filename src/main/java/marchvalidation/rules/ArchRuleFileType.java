package marchvalidation.rules;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public final class ArchRuleFileType extends LanguageFileType {
    public static final ArchRuleFileType INSTANCE = new ArchRuleFileType();

    private ArchRuleFileType() {
        super(ArchRuleLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "ArchRule";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "ArchRule language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "arch";
    }

    @Override
    public Icon getIcon() {
        return null;
    }
}