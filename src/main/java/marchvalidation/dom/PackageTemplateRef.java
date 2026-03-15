package marchvalidation.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import marchvalidation.dom.converters.PackageTemplateConverter;

/** Maps to PackageTemplateRefDto */
public interface PackageTemplateRef extends DomElement {
    @Attribute("name")
    @Convert(PackageTemplateConverter.class)
    GenericAttributeValue<PackageTemplate> getName();
}