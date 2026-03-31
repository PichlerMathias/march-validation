package marchvalidation.rules;

import com.intellij.lang.Language;

public class ArchRuleLanguage extends Language {
    // Die Instanz, die überall im Plugin (z.B. in der ParserDefinition) verwendet wird
    public static final ArchRuleLanguage INSTANCE = new ArchRuleLanguage();

    private ArchRuleLanguage() {
        // Die ID muss mit der ID in deiner plugin.xml übereinstimmen
        super("ArchRule");
    }
}