package marchvalidation.dom.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomUtil;
import marchvalidation.dom.Dimension;
import marchvalidation.dom.MarchConfigRoot;
import marchvalidation.dom.PackageTemplate;
import marchvalidation.dom.Partition;

import java.util.HashMap;
import java.util.Map;

public class MarchConfigUtil {
    public static MarchConfigRoot getRoot(final ConvertContext context) {
        return (MarchConfigRoot) DomUtil.getFileElement(context.getInvocationElement()).getRootElement();
    }

    public static class ConfigCache {
        private final Map<String, PsiElement> variables = new HashMap<>();
        private final Map<String, Dimension> dimensions = new HashMap<>();
        private final Map<String, PackageTemplate> templates = new HashMap<>();
        private final Map<String, Partition> partitions = new HashMap<>();

        public Map<String, PsiElement> getVariables() {
            return variables;
        }

        public Map<String, Dimension> getDimensions() {
            return dimensions;
        }

        public Map<String, PackageTemplate> getTemplates() {
            return templates;
        }

        public Map<String, Partition> getPartitions() {
            return partitions;
        }
    }

    public static ConfigCache getCache(MarchConfigRoot root) {
        return CachedValuesManager.getCachedValue(root.getXmlElement(), () -> {
            final var cache = new ConfigCache();

            for (var d : root.getDimensionsWrapper().getDimensionList()) {
                final var name = d.getName().getStringValue();
                if (name != null) {
                    cache.dimensions.put(name, d);
                    cache.variables.put(name, d.getXmlElement());

                    if (d.getPartitionsElement() != null) {
                        for (var p : d.getPartitionsElement().getPartitions()) {
                            final var pName = p.getName().getStringValue();
                            if (pName != null) {
                                cache.partitions.put(pName, p);
                            }
                        }
                    }
                }
            }

            if (root.getPackageTemplates() != null) {
                for (var t : root.getPackageTemplates().getPackageTemplates()) {
                    final var tName = t.getName().getStringValue();
                    if (tName != null) {
                        cache.templates.put(tName, t);
                    }
                }
            }

            return CachedValueProvider.Result.create(cache, root.getXmlElement());
        });
    }

    public static MarchConfigRoot getRoot(PsiElement element) {
        final var file = element.getContainingFile();
        final var hostFile = com.intellij.lang.injection.InjectedLanguageManager.getInstance(element.getProject()).getTopLevelFile(file);
        if (hostFile instanceof XmlFile xmlFile) {
            final var fileElement = com.intellij.util.xml.DomManager.getDomManager(element.getProject())
                    .getFileElement(xmlFile, MarchConfigRoot.class);
            return fileElement != null ? fileElement.getRootElement() : null;
        }
        return null;
    }
}