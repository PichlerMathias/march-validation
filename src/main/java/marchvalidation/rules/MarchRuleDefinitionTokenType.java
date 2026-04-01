package marchvalidation.rules;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class MarchRuleDefinitionTokenType extends IElementType {
    public MarchRuleDefinitionTokenType(@NotNull @NonNls String debugName) {
        super(debugName, MarchRuleDefinitionLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "ArchRuleTokenType." + super.toString();
    }
}