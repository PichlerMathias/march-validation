package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.SubTag;

/** Maps to RuleConfigurationDto */
public interface RuleConfiguration extends DomElement {
    @SubTag("strategy")
    GenericDomValue<RuleStrategy> getStrategy();
}