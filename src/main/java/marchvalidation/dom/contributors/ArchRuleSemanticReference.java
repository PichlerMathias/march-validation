package marchvalidation.dom.contributors;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.jetbrains.cef.remote.thrift.annotation.Nullable;
import marchvalidation.dom.MarchConfigRoot;
import marchvalidation.dom.util.MarchConfigUtil;

public class ArchRuleSemanticReference extends PsiReferenceBase<PsiElement> {
    public ArchRuleSemanticReference(PsiElement element) {
        super(element, new TextRange(0, element.getTextLength()));
    }

    @Override
    public @Nullable PsiElement resolve() {
        String name = getElement().getText();
        MarchConfigRoot root = MarchConfigUtil.getRoot(getElement());
        if (root == null) {
            return null;
        }

        var cache = MarchConfigUtil.getCache(root);

        if (cache.getDimensions().containsKey(name)) {
            return cache.getDimensions().get(name).getXmlElement();
        }
        if (cache.getPartitions().containsKey(name)) {
            return cache.getPartitions().get(name).getXmlElement();
        }

        if (name.equals("source") || name.equals("target")) {
            return getElement();
        }

        return null;
    }
}