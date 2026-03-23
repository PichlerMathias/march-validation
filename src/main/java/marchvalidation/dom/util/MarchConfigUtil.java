package marchvalidation.dom.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
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
        public final Map<String, PsiElement> variables = new HashMap<>();
        public final Map<String, Dimension> dimensions = new HashMap<>();
        public final Map<String, PackageTemplate> templates = new HashMap<>();
        public final Map<String, Partition> partitions = new HashMap<>();
    }

    public static ConfigCache getCache(MarchConfigRoot root) {
        return CachedValuesManager.getCachedValue(root.getXmlElement(), () -> {
            ConfigCache cache = new ConfigCache();

            // 1. Index Dimensions & Variable Map
            for (var d : root.getDimensionsWrapper().getDimensionList()) {
                String name = d.getName().getStringValue();
                if (name != null) {
                    cache.dimensions.put(name, d);
                    cache.variables.put(name, d.getXmlElement());

                    // 2. Index Partitions (linked to Dimensions)
                    if (d.getPartitionsElement() != null) {
                        for (var p : d.getPartitionsElement().getPartitions()) {
                            String pName = p.getName().getStringValue();
                            if (pName != null) cache.partitions.put(pName, p);
                        }
                    }
                }
            }

            // 3. Index Package Templates
            if (root.getPackageTemplates() != null) {
                for (var t : root.getPackageTemplates().getPackageTemplates()) {
                    String tName = t.getName().getStringValue();
                    if (tName != null) cache.templates.put(tName, t);
                }
            }

            return CachedValueProvider.Result.create(cache, root.getXmlElement());
        });
    }
}