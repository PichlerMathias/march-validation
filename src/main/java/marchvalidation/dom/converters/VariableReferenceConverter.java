package marchvalidation.dom.converters;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.Converter;
import com.intellij.util.xml.CustomReferenceConverter;
import com.intellij.util.xml.GenericDomValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class VariableReferenceConverter extends Converter<String> implements CustomReferenceConverter<String> {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

    @Override
    public @Nullable String fromString(final @Nullable String s, final ConvertContext context) {
        return s;
    }

    @Override
    public @Nullable String toString(final @Nullable String s, final ConvertContext context) {
        return s;
    }

    @Override
    public PsiReference @NotNull [] createReferences(final GenericDomValue<String> value, final PsiElement element, final ConvertContext context) {
        final var text = value.getStringValue();
        if (text == null || text.isEmpty()) {
            return PsiReference.EMPTY_ARRAY;
        }

        final var references = new ArrayList<PsiReference>();
        final var valueRange = ElementManipulators.getValueTextRange(element);
        final var valueStartOffset = valueRange.getStartOffset();

        final var matcher = VARIABLE_PATTERN.matcher(text);
        while (matcher.find()) {
            final var varName = matcher.group(1);
            final var start = matcher.start(1);
            final var end = matcher.end(1);
            final var range = new TextRange(valueStartOffset + start, valueStartOffset + end);
            references.add(new VariableReference(element, range, varName, context));
        }

        return references.toArray(new PsiReference[0]);
    }
}