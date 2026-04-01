package marchvalidation.rules;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class MarchRuleDefinitionElementType extends IElementType {
    public MarchRuleDefinitionElementType(@NotNull @NonNls String debugName) {
        super(debugName, MarchRuleDefinitionLanguage.INSTANCE);
    }
}