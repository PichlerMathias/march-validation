package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTag;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/** Maps to RulesDto */
public interface Rules extends DomElement {
    @SubTagList("rule")
    List<Rule> getRules();

    @SubTag("configuration")
    RuleConfiguration getConfiguration();
}