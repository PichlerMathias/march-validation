package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;

/** Maps to PartitionDto */
public interface Partition extends DomElement {
    @Required
    @NameValue
    GenericDomValue<String> getName();

    GenericDomValue<String> getDescription();
}