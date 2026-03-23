package marchvalidation.dom.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import marchvalidation.dom.Dimension;
import marchvalidation.dom.Modularity;
import marchvalidation.dom.PackageModularity;
import org.jetbrains.annotations.NotNull;

public class PartitionMatchAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof XmlAttribute attribute) || !"partition".equals(attribute.getName())) {
            return;
        }

        XmlAttributeValue valueElement = attribute.getValueElement();
        String partitionValue = attribute.getValue();
        if (valueElement == null || partitionValue == null || partitionValue.isEmpty()) {
            return;
        }

        DomElement domElement = DomUtil.getDomElement(attribute.getParent());
        if (domElement == null) return;

        Dimension parentDimension = getDimensionFromParent(domElement);

        if (parentDimension != null) {
            String[] parts = partitionValue.split(";");

            for (String p : parts) {
                String trimmed = p.trim();
                if (trimmed.isEmpty()) continue;

                boolean exists = parentDimension.getPartitionsElement().getPartitions().stream()
                        .anyMatch(def -> trimmed.equals(def.getName().getStringValue()));

                if (!exists) {
                    holder.newAnnotation(HighlightSeverity.ERROR,
                                    "Partition '" + trimmed + "' is not a valid partition of the parent dimension '"
                                            + parentDimension.getName().getStringValue() + "'")
                            .range(valueElement.getTextRange())
                            .create();
                }
            }
        }
    }

    private Dimension getDimensionFromParent(DomElement element) {
        DomElement parent = DomUtil.getParentOfType(element, DomElement.class, true);

        if (parent instanceof Modularity mod) {
            return mod.getDimension().getValue();
        } else if (parent instanceof PackageModularity pkg) {
            return pkg.getDimension().getValue();
        }

        return null;
    }
}