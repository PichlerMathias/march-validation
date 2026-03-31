package marchvalidation.rules;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ArchRuleElementType extends IElementType {
    public ArchRuleElementType(@NotNull @NonNls String debugName) {
        super(debugName, ArchRuleLanguage.INSTANCE);
    }
}