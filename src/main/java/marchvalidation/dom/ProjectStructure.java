package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTag;

/** Maps to ProjectStructureDto */
public interface ProjectStructure extends DomElement {
    @SubTag("root")
    Root getRoot();
}