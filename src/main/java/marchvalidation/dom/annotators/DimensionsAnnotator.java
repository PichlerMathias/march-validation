package marchvalidation.dom.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class DimensionsAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof XmlTag parentTag) || !"dimensions".equals(parentTag.getName())) {
            return;
        }

        XmlTag[] dimensionTags = parentTag.findSubTags("dimension");
        Set<String> seenNames = new HashSet<>();

        for (XmlTag dimension : dimensionTags) {
            XmlTag nameTag = dimension.findFirstSubTag("name");
            if (nameTag == null) continue;

            String nameValue = nameTag.getValue().getTrimmedText();

            // Check if the dimension name is empty
            if (nameValue.isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Dimension name cannot be empty")
                        .range(nameTag.getTextRange())
                        .create();
                continue;
            }

            // Check for duplicate dimension names
            if (seenNames.contains(nameValue)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Duplicate dimension: " + nameValue)
                        .range(nameTag.getTextRange())
                        .create();
            } else {
                seenNames.add(nameValue);
            }
        }
    }
}