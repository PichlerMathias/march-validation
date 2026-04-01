package marchvalidation.dom.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import marchvalidation.dom.Dimension;
import marchvalidation.dom.MarchConfigRoot;
import marchvalidation.dom.util.MarchConfigUtil;
import marchvalidation.psi.ArchPartitionExpr;
import marchvalidation.psi.ArchRuleTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArchRuleAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof ArchPartitionExpr expr)) {
            return;
        }

        List<PsiElement> literalNodes = getLiterals(expr);
        if (literalNodes.isEmpty()) {
            return;
        }

        PsiFile file = element.getContainingFile();
        PsiFile originalFile = file.getContext() != null ? file.getContext().getContainingFile() : file;

        if (!(originalFile instanceof XmlFile xmlFile)) return;

        MarchConfigRoot root = DomManager.getDomManager(xmlFile.getProject())
                .getFileElement(xmlFile, MarchConfigRoot.class)
                .getRootElement();
        var cache = MarchConfigUtil.getCache(root);

        PsiElement dimNode = literalNodes.get(0);
        String dimName = dimNode.getText();

        if (isKeyword(dimName)) return;

        Dimension dim = cache.dimensions.get(dimName);
        if (dim == null) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Dimension '" + dimName + "' is not defined")
                    .range(dimNode)
                    .create();
            return;
        }

        if (literalNodes.size() > 1) {
            PsiElement partNode = literalNodes.get(1);
            String partName = partNode.getText();

            boolean exists = dim.getPartitionsElement().getPartitions().stream()
                    .anyMatch(p -> partName.equals(p.getName().getStringValue()));

            if (!exists) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Partition '" + partName + "' is not defined for dimension '" + dimName + "'")
                        .range(partNode)
                        .create();
            }
        }
    }

    private List<PsiElement> getLiterals(ArchPartitionExpr expr) {
        List<PsiElement> results = new ArrayList<>();
        PsiElement child = expr.getFirstChild();
        while (child != null) {
            if (child.getNode().getElementType() == ArchRuleTypes.LITERAL) {
                results.add(child);
            }
            child = child.getNextSibling();
        }
        return results;
    }

    private boolean isKeyword(String name) {
        return "source".equals(name) || "target".equals(name);
    }
}