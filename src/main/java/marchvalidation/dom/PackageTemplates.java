package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/** Maps to PackageTemplatesDto */
public interface PackageTemplates extends DomElement {
    @SubTagList("packageTemplate")
    List<PackageTemplate> getPackageTemplates();
}