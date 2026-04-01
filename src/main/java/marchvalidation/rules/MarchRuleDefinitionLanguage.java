package marchvalidation.rules;

import com.intellij.lang.Language;

public final class MarchRuleDefinitionLanguage extends Language {
    public static final MarchRuleDefinitionLanguage INSTANCE = new MarchRuleDefinitionLanguage();

    private MarchRuleDefinitionLanguage() {
        super("ArchRule");
    }
}