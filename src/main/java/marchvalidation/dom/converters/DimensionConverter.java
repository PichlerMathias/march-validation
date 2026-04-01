package marchvalidation.dom.converters;

import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.xml.CustomReferenceConverter;
import com.intellij.util.xml.GenericDomValue;
import marchvalidation.dom.Dimension;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.ResolvingConverter;
import marchvalidation.dom.util.MarchConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class DimensionConverter extends ResolvingConverter<Dimension> implements CustomReferenceConverter<Dimension> {

    @Override
    public @Nullable Dimension fromString(final @Nullable String s, final ConvertContext context) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).getDimensions().get(s);
    }


    @Override
    public String getErrorMessage(@Nullable String s, ConvertContext context) {
        return "Dimension '" + s + "' is not defined in global dimensions";
    }

    @Override
    public @NotNull Collection<? extends Dimension> getVariants(final ConvertContext context) {
        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).getDimensions().values();
    }

    @Override
    public String toString(final @Nullable Dimension dimension, final ConvertContext context) {
        return dimension != null ? dimension.getName().getValue() : null;
    }

    @Override
    public PsiReference @NotNull [] createReferences(final GenericDomValue<Dimension> value, final PsiElement element, final ConvertContext context) {
        final var s = value.getStringValue();
        if (s == null || s.isEmpty()) {
            return PsiReference.EMPTY_ARRAY;
        }

        final var range = ElementManipulators.getValueTextRange(element);
        return new PsiReference[] {new DimensionReference(element, range, s, context)};
    }
}