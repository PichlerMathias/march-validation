package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.SubTag;
import com.intellij.util.xml.TagValue;

/** Maps to RuleDto */
public interface Rule extends DomElement {
    @TagValue
    GenericDomValue<String> getDefinition();

    GenericDomValue<String> getDescription();

    @SubTag("scope")
    GenericDomValue<ValidationScope> getScope();
}