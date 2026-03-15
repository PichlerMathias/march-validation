package marchvalidation.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.SubTag;
import com.intellij.util.xml.SubTagList;
import marchvalidation.dom.converters.PartitionConverter;

import java.util.List;

/** Maps to ModuleDto */
public interface Module extends DomElement {
    @SubTagList("module")
    List<Module> getSubModules();

    @SubTag("packageTemplate")
    PackageTemplateRef getPackageTemplate();

    @SubTagList("virtualModule")
    List<VirtualModule> getVirtualModules();

    GenericDomValue<String> getGroupId();
    GenericDomValue<String> getArtifactId();

    @Attribute("partition")
    @Convert(PartitionConverter.class)
    GenericAttributeValue<Partition> getPartition();
}