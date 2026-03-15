package marchvalidation.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTagList;
import marchvalidation.dom.converters.PartitionConverter;

import java.util.List;

/** Maps to JPackageDto */
public interface JPackage extends DomElement {
    @Required
    GenericDomValue<String> getName();

    @Attribute("partition")
    @Convert(PartitionConverter.class)
    GenericAttributeValue<Partition> getPartition();

    GenericDomValue<Boolean> getOptional();

    @SubTagList("jpackage")
    List<JPackage> getSubJPackages();
}