package marchvalidation.dom;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

import java.util.List;

public interface Dimensions extends DomElement {
    @SubTagList("dimension")
    List<Dimension> getDimensionList();
}