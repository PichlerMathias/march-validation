package marchvalidation.dom.converters;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.GenericAttributeValue;
import marchvalidation.dom.Modularity;
import marchvalidation.dom.util.MarchConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VariableReference extends PsiReferenceBase<PsiElement> {
    private final String variableName;
    private final ConvertContext context;

    public VariableReference(final @NotNull PsiElement element, final TextRange range, final String variableName, final ConvertContext context) {
        super(element, range);
        this.variableName = variableName;
        this.context = context;
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @Override
    public @Nullable PsiElement resolve() {

        final var root = MarchConfigUtil.getRoot(context);
        final var globalMatch = MarchConfigUtil.getCache(root).getVariables().get(variableName);
        if (globalMatch != null) {
            return globalMatch;
        }

        final var currentDom = DomUtil.getDomElement(getElement());
        if (currentDom == null) {
            return null;
        }

        var modularity = DomUtil.getParentOfType(currentDom, Modularity.class, false);
        while (modularity != null) {
            final var attr = getAttributeByName(modularity, variableName);

            if (attr != null && DomUtil.hasXml(attr)) {
                final var value = attr.getStringValue();
                if (value != null && !value.contains("${" + variableName + "}")) {
                    return attr.getXmlElement();
                }
            }
            modularity = DomUtil.getParentOfType(modularity, Modularity.class, true);
        }

        return null;
    }

    private @Nullable GenericAttributeValue<String> getAttributeByName(final Modularity modularity, final String name) {
        return switch (name) {
            case "groupId" -> modularity.getGroupId();
            case "artifactId" -> modularity.getArtifactId();
            case "rootPackage" -> modularity.getRootPackage();
            default -> null;
        };
    }

    @Override
    public Object @NotNull [] getVariants() {
        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).getVariables().keySet().toArray();
    }
}