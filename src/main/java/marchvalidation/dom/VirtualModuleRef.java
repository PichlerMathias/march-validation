package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericDomValue;

/** Maps to VirtualModuleRefDto */
public interface VirtualModuleRef extends DomElement {
    GenericDomValue<String> getGroupId();
    GenericDomValue<String> getArtifactId();
    GenericDomValue<String> getPartition();
    GenericDomValue<String> getVirtualArtifactId();
}