package marchvalidation.injection;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import marchvalidation.rules.ArchRuleLanguage;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ArchRuleInjector implements MultiHostInjector {
    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        // 1. Check if the context is an XML Text element
        if (!(context instanceof XmlText)) {
            return;
        }

        // 2. Check if the parent is a <definition> tag
        XmlTag tag = PsiTreeUtil.getParentOfType(context, XmlTag.class);
        if (tag == null || !"definition".equals(tag.getName())) {
            return;
        }

        // 3. Optional: Verify the root tag is <march> to avoid false positives
        XmlFile xmlFile = (XmlFile) context.getContainingFile();
        if (xmlFile.getRootTag() == null || !"march".equals(xmlFile.getRootTag().getName())) {
            return;
        }

        // 4. Inject the language
        registrar.startInjecting(ArchRuleLanguage.INSTANCE)
                .addPlace(null, null, (PsiLanguageInjectionHost) context,
                        new TextRange(0, context.getTextLength()))
                .doneInjecting();
    }

    @Override
    public @NotNull List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Collections.singletonList(XmlText.class);
    }
}