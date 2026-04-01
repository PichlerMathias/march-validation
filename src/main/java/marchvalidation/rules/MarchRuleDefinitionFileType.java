package marchvalidation.rules;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public final class MarchRuleDefinitionFileType extends LanguageFileType {
    public static final MarchRuleDefinitionFileType INSTANCE = new MarchRuleDefinitionFileType();

    private MarchRuleDefinitionFileType() {
        super(MarchRuleDefinitionLanguage.INSTANCE);
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