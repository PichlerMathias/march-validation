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

public class DimensionAncestryAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof XmlAttribute attribute) || !"dimension".equals(attribute.getName())) {
            return;
        }

        final var valueElement = attribute.getValueElement();
        final var dimensionValue = attribute.getValue();
        if (valueElement == null || dimensionValue == null || dimensionValue.isEmpty()) {
            return;
        }

        final var domElement = DomUtil.getDomElement(attribute.getParent());
        if (domElement == null) {
            return;
        }

        if (hasDuplicateInAncestry(domElement, dimensionValue)) {
            holder.newAnnotation(HighlightSeverity.ERROR,
                            "Dimension '" + dimensionValue + "' is already defined in the linear ancestry.")
                    .range(valueElement.getTextRange())
                    .create();
        }
    }

    private boolean hasDuplicateInAncestry(DomElement current, String name) {
        var parent = current.getParent();

        while (parent != null) {
            final var parentDimName = getParentDimName(parent);

            if (name.equals(parentDimName)) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    private String getParentDimName(DomElement parent) {
        if (parent instanceof Modularity mod) {
            return getDimName(mod.getDimension());
        }
        if (parent instanceof PackageModularity pkg) {
            return getDimName(pkg.getDimension());
        }
        return null;
    }

    private String getDimName(com.intellij.util.xml.GenericDomValue<Dimension> dim) {
        return (dim != null && dim.getValue() != null)
                ? dim.getValue().getName().getStringValue()
                : null;
    }
}