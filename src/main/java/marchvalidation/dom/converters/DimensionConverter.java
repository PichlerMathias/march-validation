package marchvalidation.dom.converters;

import marchvalidation.dom.Dimension;
import marchvalidation.dom.MarchConfigRoot;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.ResolvingConverter;
import marchvalidation.dom.util.MarchConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class DimensionConverter extends ResolvingConverter<Dimension> {

    @Override
    public @Nullable Dimension fromString(final @Nullable String s, final ConvertContext context) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).dimensions.get(s);
    }

    @Override
    public @NotNull Collection<? extends Dimension> getVariants(final ConvertContext context) {
        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).dimensions.values();
    }

    @Override
    public String toString(final @Nullable Dimension dimension, final ConvertContext context) {
        return dimension != null ? dimension.getName().getValue() : null;
    }
}