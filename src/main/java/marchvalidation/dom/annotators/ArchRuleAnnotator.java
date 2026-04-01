package marchvalidation.dom.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import marchvalidation.dom.MarchConfigRoot;
import marchvalidation.dom.util.MarchConfigUtil;
import marchvalidation.psi.ArchComparisonWrap;
import marchvalidation.psi.ArchPartitionExpr;
import marchvalidation.psi.ArchRuleTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArchRuleAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof ArchPartitionExpr expr) {
            validatePartitionExpr(expr, holder);
        }
        else if (element instanceof ArchComparisonWrap wrap) {
            validateComparisonWrap(wrap, holder);
        }
    }

    private void validatePartitionExpr(ArchPartitionExpr expr, AnnotationHolder holder) {
        final var literals = getLiteralsFromElement(expr);
        if (literals.isEmpty()) {
            return;
        }

        final var dimName = literals.get(0).getText();
        if (isKeyword(dimName)) {
            return;
        }

        final var dim = resolveDimension(expr, dimName);
        if (dim == null) {
            markError(holder, literals.get(0), "Dimension '" + dimName + "' is not defined");
            return;
        }

        // If it's 'layer.dto', literals.get(1) is 'dto'
        if (literals.size() > 1) {
            validatePartition(dim, literals.get(1), holder, dimName);
        }
    }

    private void validateComparisonWrap(ArchComparisonWrap wrap, AnnotationHolder holder) {
        final var dimNode = wrap.getLiteral();
        if (dimNode == null) {
            return;
        }

        final var dimName = dimNode.getText();
        final var dim = resolveDimension(wrap, dimName);

        if (dim == null) {
            markError(holder, dimNode, "Dimension '" + dimName + "' is not defined");
            return;
        }

        final var options = wrap.getInOptions();
        if (options != null) {
            final var partitions = getLiteralsFromElement(options);
            for (PsiElement partNode : partitions) {
                validatePartition(dim, partNode, holder, dimName);
            }
        }
    }

    private void validatePartition(marchvalidation.dom.Dimension dim, PsiElement partNode, AnnotationHolder holder, String dimName) {
        final var partName = partNode.getText();
        final var exists = dim.getPartitionsElement().getPartitions().stream()
                .anyMatch(p -> partName.equals(p.getName().getStringValue()));

        if (!exists) {
            markError(holder, partNode, "Partition '" + partName + "' is not defined for dimension '" + dimName + "'");
        }
    }

    private marchvalidation.dom.Dimension resolveDimension(PsiElement element, String dimName) {
        final var file = element.getContainingFile();
        final var originalFile = file.getContext() != null ? file.getContext().getContainingFile() : file;
        if (!(originalFile instanceof XmlFile xmlFile)) {
            return null;
        }

        final var root = DomManager.getDomManager(xmlFile.getProject())
                .getFileElement(xmlFile, MarchConfigRoot.class);
        if (root == null) {
            return null;
        }

        final var cache = MarchConfigUtil.getCache(root.getRootElement());
        return cache.getDimensions().get(dimName);
    }

    private List<PsiElement> getLiteralsFromElement(PsiElement element) {
        return PsiTreeUtil.findChildrenOfType(element, PsiElement.class).stream()
                .filter(e -> e.getNode().getElementType() == ArchRuleTypes.LITERAL)
                .toList();
    }

    private void markError(AnnotationHolder holder, PsiElement node, String message) {
        holder.newAnnotation(HighlightSeverity.ERROR, message)
                .range(node)
                .create();
    }

    private boolean isKeyword(String name) {
        return "source".equals(name) || "target".equals(name);
    }
}