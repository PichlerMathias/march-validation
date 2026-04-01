package marchvalidation.dom.contributors;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.util.ProcessingContext;
import marchvalidation.psi.MarchRuleDefinitionTypes;
import marchvalidation.rules.MarchRuleDefinitionLanguage;
import org.jetbrains.annotations.NotNull;

public class MrchRuleDefinitionReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(MarchRuleDefinitionTypes.LITERAL).withLanguage(MarchRuleDefinitionLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        return new PsiReference[] {
                            new MarchRuleDefinitionSemanticReference(element)
                        };
                    }
                }
        );
    }
}