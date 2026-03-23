package marchvalidation.dom.converters;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.CustomReferenceConverter;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.ResolvingConverter;
import marchvalidation.dom.Dimension;
import marchvalidation.dom.MarchConfigRoot;
import marchvalidation.dom.Partition;
import marchvalidation.dom.util.MarchConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import com.intellij.psi.ElementManipulators;

public class PartitionConverter extends ResolvingConverter<Partition> implements CustomReferenceConverter<Partition> {

    @Override
    public @Nullable Partition fromString(final @Nullable String s, final ConvertContext context) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).partitions.get(s);
    }

    @Override
    public String toString(final @Nullable Partition partition, final ConvertContext context) {
        return partition != null ? partition.getName().getStringValue() : null;
    }

    @Override
    public @NotNull Collection<? extends Partition> getVariants(final ConvertContext context) {
        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).partitions.values();
    }

    @Override
    public PsiReference @NotNull [] createReferences(final GenericDomValue<Partition> value, final PsiElement element, final ConvertContext context) {
        final var text = value.getStringValue();
        if (text == null || text.isEmpty()) {
            return PsiReference.EMPTY_ARRAY;
        }

        final var references = new ArrayList<PsiReference>();
        final var valueStartOffset = ElementManipulators.getValueTextRange(element).getStartOffset();

        final var parts = text.split(";");
        var currentPos = 0;

        for (final var part : parts) {
            final var partIndex = text.indexOf(part, currentPos);
            if (partIndex != -1) {
                final var range = new TextRange(valueStartOffset + partIndex, valueStartOffset + partIndex + part.length());
                references.add(new PartitionReference(element, range, part, context));
                currentPos = partIndex + part.length();
            }
        }

        return references.toArray(new PsiReference[0]);
    }
}