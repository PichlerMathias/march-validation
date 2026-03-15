package marchvalidation.dom;

import com.intellij.util.xml.GenericAttributeValue;
import marchvalidation.dom.converters.DimensionConverter;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.SubTagList;
import marchvalidation.dom.converters.PartitionConverter;

import java.util.List;

/** Maps to VirtualModuleDto */
public interface VirtualModule extends DomElement {
    @SubTagList("virtualModule")
    List<VirtualModule> getSubVirtualModules();

    @SubTagList("virtualModuleRef")
    List<VirtualModuleRef> getVirtualModuleRefs();


    @Attribute("dimension")
    @Convert(DimensionConverter.class)
    GenericAttributeValue<Dimension> getDimension();

    @Attribute("partition")
    @Convert(PartitionConverter.class)
    GenericAttributeValue<Partition> getPartition();

    GenericDomValue<String> getVirtualArtifactId();
    GenericDomValue<String> getVirtualGroupId();
}