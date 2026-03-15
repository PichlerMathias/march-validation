package marchvalidation.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/** Maps to PackageTemplateDto */
public interface PackageTemplate extends DomElement {
    @Required
    @NameValue
    @Attribute("name")
    GenericAttributeValue<String> getName();

    @SubTagList("jpackage")
    List<JPackage> getJPackages();
}