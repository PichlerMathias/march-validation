package marchvalidation.dom;


import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTag;

/** Maps to DimensionDto */
public interface Dimension extends DomElement {
    @Required
    @NameValue
    GenericDomValue<String> getName();

    GenericDomValue<String> getDescription();

    /** Maps to PartitionsDto */
    @SubTag("partitions")
    Partitions getPartitionsElement();
}