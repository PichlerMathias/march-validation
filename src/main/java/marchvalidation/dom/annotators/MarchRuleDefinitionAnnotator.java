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
import marchvalidation.psi.MarchRuleDefinitionComparisonWrap;
import marchvalidation.psi.MarchRuleDefinitionPartitionExpr;
import marchvalidation.psi.MarchRuleDefinitionTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MarchRuleDefinitionAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof MarchRuleDefinitionPartitionExpr expr) {
            validatePartitionExpr(expr, holder);
        } else if (element instanceof MarchRuleDefinitionComparisonWrap wrap) {
            validateComparisonWrap(wrap, holder);
        }
    }

    private void validateComparisonWrap(MarchRuleDefinitionComparisonWrap wrap, AnnotationHolder holder) {
        final var partitions = wrap.getPartitionExprList();

        if (partitions.size() == 2) {
            final var leftDim = getDimensionName(partitions.get(0));
            final var rightDim = getDimensionName(partitions.get(1));

            if (leftDim != null && rightDim != null && !leftDim.equals(rightDim)) {
                holder.newAnnotation(HighlightSeverity.ERROR,
                                "Cannot compare different dimensions: '" + leftDim + "' and '" + rightDim + "'")
                        .range(wrap)
                        .create();
            }
        }

        final var dimNode = wrap.getLiteral();
        if (dimNode != null) {
            final var leftDim = getDimensionName(partitions.get(0));
            final var rightDim = dimNode.getText();

            if (leftDim != null && !leftDim.equals(rightDim)) {
                holder.newAnnotation(HighlightSeverity.ERROR,
                                "Dimension mismatch: expected '" + leftDim + "' but found '" + rightDim + "'")
                        .range(dimNode)
                        .create();
            }
            validateInOptions(wrap, rightDim, holder);
        }
    }

    private void validatePartitionExpr(MarchRuleDefinitionPartitionExpr expr, AnnotationHolder holder) {
        final var literals = getLiterals(expr);
        if (literals.isEmpty()) {
            return;
        }

        final var dimName = isKeyword(literals.get(0).getText())
                ? (literals.size() > 1 ? literals.get(1).getText() : null)
                : literals.get(0).getText();

        if (dimName == null) {
            return;
        }

        final var dim = resolveDimension(expr, dimName);
        if (dim == null) {
            final var nodeToMark = isKeyword(literals.get(0).getText()) ? literals.get(1) : literals.get(0);
            holder.newAnnotation(HighlightSeverity.ERROR, "Dimension '" + dimName + "' is not defined")
                    .range(nodeToMark).create();
            return;
        }

        if (literals.size() > (isKeyword(literals.get(0).getText()) ? 2 : 1)) {
            final var partNode = literals.get(literals.size() - 1);
            validatePartitionValue(dim, partNode, holder, dimName);
        }
    }

    private void validateInOptions(MarchRuleDefinitionComparisonWrap wrap, String dimName, AnnotationHolder holder) {
        final var dim = resolveDimension(wrap, dimName);
        if (dim == null) {
            return;
        }

        final var options = wrap.getInOptions();
        if (options != null) {
            for (PsiElement partNode : getLiterals(options)) {
                validatePartitionValue(dim, partNode, holder, dimName);
            }
        }
    }

    private void validatePartitionValue(marchvalidation.dom.Dimension dim, PsiElement partNode, AnnotationHolder holder, String dimName) {
        final var partName = partNode.getText();
        final var exists = dim.getPartitionsElement().getPartitions().stream()
                .anyMatch(p -> partName.equals(p.getName().getStringValue()));

        if (!exists) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Partition '" + partName + "' is not defined for dimension '" + dimName + "'")
                    .range(partNode).create();
        }
    }

    @Nullable
    private String getDimensionName(MarchRuleDefinitionPartitionExpr expr) {
        final var literals = getLiterals(expr);
        if (literals.isEmpty()) {
            return null;
        }

        final var first = literals.get(0).getText();
        if (isKeyword(first)) {
            return (literals.size() > 1) ? literals.get(1).getText() : null;
        }
        return first;
    }

    private List<PsiElement> getLiterals(PsiElement element) {
        return PsiTreeUtil.findChildrenOfType(element, PsiElement.class).stream()
                .filter(e -> e.getNode().getElementType() == MarchRuleDefinitionTypes.LITERAL)
                .toList();
    }

    private marchvalidation.dom.Dimension resolveDimension(PsiElement element, String dimName) {
        final var file = element.getContainingFile();
        final var originalFile = file.getContext() != null ? file.getContext().getContainingFile() : file;
        if (!(originalFile instanceof XmlFile xmlFile)) {
            return null;
        }

        final var root = DomManager.getDomManager(xmlFile.getProject()).getFileElement(xmlFile, MarchConfigRoot.class);
        if (root == null) {
            return null;
        }

        return MarchConfigUtil.getCache(root.getRootElement()).getDimensions().get(dimName);
    }

    private boolean isKeyword(String name) {
        return "source".equals(name) || "target".equals(name);
    }
}