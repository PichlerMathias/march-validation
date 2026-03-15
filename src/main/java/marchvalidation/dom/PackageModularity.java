package marchvalidation.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import marchvalidation.dom.converters.DimensionConverter;
import marchvalidation.dom.converters.PartitionConverter;
import marchvalidation.dom.converters.VariableReferenceConverter;

import java.util.List;

/** Maps to PackageModularityDto */
public interface PackageModularity extends DomElement {
    @Attribute("dimension")
    @Convert(DimensionConverter.class)
    GenericAttributeValue<Dimension> getDimension();

    @Attribute("partition")
    @Convert(PartitionConverter.class)
    GenericAttributeValue<Partition> getPartition();

    @Attribute("name")
    @Convert(VariableReferenceConverter.class)
    GenericAttributeValue<String> getName();

    @SubTagList("packageModularity")
    List<PackageModularity> getSubPackageModularities();
}