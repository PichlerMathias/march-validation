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

public class DimensionAncestryAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof XmlAttribute attribute) || !"dimension".equals(attribute.getName())) {
            return;
        }

        XmlAttributeValue valueElement = attribute.getValueElement();
        String dimensionValue = attribute.getValue();
        if (valueElement == null || dimensionValue == null || dimensionValue.isEmpty()) {
            return;
        }

        DomElement domElement = DomUtil.getDomElement(attribute.getParent());
        if (domElement == null) return;

        if (hasDuplicateInAncestry(domElement, dimensionValue)) {
            holder.newAnnotation(HighlightSeverity.ERROR,
                            "Dimension '" + dimensionValue + "' is already defined in the linear ancestry.")
                    .range(valueElement.getTextRange())
                    .create();
        }
    }

    private boolean hasDuplicateInAncestry(DomElement current, String name) {
        DomElement parent = current.getParent();

        while (parent != null) {
            String parentDimName = null;

            if (parent instanceof Modularity mod) {
                parentDimName = getDimName(mod.getDimension());
            } else if (parent instanceof PackageModularity pkg) {
                parentDimName = getDimName(pkg.getDimension());
            }

            if (name.equals(parentDimName)) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    private String getDimName(com.intellij.util.xml.GenericDomValue<Dimension> dim) {
        return (dim != null && dim.getValue() != null)
                ? dim.getValue().getName().getStringValue()
                : null;
    }
}