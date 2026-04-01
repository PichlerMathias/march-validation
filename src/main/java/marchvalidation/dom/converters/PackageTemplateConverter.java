package marchvalidation.dom.converters;

import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomUtil;
import com.intellij.util.xml.ResolvingConverter;
import marchvalidation.dom.MarchConfigRoot;
import marchvalidation.dom.PackageTemplate;
import marchvalidation.dom.util.MarchConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class PackageTemplateConverter extends ResolvingConverter<PackageTemplate> {

    @Override
    public @Nullable PackageTemplate fromString(final @Nullable String s, final ConvertContext context) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }

        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).getTemplates().get(s);
    }

    @Override
    public @NotNull Collection<? extends PackageTemplate> getVariants(final ConvertContext context) {
        return MarchConfigUtil.getCache(MarchConfigUtil.getRoot(context)).getTemplates().values();
    }

    @Override
    public String toString(final @Nullable PackageTemplate template, final ConvertContext context) {
        return (template != null && template.getName() != null) ? template.getName().getStringValue() : null;
    }
}