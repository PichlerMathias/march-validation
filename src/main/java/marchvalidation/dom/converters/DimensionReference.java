package marchvalidation.dom.converters;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.xml.ConvertContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DimensionReference extends PsiReferenceBase<PsiElement> {
    private final String dimensionName;
    private final ConvertContext context;

    public DimensionReference(@NotNull PsiElement element, TextRange range, String dimensionName, ConvertContext context) {
        super(element, range);
        this.dimensionName = dimensionName != null ? dimensionName.trim() : "";
        this.context = context;
    }

    @Override
    public @Nullable PsiElement resolve() {
        final var converter = new DimensionConverter();
        final var dimension = converter.fromString(dimensionName, context);
        return dimension != null ? dimension.getXmlElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        final var converter = new DimensionConverter();
        return converter.getVariants(context).stream()
                .map(d -> d.getName().getStringValue())
                .filter(name -> name != null && !name.isEmpty())
                .toArray();
    }
}