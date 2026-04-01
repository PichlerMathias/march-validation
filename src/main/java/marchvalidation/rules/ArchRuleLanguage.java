package marchvalidation.rules;

import com.intellij.lang.Language;

public final class ArchRuleLanguage extends Language {
    public static final ArchRuleLanguage INSTANCE = new ArchRuleLanguage();

    private ArchRuleLanguage() {
        super("ArchRule");
    }
}