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
import marchvalidation.rules.MarchRuleDefinitionLanguage;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MarchRuleDefinitionInjector implements MultiHostInjector {
    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (!(context instanceof XmlText)) {
            return;
        }

        final var tag = PsiTreeUtil.getParentOfType(context, XmlTag.class);
        if (tag == null || !"definition".equals(tag.getName())) {
            return;
        }

        final var xmlFile = (XmlFile) context.getContainingFile();
        if (xmlFile.getRootTag() == null || !"march".equals(xmlFile.getRootTag().getName())) {
            return;
        }

        registrar.startInjecting(MarchRuleDefinitionLanguage.INSTANCE)
                .addPlace(null, null, (PsiLanguageInjectionHost) context,
                        new TextRange(0, context.getTextLength()))
                .doneInjecting();
    }

    @Override
    public @NotNull List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Collections.singletonList(XmlText.class);
    }
}