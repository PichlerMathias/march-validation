package marchvalidation.dom.converters;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomElement;
import com.jetbrains.cef.remote.thrift.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class PartitionReference extends PsiReferenceBase<PsiElement> {
    private final String partName;
    private final ConvertContext context;

    public PartitionReference(final @NotNull PsiElement element, final TextRange range, final String partName, final ConvertContext context) {
        super(element, range);
        this.partName = partName.trim();
        this.context = context;
    }

    @Override
    public @Nullable PsiElement resolve() {
        final var converter = new PartitionConverter();
        final var variants = converter.getVariants(context);

        return variants.stream()
                .filter(p -> partName.equals(p.getName().getStringValue()))
                .map(DomElement::getXmlElement)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Object @NotNull [] getVariants() {
        final var converter = new PartitionConverter();
        return converter.getVariants(context).stream()
                .map(p -> p.getName().getStringValue())
                .filter(name -> name != null && !name.isEmpty())
                .toArray();
    }
}