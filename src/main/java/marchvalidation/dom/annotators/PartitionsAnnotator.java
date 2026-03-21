package marchvalidation.dom.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class PartitionsAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof XmlTag parentTag) || !"partitions".equals(parentTag.getName())) {
            return;
        }

        XmlTag[] partitionTags = parentTag.findSubTags("partition");
        Set<String> seenNames = new HashSet<>();

        for (XmlTag partition : partitionTags) {
            XmlTag nameTag = partition.findFirstSubTag("name");
            if (nameTag == null) continue;

            String nameValue = nameTag.getValue().getTrimmedText();

            // Check if the name tag is empty
            if (nameValue.isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Partition name cannot be empty")
                        .range(nameTag.getTextRange())
                        .create();
                continue;
            }

            // Check for duplicates
            if (seenNames.contains(nameValue)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Duplicate partition: " + nameValue)
                        .range(nameTag.getTextRange())
                        .create();
            } else {
                seenNames.add(nameValue);
            }
        }
    }
}