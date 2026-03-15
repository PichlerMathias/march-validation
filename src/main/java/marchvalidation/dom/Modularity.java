package marchvalidation.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTagList;
import marchvalidation.dom.converters.DimensionConverter;
import marchvalidation.dom.converters.PartitionConverter;
import marchvalidation.dom.converters.VariableReferenceConverter;

import java.util.List;

/** Maps to ModularityDto */
public interface Modularity extends DomElement {
    @Required
    @Attribute("dimension")
    @Convert(DimensionConverter.class)
    GenericAttributeValue<Dimension> getDimension();

    @Attribute("partition")
    @Convert(PartitionConverter.class)
    GenericAttributeValue<Partition> getPartition();

    @Attribute("groupId")
    @Convert(VariableReferenceConverter.class)
    GenericAttributeValue<String> getGroupId();

    GenericDomValue<String> getParentGroupId();

    @Attribute("artifactId")
    @Convert(VariableReferenceConverter.class)
    GenericAttributeValue<String> getArtifactId();

    @Attribute("rootPackage")
    @Convert(VariableReferenceConverter.class)
    GenericAttributeValue<String> getRootPackage();

    @SubTagList("modularity")
    List<Modularity> getSubModularities();

    @SubTagList("packageModularity")
    List<PackageModularity> getPackageModularities();
}