package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/** Maps to ModulesDto */
public interface Modules extends DomElement {
    @SubTagList("module")
    List<Module> getModules();
}