package marchvalidation.dom.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
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

        final var valueElement = attribute.getValueElement();
        final var partitionValue = attribute.getValue();
        if (valueElement == null || partitionValue == null || partitionValue.isEmpty()) {
            return;
        }

        final var domElement = DomUtil.getDomElement(attribute.getParent());
        if (domElement == null) {
            return;
        }

        final var parentDimension = getDimensionFromParent(domElement);

        if (parentDimension != null) {
            final var parts = partitionValue.split(";");

            for (final var p : parts) {
                final var trimmed = p.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                final var exists = parentDimension.getPartitionsElement().getPartitions().stream()
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
        final var parent = DomUtil.getParentOfType(element, DomElement.class, true);

        if (parent instanceof Modularity mod) {
            return mod.getDimension().getValue();
        } else if (parent instanceof PackageModularity pkg) {
            return pkg.getDimension().getValue();
        }

        return null;
    }
}