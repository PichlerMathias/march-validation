package marchvalidation.rules;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ArchRuleTokenType extends IElementType {
    public ArchRuleTokenType(@NotNull @NonNls String debugName) {
        super(debugName, ArchRuleLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "ArchRuleTokenType." + super.toString();
    }
}