package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTag;

/** * Root element for march-config.xml.
 * Maps to MarchConfigDto.java
 */
public interface MarchConfigRoot extends DomElement {

    @SubTag("dimensions")
    Dimensions getDimensionsWrapper();

    @SubTag("projectStructure")
    ProjectStructure getProjectStructure();

    @SubTag("packageTemplates")
    PackageTemplates getPackageTemplates();

    @SubTag("modules")
    Modules getModules();

    @SubTag("rules")
    Rules getRules();
}